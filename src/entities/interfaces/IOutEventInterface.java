/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.interfaces;

import data.structs.MsgDelayReq;
import data.structs.MsgDelayResp;
import data.structs.MsgSync;

/**
 *
 * @author Marcel
 */
public interface IOutEventInterface {

    MsgDelayResp OutDelayRequest(MsgDelayReq delayRequest);

    void OutSync(MsgSync sync);
}
