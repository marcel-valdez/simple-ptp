/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;

/**
 *
 * @author Marcel
 */
public final class MsgTmp  extends DataStruct {
	MsgSync	sync = new MsgSync();
	MsgFollowUp follow = new MsgFollowUp();
	MsgDelayReq req = new MsgDelayReq();
	MsgDelayResp resp = new MsgDelayResp();
	MsgManagement manage = new MsgManagement();
}
