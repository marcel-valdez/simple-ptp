/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.interfaces.IEventInterface;
import data.structs.MsgDelayReq;
import data.structs.MsgDelayResp;
import data.structs.MsgSync;
import data.structs.PortDataSet;
import data.types.Octet;
import entities.interfaces.IEventMsgHandler;
import entities.interfaces.IInEventInterface;
import entities.interfaces.IInInterface;
import entities.interfaces.INetNodesRepository;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcel
 */
public class EventInterface implements IEventInterface {

    final private ClockPort mOwner;
    final private PortDataSet portDataSet;
    final private INetNodesRepository mRepo;
    private IEventMsgHandler mHandler;
    private IInEventInterface mMasterClock;

    public EventInterface(ClockPort owner, INetNodesRepository repo) {
        this.mOwner = owner;
        this.portDataSet = owner.getPortDataSet();
        this.mRepo = repo;
    }

    public PortDataSet GetPortDataSet() {
        return this.mOwner.getPortDataSet();
    }

    @Override
    public void setHandler(IEventMsgHandler handler) {
        this.mHandler = handler;
    }

    /**
     * Sets the master clock of this interface
     * @param master the master clock
     */
    public void SetMaster(IInEventInterface master) {
        this.mMasterClock = master;
    }

    /** 
     * Envía el mensaje delay request por la red 
     * -- Comunicación síncrona remota
     */
    @Override
    public MsgDelayResp OutDelayRequest(MsgDelayReq delayRequest) {
        /*/ El mensaje se envía al master-clock */
        try {
            if (this.mMasterClock != null) {
                return mMasterClock.InDelayRequest(delayRequest);
            } else {
                Logger.getLogger(EventInterface.class.getName()).log(
                        Level.WARNING,
                        "El reloj maestro era nulo al intentar"
                        + "enviarle el delayrequest");
            }
        } catch (Exception e) {
            Logger.getLogger(EventInterface.class.getName()).log(
                    Level.WARNING,
                    "Ocurrio un error al intentar enviar el"
                    + "mensaje delayrequest al reloj maestro", e);
        }

        return MsgDelayResp.Empty;
    }

    /** 
     * Envía el mensaje de sincronización por la red .
     * -- Comunicación asíncrona remota
     */
    @Override
    public void OutSync(MsgSync sync) {
        /** Debe enviar el sync al slave-clock */
        for (IInInterface slave : mRepo.GetNodes()) {
            // Debería ser one-way (asíncrono)
            try {
                slave.InSync(sync);
            } catch (NullPointerException e) {
                Logger.getLogger(EventInterface.class.getName()).log(
                        Level.WARNING,
                        "Uno de los relojes esclavos era nulo al intentar"
                        + "\nenviarle el mensaje de sincronizacion, tal reloj"
                        + "\nse eliminara del repositorio local.");
                this.mRepo.UnregisterClock(slave);
            } catch (Exception e) {
                Logger.getLogger(EventInterface.class.getName()).log(Level.WARNING,
                        "Uno de los relojes esclavos arrojó un error,"
                        + "\nse eliminará del repositorio local", e);
                this.mRepo.UnregisterClock(slave);
            }

        }
    }

    /** 
     * Recibe el mensaje delay request por la red 
     * -- Comunicación síncrona remota (por cliente),
     *    asíncrona para recibir peticiones de muchos
     *    clientes (CORBA debe proveer esto)
     **/
    @Override
    public MsgDelayResp InDelayRequest(MsgDelayReq delayRequest) {
        /**
         * Solamente debería entrar aquí si es un master el
         * puerto en cuestión
         */
        return mHandler.ProcessDelayReq(delayRequest);
    }

    /** 
     * Recibe el mensaje de sincronización por la red,
     * este método debe ser asíncrono (a nivel CORBA).
     * -- Comunicación asíncrona remota
     **/
    @Override
    public void InSync(MsgSync sync) {
        mHandler.ProcessSync(sync);
    }
}
