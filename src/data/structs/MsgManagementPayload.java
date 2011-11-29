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
	public ClockIdentity clockIdentity = new ClockIdentity();
	public DefaultDataSet defaultData = new DefaultDataSet();
	public CurrentDataSet current = new CurrentDataSet();
	public ParentDataSet parent = new ParentDataSet();
	public PortDataSet port = new PortDataSet();
	public GlobalTime globalTime = new GlobalTime();
	public Foreign foreign = new Foreign();
}