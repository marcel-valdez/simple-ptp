/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import data.structs.ClockIdentity;
import data.structs.CurrentDataSet;
import data.structs.MsgDelayReq;
import data.structs.MsgDelayResp;
import data.structs.MsgFollowUp;
import data.structs.MsgManagement;
import data.structs.MsgSync;
import data.structs.PortDataSet;
import data.structs.TimeRepresentation;
import data.types.DataValue;
import data.types.Int32;
import data.types.UInt32;
import entities.enums.ManagementKey;
import entities.enums.PortState;
import entities.enums.TimeSource;
import entities.factory.Factory;
import entities.interfaces.IEventMsgHandler;
import entities.interfaces.IGeneralMsgHandler;
import entities.interfaces.IGlobalNodesRegistry;
import entities.interfaces.IInInterface;
import java.util.Arrays;
import system.NotImplementedException;

/**
 *
 * @author Marcel
 */
public class PTPEngine implements IEventMsgHandler, IGeneralMsgHandler, Runnable {

    final private ClockIdentity ownerId;
    final private OrdinaryClock owner;
    /**
     * El puerto por el cuál se comunica el reloj 
     */
    private final ClockPort port;
    /**
     * El reloj local, sincronizado en caso de que no sea maestro este reloj 
     */
    final private LocalClock clock;
    final private Thread thread;
    private boolean startedBMC = false;

    public PTPEngine(OrdinaryClock owner, PortDataSet portData) {
        this.owner = owner;
        this.ownerId = owner.ClockId;
        this.port = new ClockPort(this);
        this.clock = Factory.getInstance().CreateClock();
        owner.currentDataSet = new CurrentDataSet();
        OrdinaryClock.defaultDataSet.timeSource = this.clock.IsHighResolution() ? TimeSource.PTP : TimeSource.INTERNAL_OSCILLATOR;

        Thread tStart = new Thread(this);
        tStart.start();
        this.thread = tStart;
    }

    private void AttemptInitBMC(PortDataSet ds) {

        if (Factory.getInstance().GetGlobalNodesRegistry().InitBMC(this.ownerId.clockUuidField)) {
            ds.portState = PortState.PRE_MASTER;
            this.startedBMC = true;
            SendCandidateBMC();
        } else {
            ds.portState = PortState.WAIT_PRE_MASTER;
        }
    }

    private void EndBMC() {
        if (Factory.getInstance().GetGlobalNodesRegistry().EndBMC(this.ownerId.clockUuidField)) {
            this.startedBMC = false;
        }
    }

    private void ForwardMessage(MsgManagement message) {
        IInInterface neighbor = Factory.getInstance().GetGlobalNodesRegistry().GetNextClock(this.ownerId.clockUuidField);
        neighbor.InAnnounce(message);
    }

    private void ProcessCandidateMessage(MsgManagement message) {
        // if its me, then start sending the SET_MASTER message
        if (Arrays.equals(message.payload.clockIdentity.clockUuidField, this.ownerId.clockUuidField)) {
            this.port.getPortDataSet().portState = PortState.MASTER;
            message.managementMessageKey = ManagementKey.BMC_SET_MASTER;
            ForwardMessage(message);
        } else {
            // Compare the dataset
            int p1 = message.payload.defaultData.priority1.getValue();
            int myP1 = OrdinaryClock.defaultDataSet.priority1.getValue();
            int p2 = message.payload.defaultData.priority2.getValue();
            int myP2 = OrdinaryClock.defaultDataSet.priority2.getValue();
            int clockClass = message.payload.defaultData.clockClass.getValue();
            int myClockClass = OrdinaryClock.defaultDataSet.clockClass.getValue();
            int timeSource = message.payload.defaultData.timeSource.getValue();
            int myTimeSource = OrdinaryClock.defaultDataSet.timeSource.getValue();
            long variance = message.payload.defaultData.clockVariance.getValue();
            long myVariance = OrdinaryClock.defaultDataSet.clockVariance.getValue();
            if (p1 > myP1 || p2 > myP2 || clockClass > myClockClass || timeSource > myTimeSource || variance > myVariance) {
                // Yo soy mejor candidato
                this.port.getPortDataSet().portState = PortState.PRE_MASTER;
                SendCandidateBMC();
            } else if (!this.startedBMC) {
                // El candidato es mejor o igual, se queda
                this.port.getPortDataSet().portState = PortState.MASTER_VOTER;
                ForwardMessage(message);
            } else {
                // Ya dio la vuelta el mensaje, hay que enviar set master del
                // candidato recibido
                message.managementMessageKey = ManagementKey.BMC_SET_MASTER;
                // Somos slave, ya lo sabemos :)
                this.port.getPortDataSet().portState = PortState.SLAVE;
                ForwardMessage(message);
            }
        }
    }

    private void ProcessSetMasterMessage(MsgManagement message) {
        // Set the new master, if its me, then change the state, and stop sending the message
        if (Arrays.equals(message.payload.clockIdentity.clockUuidField, this.ownerId.clockUuidField)) {
            this.port.getPortDataSet().portState = PortState.MASTER;
        } else {
            this.port.getPortDataSet().portState = PortState.SLAVE;
        }

        if (!this.startedBMC) {
            // Hay que reenviar el mensaje
            this.ForwardMessage(message);
        } else {
            // Si yo empecé el BMC, entonces aquí acaba, no hay nada qué hacer.
            // más que terminar el algoritmo
            this.EndBMC();
        }
    }

    private void SendCandidateBMC() {
        MsgManagement msg = new MsgManagement();
        msg.managementMessageKey = ManagementKey.BMC_CANDIDATE;
        msg.payload.clockIdentity = this.ownerId;
        msg.payload.defaultData = OrdinaryClock.defaultDataSet;
        msg.payload.current = this.owner.currentDataSet;
        msg.payload.port = this.port.getPortDataSet();
        this.ForwardMessage(msg);
    }

    private void UpdateTime(long offset, long delay) {
        this.clock.setOffset(offset);
        this.owner.currentDataSet.offsetFromMaster.seconds = new UInt32(DataValue.ToData(offset / 1000000000L));
        this.owner.currentDataSet.offsetFromMaster.nanoseconds = new Int32(DataValue.ToData(offset % 1000000000L));
        this.owner.currentDataSet.oneWayDelay.seconds = new UInt32(DataValue.ToData(delay / 1000000000L));
        this.owner.currentDataSet.oneWayDelay.nanoseconds = new Int32(DataValue.ToData(delay % 1000000000L));
    }

    private void Initialize() {
        /**
         * Qué hacer al inicializar? Empezar el BMC y pasar a PRE_MASTER?
         * Sí, entonces se requiere acceso al repositorio global.
         */
        PortDataSet ds = port.getPortDataSet();
        IGlobalNodesRegistry registry = Factory.getInstance().GetGlobalNodesRegistry();
        registry.RegisterClock(
                this.ownerId.clockUuidField,
                Factory.getInstance().CreateInInterface(
                port.getGeneralInterface(),
                port.getEventInterface()));
        registry.RegisterRepository(this.ownerId.clockUuidField, port.getRepo());
        AttemptInitBMC(ds);
    }

    @Override
    public void ProcessSync(MsgSync message) {

        PortDataSet ds = port.getPortDataSet();
        if (ds.portState.equals(PortState.MASTER)) {
            /**
             * ¿Si somos master, ignoramos el mensaje, pero actualizamos varianza?
             * NO.
             */
        } else if (ds.portState.equals(PortState.SLAVE)) {
            /**
             * Sync itself
             */
            long syncSentStamp = 0;
            long syncRecvStamp = clock.getTimeMillis() * 1000L * 1000L;

            syncSentStamp = message.originTimestamp.seconds.getValue() * 1000L * 1000L * 1000L;
            syncSentStamp += message.originTimestamp.nanoseconds.getValue();

            long delayReceivedStamp = 0;
            long delaySentStamp = 0;

            MsgDelayReq request = new MsgDelayReq();
            TimeRepresentation time = clock.getTime();
            request.originTimestamp.nanoseconds = time.nanoseconds;
            request.originTimestamp.seconds = time.seconds;
            MsgDelayResp response = this.port.getEventInterface().OutDelayRequest(request);

            delayReceivedStamp = response.delayReceiptTimestamp.seconds.getValue() * 1000L * 1000L * 1000L;
            delayReceivedStamp += response.delayReceiptTimestamp.nanoseconds.getValue();
            delaySentStamp = time.seconds.getValue() * 1000L * 1000L * 1000L;
            delaySentStamp += time.nanoseconds.getValue();

            long offset = ((syncRecvStamp - syncSentStamp) - (delayReceivedStamp - delaySentStamp)) / 2;
            long delay = ((syncRecvStamp - syncSentStamp) + (delayReceivedStamp - delaySentStamp)) / 2;
            UpdateTime(offset, delay);
        }
    }

    @Override
    public MsgDelayResp ProcessDelayReq(MsgDelayReq message) {
        MsgDelayResp response = new MsgDelayResp();
        response.delayReceiptTimestamp = this.clock.getTime();
        return response;
    }

    @Override
    public void ProcessFollowUp(MsgFollowUp message) {
        throw new NotImplementedException("Follow_Up not implemented.");
    }

    @Override
    public void ProcessAnnouncement(MsgManagement message) {
        if (message.managementMessageKey.equals(ManagementKey.BMC_CANDIDATE)) {
            ProcessCandidateMessage(message);
        } else if (message.managementMessageKey.equals(ManagementKey.BMC_SET_MASTER)) {
            ProcessSetMasterMessage(message);
        }
    }

    public void run() {
        /** Se usa while true, porque esta instancia sabrá cuando 
        morir, en base a los mensajes de Announce */
        while (true) {
            // Sincroniza a los slaves

            PortDataSet ds = port.getPortDataSet();
            if (ds.portState.equals(PortState.INITIALIZING)) {
                Initialize();
            } else if (ds.portState.equals(PortState.MASTER)) {
                /**
                 * Si estamos en master, hay que sincronizar a los putos
                 */
                MsgSync sync = new MsgSync();
                sync.originTimestamp = this.clock.getTime();
                this.port.getEventInterface().OutSync(sync);
            } else if (ds.portState.equals(PortState.WAIT_PRE_MASTER)) {
                IGlobalNodesRegistry registry = Factory.getInstance().GetGlobalNodesRegistry();
                AttemptInitBMC(ds);
            } else if (ds.portState.equals(PortState.MASTER_VOTER)) {
                /**
                 * ???
                 */
            } else if (ds.portState.equals(PortState.PRE_MASTER)) {
                /**
                 * Cuando eres candidato master
                 */
            } else if (ds.portState.equals(PortState.SLAVE)) {
                /**
                 * Nada qué hacer, esperar mensajes sync o management
                 */
            } else if (ds.portState.equals(PortState.LISTENING)) {
                /**
                 * ???
                 */
            } else if (ds.portState.equals(PortState.DISABLED)) {
                /**
                 * ???
                 */
            } else if (ds.portState.equals(PortState.FAULTY)) {
                /**
                 * ???
                 */
            } else if (ds.portState.equals(PortState.PASSIVE)) {
                /**
                 * ???
                 */
            } else if (ds.portState.equals(PortState.UNCALIBRATED)) {
                /**
                 * ???
                 */
            }
        }
    }
}
