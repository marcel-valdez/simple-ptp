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
import entities.enums.ClockClass;
import entities.enums.ManagementKey;
import entities.enums.PortState;
import entities.enums.TimeSource;
import entities.factory.Factory;
import entities.global.GlobalNodesRegistry;
import entities.interfaces.IEventMsgHandler;
import entities.interfaces.IGeneralMsgHandler;
import entities.interfaces.IInInterface;
import java.util.Arrays;

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
    final ClockPort port;
    /**
     * El reloj local, sincronizado en caso de que no sea maestro este reloj 
     */
    final LocalClock clock;

    public PTPEngine(OrdinaryClock owner, PortDataSet portData) {
        this.owner = owner;
        this.ownerId = owner.ClockId;
        this.port = new ClockPort(this);
        this.clock = Factory.getInstance().CreateClock();
        owner.currentDataSet = new CurrentDataSet();
        OrdinaryClock.defaultDataSet.timeSource = this.clock.IsHighResolution() ? TimeSource.PTP : TimeSource.INTERNAL_OSCILLATOR;
    }

    @Override
    public void ProcessSync(MsgSync message) {

        PortDataSet ds = port.getPortDataSet();
        if (ds.portState.equals(PortState.MASTER)) {
            /**
             * ¿Si somos master, ignoramos el mensaje, pero actualizamos varianza?
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

    private void AttemptInitBMC(GlobalNodesRegistry registry, PortDataSet ds) {
        if (registry.InitBMC(this.ownerId.clockUuidField)) {
            ds.portState = PortState.PRE_MASTER;
            SendCandidateBMC(registry);
        } else {
            ds.portState = PortState.WAIT_PRE_MASTER;
        }
    }

    private void SendCandidateBMC(GlobalNodesRegistry registry) {
        MsgManagement msg = new MsgManagement();
        msg.managementMessageKey = ManagementKey.BMC_CANDIDATE;
        msg.payload.clockIdentity = this.ownerId;
        msg.payload.defaultData = OrdinaryClock.defaultDataSet;
        msg.payload.current = this.owner.currentDataSet;
        msg.payload.port = this.port.getPortDataSet();
        IInInterface neighbor = registry.GetNextClock(this.ownerId.clockUuidField);
        neighbor.InAnnounce(msg);
    }

    private void SendSetMasterBMC(GlobalNodesRegistry registry) {
        MsgManagement msg = new MsgManagement();
        msg.managementMessageKey = ManagementKey.BMC_SET_MASTER;
        msg.payload.clockIdentity = this.ownerId;
        msg.payload.defaultData = OrdinaryClock.defaultDataSet;
        msg.payload.current = this.owner.currentDataSet;
        msg.payload.port = this.port.getPortDataSet();
        IInInterface neighbor = registry.GetNextClock(this.ownerId.clockUuidField);
        neighbor.InAnnounce(msg);
    }

    private void UpdateTime(long offset, long delay) {
        this.clock.setOffset(offset);
        this.owner.currentDataSet.offsetFromMaster.seconds = new UInt32(DataValue.ToData(offset / 1000000000L));
        this.owner.currentDataSet.offsetFromMaster.nanoseconds = new Int32(DataValue.ToData(offset % 1000000000L));
        this.owner.currentDataSet.oneWayDelay.seconds = new UInt32(DataValue.ToData(delay / 1000000000L));
        this.owner.currentDataSet.oneWayDelay.nanoseconds = new Int32(DataValue.ToData(delay % 1000000000L));
    }

    @Override
    public MsgDelayResp ProcessDelayReq(MsgDelayReq message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void ProcessFollowUp(MsgFollowUp message) {
    }

    @Override
    public void ProcessMessage(MsgManagement message) {
        if (message.managementMessageKey.equals(ManagementKey.BMC_CANDIDATE)) {
            if (Arrays.equals(message.payload.clockIdentity.clockUuidField, this.ownerId.clockUuidField)) {
                this.port.getPortDataSet().portState = PortState.MASTER;

            }
            // Compare the dataset, but if its me, then start sending the SET_MASTER message
        } else if (message.managementMessageKey.equals(ManagementKey.BMC_SET_MASTER)) {
            // Set the new master, if its me, then change the state, and stop sending the message
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
            } else if (ds.portState.equals(PortState.PRE_MASTER)) {
                /**
                 * Cuando eres candidato master
                 */
            } else if (ds.portState.equals(PortState.MASTER)) {
                /**
                 * Si estamos en master, hay que sincronizar a los putos
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
            } else if (ds.portState.equals(PortState.WAIT_PRE_MASTER)) {
                GlobalNodesRegistry registry = Factory.getInstance().GetGlobalNodesRegistry();
                AttemptInitBMC(registry, ds);
            }

        }
    }

    private void Initialize() {
        /**
         * Qué hacer al inicializar? Empezar el BMC y pasar a PRE_MASTER?
         * Sí, entonces se requiere acceso al repositorio global.
         */
        PortDataSet ds = port.getPortDataSet();
        GlobalNodesRegistry registry = Factory.getInstance().GetGlobalNodesRegistry();
        registry.RegisterClock(
                this.ownerId.clockUuidField,
                Factory.getInstance().CreateInInterface(
                port.getGeneralInterface(),
                port.getEventInterface()));
        registry.RegisterRepository(this.ownerId.clockUuidField, port.getRepo());
        AttemptInitBMC(registry, ds);
    }
}
