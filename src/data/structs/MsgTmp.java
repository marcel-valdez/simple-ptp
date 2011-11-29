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
    
        public static final MsgTmp Empty = new MsgTmp();
	public MsgSync	sync = new MsgSync();
	public MsgFollowUp follow = new MsgFollowUp();
	public MsgDelayReq req = new MsgDelayReq();
	public MsgDelayResp resp = new MsgDelayResp();
	public MsgManagement manage = new MsgManagement();
}
