/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;
/**
 *
 * @author Marcel
 */
public final class MsgManagementPayload extends DataStruct {
    
        public static final MsgManagementPayload Empty = new MsgManagementPayload();
	ClockIdentity clockIdentity = new ClockIdentity();
	DefaultDataSet defaultData = new DefaultDataSet();
	CurrentDataSet current = new CurrentDataSet();
	ParentDataSet parent = new ParentDataSet();
	PortDataSet port = new PortDataSet();
	GlobalTime globalTime = new GlobalTime();
	Foreign foreign = new Foreign();
}