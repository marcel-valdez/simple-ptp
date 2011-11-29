/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.interfaces;

import data.structs.MsgDelayReq;
import data.structs.MsgDelayResp;
import data.structs.MsgSync;
import data.structs.PortDataSet;

/**
 *
 * @author Marcel
 */
public interface IInEventInterface {

    MsgDelayResp InDelayRequest(MsgDelayReq delayRequest);

    void InSync(MsgSync sync);
    
    PortDataSet GetPortDataSet();
}
