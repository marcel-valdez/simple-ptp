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
import data.types.Octet;
import data.types.UInt16;
import data.types.UInt32;
import entities.enums.ManagementKey;
import entities.enums.PortState;
import entities.enums.TimeSource;
import entities.factory.Factory;
import entities.interfaces.IEventMsgHandler;
import entities.interfaces.IGeneralMsgHandler;
import entities.interfaces.IGlobalNodesRegistry;
import entities.interfaces.IInInterface;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import system.NotImplementedException;

/**
 *
 * @author Marcel
 */
public class PTPEngine implements IEventMsgHandler, IGeneralMsgHandler, Runnable {

    public static final String CHANGING_DELAY = "Changing delay to: {0} nanoseconds";
    public static final String CHANGING_OFFSET = "Changing offset by: {0} nanoseconds";
    private static final String ERROR_DEFINIR_EL_MASTER = "Hubo un error al intentar definir el master clock";
    private static final String RECEIVED_CANDIDATE_MESSAGE = "Received a BMC Candidate message";
    final private static Random random = new Random(System.currentTimeMillis());
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
        this.Initialize();
        this.thread = new Thread(this);
        this.thread.start();
    }

    private void Initialize() {
        /**
         * Qué hacer al inicializar? Empezar el BMC y pasar a PRE_MASTER?
         * Sí, entonces se requiere acceso al repositorio global.
         */
        this.owner.currentDataSet = new CurrentDataSet();
        OrdinaryClock.defaultDataSet.timeSource = this.clock.IsHighResolution() ? TimeSource.PTP : TimeSource.INTERNAL_OSCILLATOR;
        this.SetState(PortState.INITIALIZING);
        PortDataSet ds = port.getPortDataSet();
        ds.portIdField = new UInt16((byte) random.nextInt(127), (byte) random.nextInt(127));
        ds.portUuidField = this.ownerId.clockUuidField;
        IGlobalNodesRegistry registry = Factory.getInstance().GetGlobalNodesRegistry();
        Logger.getGlobal().log(Level.INFO, "Registering Clock");
        registry.RegisterRepository(this.ownerId.clockUuidField, port.getRepo());
        registry.RegisterClock(
                this.ownerId.clockUuidField,
                Factory.getInstance().CreateInInterface(
                port.getGeneralInterface(),
                port.getEventInterface()));
        this.SetState(PortState.WAIT_PRE_MASTER);
        this.port.SynchronizeRepo(registry);
        AttemptInitBMC(ds);
    }

    private void AttemptInitBMC(PortDataSet ds) {

        if (Factory.getInstance().GetGlobalNodesRegistry().InitBMC(this.ownerId.clockUuidField)) {
            SetState(PortState.PRE_MASTER);
            this.startedBMC = true;
            SendCandidateBMC();
        } else {
            SetState(PortState.WAIT_PRE_MASTER);
        }
    }

    private boolean IsState(PortState state) {
        return this.port.getPortDataSet().portState.equals(state);
    }

    private boolean SetSlaveState(MsgManagement message) {
        // Somos slave, ya lo sabemos
        Octet[] masterUuid = message.payload.clockIdentity.clockUuidField;
        boolean result = this.port.SetMaster(masterUuid);
        if (result) {
            SetState(PortState.SLAVE);
        }

        return result;
    }

    private void SetState(PortState state) {
        this.PrintStateName(state);
        this.port.getPortDataSet().portState = state;
    }

    private void EndBMC() {
        if (Factory.getInstance().GetGlobalNodesRegistry().EndBMC(this.ownerId.clockUuidField)) {
            this.startedBMC = false;
        }
    }

    private void ForwardMessage(MsgManagement message) {
        IInInterface neighbor = Factory.getInstance().GetGlobalNodesRegistry().GetNextClock(this.ownerId.clockUuidField);
        if (neighbor != null && !Octet.Compare(neighbor.GetPortDataSet().portUuidField, this.port.getPortDataSet().portUuidField)) {
            neighbor.InAnnounce(message);
        } else if (!IsState(PortState.MASTER)) { // Está entrando aquí sin ser el que inició el BMC
            // me hago master, obviamente, soy el mejor (porque no hay ningún otro), empiezo a enviar el mensaje SET_MASTER
            this.SetState(PortState.MASTER);
            message.managementMessageKey = ManagementKey.BMC_SET_MASTER;
            //this.ForwardMessage(message);
            this.EndBMC();
        }
    }

    private void ProcessCandidateMessage(MsgManagement message) {
        Logger.getGlobal().log(Level.INFO, RECEIVED_CANDIDATE_MESSAGE);
        // if its me, then start sending the SET_MASTER message
        if (Octet.Compare(message.payload.clockIdentity.clockUuidField, this.ownerId.clockUuidField)) {
            /** 
             * Bajo cualquier condición, si te llega tu propio mensaje para
             * ser candidato de master, eres master.
             */
            SetState(PortState.MASTER);
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
                SetState(PortState.PRE_MASTER);
                SendCandidateBMC();
            } else if (!this.startedBMC) {
                // El candidato es mejor o igual, se queda
                SetState(PortState.MASTER_VOTER);
                ForwardMessage(message);
            } else {
                // So yo inicié el BMC, y me llevo un mensaje de Candidato
                // de master, quiere decir que ya dio la vuelta, y
                // ya se definió el master.
                message.managementMessageKey = ManagementKey.BMC_SET_MASTER;
                if (SetSlaveState(message)) {
                    ForwardMessage(message);
                } else {
                    Logger.getGlobal().log(Level.SEVERE, ERROR_DEFINIR_EL_MASTER);
                }
            }
        }
    }

    private void ProcessSetMasterMessage(MsgManagement message) {
        Logger.getGlobal().log(Level.INFO, "Received a Set Master message");
        // Set the new master, if its me, then change the state, and stop sending the message
        if (Octet.Compare(message.payload.clockIdentity.clockUuidField, this.ownerId.clockUuidField)) {
            SetState(PortState.MASTER);
        } else {
            if (!SetSlaveState(message)) {
                Logger.getGlobal().log(Level.SEVERE, ERROR_DEFINIR_EL_MASTER);
            } else {
            }
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
        Logger.getGlobal().log(Level.INFO, CHANGING_OFFSET, offset);
        Logger.getGlobal().log(Level.INFO, CHANGING_DELAY, delay);
        long newOffSet = this.clock.getOffset() - offset;
        Logger.getGlobal().log(Level.INFO, "Slave: Nuevo offset {0}", new Object[]{newOffSet});
        this.clock.setOffset(newOffSet);
        this.owner.currentDataSet.offsetFromMaster.seconds = new UInt32(DataValue.ToData(offset / 1000000000L));
        this.owner.currentDataSet.offsetFromMaster.nanoseconds = new Int32(DataValue.ToData(offset % 1000000000L));
        this.owner.currentDataSet.oneWayDelay.seconds = new UInt32(DataValue.ToData(delay / 1000000000L));
        this.owner.currentDataSet.oneWayDelay.nanoseconds = new Int32(DataValue.ToData(delay % 1000000000L));
        TimeRepresentation time = this.clock.getTime();
        Logger.getGlobal().log(Level.INFO, "Synced time to: {0} seconds : {1} nanoseconds", new Object[]{time.seconds.getValue(), time.nanoseconds.getValue()});
    }

    @Override
    public void ProcessSync(MsgSync message) {
        Logger.getGlobal().log(Level.INFO, "Received a Sync message");
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
            long mtrSyncSentStamp = 0;
            long slvSyncRecvStamp = clock.getTimeNanos();

            mtrSyncSentStamp = message.originTimestamp.toNanos();
            Logger.getGlobal().log(Level.INFO, "Slave: recibido sync con {0} ns\n\t @ {1}", new Object[]{message.originTimestamp.toNanos(), slvSyncRecvStamp});

            long mtrDelayRespStamp = 0;
            long slvDelayReqStamp = 0;

            MsgDelayReq request = new MsgDelayReq();
            TimeRepresentation delayReqTime = clock.getTime();
            request.originTimestamp = delayReqTime;
            slvDelayReqStamp = delayReqTime.toNanos();

            MsgDelayResp response = this.port.getEventInterface().OutDelayRequest(request);
            mtrDelayRespStamp = response.delayReceiptTimestamp.toNanos();
            Logger.getGlobal().log(Level.INFO, "Slave: envio delayreq @ {0} ns\nrecibo resp con {1}", new Object[]{slvDelayReqStamp, mtrDelayRespStamp});
            long offset = ((slvSyncRecvStamp - mtrSyncSentStamp) - (mtrDelayRespStamp - slvDelayReqStamp)) / 2;
            long delay = ((slvSyncRecvStamp - mtrSyncSentStamp) + (mtrDelayRespStamp - slvDelayReqStamp)) / 2;

            UpdateTime(offset, delay);
        }
    }

    @Override
    public MsgDelayResp ProcessDelayReq(MsgDelayReq message) {
        Logger.getGlobal().log(Level.INFO, "Received a delay request message");
        MsgDelayResp response = new MsgDelayResp();
        response.delayReceiptTimestamp = this.clock.getTime();
        Logger.getGlobal().log(Level.INFO, "Master: envio delayresp con {0} seg {1} ns", new Object[]{response.delayReceiptTimestamp.seconds.getValue(), response.delayReceiptTimestamp.nanoseconds.getValue()});
        return response;
    }

    @Override
    public void ProcessFollowUp(MsgFollowUp message) {
        throw new NotImplementedException("Follow_Up not implemented.");
    }

    @Override
    public void ProcessAnnouncement(MsgManagement message) {
        Logger.getGlobal().log(Level.INFO, "Received an announcement");
        if (message.managementMessageKey.equals(ManagementKey.BMC_CANDIDATE)) {
            ProcessCandidateMessage(message);
        } else if (message.managementMessageKey.equals(ManagementKey.BMC_SET_MASTER)) {
            ProcessSetMasterMessage(message);
        }
    }

    public void run() {
        /** Se usa while true, porque esta instancia sabrá cuando 
        morir, en base a los mensajes de Announce */
        long lastPing = System.currentTimeMillis() - 5000;
        while (true) {
            // Sincroniza a los slaves
            if (System.currentTimeMillis() - lastPing >= 5000) {
                Factory.getInstance().GetGlobalNodesRegistry().Ping(this.ownerId.clockUuidField);
                lastPing = System.currentTimeMillis();
            }

            PortDataSet ds = port.getPortDataSet();
            if (ds.portState.equals(PortState.INITIALIZING)) {
                /**
                 * Pasar a BMC?
                 */
            } else if (ds.portState.equals(PortState.MASTER)) {

                /**
                 * Si estamos en master, hay que sincronizar a los putos
                 */
                MsgSync sync = new MsgSync();
                sync.originTimestamp = this.clock.getTime();
                Logger.getGlobal().log(Level.INFO, "Master: syncronizando con {0} seg {1} ns", new Object[]{sync.originTimestamp.seconds.getValue(), sync.originTimestamp.nanoseconds.getValue()});
                this.port.getEventInterface().OutSync(sync);
            } else if (ds.portState.equals(PortState.WAIT_PRE_MASTER)) {
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

            try {
                synchronized (this) {
                    this.wait(2000);
                }
            } catch (InterruptedException ex) {
                Logger.getGlobal().log(Level.WARNING, ex.getMessage());
            }
        }
    }

    private void PrintStateName(PortState state) {
        if (state.equals(PortState.INITIALIZING)) {
            System.out.println("Change To: INITIALIZING");
        } else if (state.equals(PortState.MASTER)) {
            System.out.println("Change To: MASTER");
        } else if (state.equals(PortState.WAIT_PRE_MASTER)) {
            System.out.println("Change To: WAIT_PRE_MASTER");
        } else if (state.equals(PortState.MASTER_VOTER)) {
            System.out.println("Change To: MASTER_VOTER");
        } else if (state.equals(PortState.PRE_MASTER)) {
            System.out.println("Change To: PRE_MASTER");
        } else if (state.equals(PortState.SLAVE)) {
            System.out.println("Change To: SLAVE");
        } else if (state.equals(PortState.LISTENING)) {
            System.out.println("Change To: LISTENING");
        } else if (state.equals(PortState.DISABLED)) {
            System.out.println("Change To: DISABLED");
        } else if (state.equals(PortState.FAULTY)) {
            System.out.println("Change To: FAULTY");
        } else if (state.equals(PortState.PASSIVE)) {
            System.out.println("Change To: PASSIVE");
        } else if (state.equals(PortState.UNCALIBRATED)) {
            System.out.println("Change To: UNCALIBRATED");
        } else {
            System.out.println("Unkown State! Impossible, value: " + state.getValue());
        }
    }
}
