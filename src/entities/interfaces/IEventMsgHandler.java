/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.interfaces;

import data.structs.MsgDelayReq;
import data.structs.MsgDelayResp;
import data.structs.MsgFollowUp;
import data.structs.MsgSync;

/**
 * A class that implements this interface, should do necessary actions or
 * delegate such actions in order to change the state of the PTP Clock
 * @author Marcel
 */
public interface IEventMsgHandler {
    
    /* Processes a sync message */
    void ProcessSync(MsgSync message);
    
    /* Processes a delay request message */
    MsgDelayResp ProcessDelayReq(MsgDelayReq message);
    
    /* Processes a follow-up message */
    void ProcessFollowUp(MsgFollowUp message);
}
