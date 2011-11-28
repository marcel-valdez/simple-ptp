/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import data.structs.MsgDelayReq;
import data.structs.MsgDelayResp;
import data.structs.MsgManagement;
import data.structs.MsgSync;
import data.structs.PortDataSet;
import entities.interfaces.IInEventInterface;
import entities.interfaces.IInGeneralInterface;
import entities.interfaces.IInInterface;

/**
 *
 * @author Marcel
 */
public class InInterface implements IInInterface {
    
    final private IInEventInterface mInEvent;
    final private IInGeneralInterface mInGeneral;

    public InInterface(IInEventInterface inEvent, IInGeneralInterface inGeneral) {
        this.mInEvent = inEvent;
        this.mInGeneral = inGeneral;
    }

    @Override
    public void InDelayRequest(MsgDelayReq delayRequest) {
        this.mInEvent.InDelayRequest(delayRequest);
    }
    
    @Override
    public void InSync(MsgSync sync) {
        this.mInEvent.InSync(sync);
    }
    
    @Override
    public void InDelayResp(MsgDelayResp delayResponse) {
        this.mInEvent.InDelayResp(delayResponse);
    }
    
    @Override
    public PortDataSet GetPortDataSet() {
        return this.mInEvent.GetPortDataSet();
    }
    
    @Override
    public void InAnnounce(MsgManagement mngMsg) {
        this.mInGeneral.InAnnounce(mngMsg);
    }
}
