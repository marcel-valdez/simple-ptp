/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.interfaces.IOutEventInterface;
import entities.interfaces.IInEventInterface;
import entities.interfaces.IEventMsgHandler;
import data.structs.MsgDelayReq;
import data.structs.MsgDelayResp;
import data.structs.MsgSync;
import data.structs.PortDataSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcel
 */
class EventInterface implements IInEventInterface, IOutEventInterface {

    private ClockPort mOwner;
    private List<IEventMsgHandler> handlers;
    private List<PortDataSet> clockPorts;

    public EventInterface(ClockPort owner) {
        this.mOwner = owner;
        this.handlers = new ArrayList<>();
        this.clockPorts = new ArrayList<>();
    }

    public PortDataSet GetPortDataSet() {
        return this.mOwner.portDataSet;
    }
    
    /* Envía el mensaje delay request por la red */
    @Override
    public void OutDelayRequest(MsgDelayReq delayRequest) {
    }

    /* Envía el mensaje de sincronización por la red */
    @Override
    public void OutSync(MsgSync sync) {
    }

    /* Recibe el mensaje delay request por la red */
    @Override
    public void InDelayRequest(MsgDelayReq delayRequest) {
        for (IEventMsgHandler handler : handlers) {
            handler.ProcessDelayReq(delayRequest);
        }
    }

    /* Recibe el mensaje delay request por la red */
    @Override
    public void OutDelayResponse(MsgDelayResp delayResponse) {
    }

    /* Recibe el mensaje de sincronización por la red */
    @Override
    public void InSync(MsgSync sync) {
        for (IEventMsgHandler handler : handlers) {
            handler.ProcessSync(sync);
        }
    }

    @Override
    public void InDelayResp(MsgDelayResp delayResponse) {
        for (IEventMsgHandler handler : handlers) {
            handler.ProcessDelayResp(delayResponse);
        }
    }
}
