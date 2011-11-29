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
import entities.interfaces.IEventMsgHandler;
import entities.interfaces.IInEventInterface;
import entities.interfaces.INetNodesRepository;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcel
 */
public class EventInterface implements IEventInterface {

    final private ClockPort mOwner;
    private IEventMsgHandler mHandler;
    final private PortDataSet portDataSet;
    private IInEventInterface mMasterClock;
    private INetNodesRepository mRepo;

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
     * Envía el mensaje delay request por la red 
     * -- Comunicación síncrona remota
     */
    @Override
    public MsgDelayResp OutDelayRequest(MsgDelayReq delayRequest) {
        /*/ El mensaje se envía al master-clock */
        try {
            return mMasterClock.InDelayRequest(delayRequest);
        } catch (Exception e) {
            Logger.getLogger(EventInterface.class.getName()).log(Level.WARNING, null, e);
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
        for (IInEventInterface slave : mRepo.GetNodes()) {
            // Debería ser one-way (asíncrono)
            if (slave != this.mMasterClock) {
                try {
                    slave.InSync(sync);
                } catch (Exception e) {
                    Logger.getLogger(EventInterface.class.getName()).log(Level.WARNING, null, e);
                }
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
