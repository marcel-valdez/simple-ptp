/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.interfaces.IGeneralInterface;
import entities.interfaces.IGeneralMsgHandler;
import data.structs.MsgManagement;
import data.structs.PortDataSet;
import entities.interfaces.IInInterface;
import entities.interfaces.INetNodesRepository;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcel
 */
class GeneralInterface implements IGeneralInterface {

    final private ClockPort mOwner;
    private IGeneralMsgHandler mHandler;
    private final INetNodesRepository mRepo;

    public GeneralInterface(ClockPort owner, INetNodesRepository repo) {
        this.mOwner = owner;
        this.mRepo = repo;
    }

    @Override
    public void setHandler(IGeneralMsgHandler handler) {
        this.mHandler = handler;
    }

    public PortDataSet GetPortDataSet() {
        return this.mOwner.getPortDataSet();
    }

    /* Envía un mensaje de gestión por el puerto al cuál pertenece esta interfaz */
    @Override
    public void OutAnnounce(MsgManagement mngMsg) {
        // Enviar mensaje a todos los relojes correspondientes,
        // se me ocurre que todos los relojes tengan conocimiento
        // de todos los demás relojes, y solos se vayan agregando
        // a la lista, y cuando uno no sea accesible, se deseche,
        // y que cuando se actualice la lista de relojes disponibles,
        // todos los clientes que estén escuchando a la lista, 
        // actualicen su lista.
        // ¿cómo? Nosé, y es a través de CORBA
        for (IInInterface handler : this.mRepo.GetNodes()) {
            try {
                handler.InAnnounce(mngMsg);
            } catch (Exception e) {
                Logger.getLogger(GeneralInterface.class.getName()).log(Level.WARNING, null, e);
            }
        }
    }

    /* Recibe un mensaje administrativo de un nodo externo */
    @Override
    public void InAnnounce(MsgManagement mngMsg) {
        this.mHandler.ProcessMessage(mngMsg);
    }
}
