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
	ClockIdentity clockIdentity = new ClockIdentity();
	DefaultData defaultData = new DefaultData();
	Current current = new Current();
	Parent parent = new Parent();
	Port port = new Port();
	GlobalTime globalTime = new GlobalTime();
	Foreign foreign = new Foreign();
}