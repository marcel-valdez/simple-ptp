/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.interfaces.IOutGeneralInterface;
import entities.interfaces.IInGeneralInterface;
import entities.interfaces.IGeneralMsgHandler;
import data.structs.MsgManagement;
import data.structs.PortDataSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcel
 */
class GeneralInterface implements IInGeneralInterface, IOutGeneralInterface {

    final private ClockPort mOwner;
    final private List<IGeneralMsgHandler> handlers;

    public GeneralInterface(ClockPort owner) {
        this.mOwner = owner;
        this.handlers = new ArrayList<>();
    }

    public void RegisterHandler(IGeneralMsgHandler handler) {
        this.handlers.add(handler);
    }

    public PortDataSet GetPortDataSet() {
        return this.mOwner.portDataSet;
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
    }

    /* Recibe un mensaje administrativo de un nodo externo */
    @Override
    public void InAnnounce(MsgManagement mngMsg) {
        for (IGeneralMsgHandler handler : handlers) {
            handler.ProcessMessage(mngMsg);
        }
    }
}
