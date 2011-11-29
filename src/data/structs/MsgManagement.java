/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;

import data.constants.Constants;
import data.types.*;
/**
 *
 * @author Marcel
 */
public final class MsgManagement  extends DataStruct {
    
        public static final MsgManagement Empty = new MsgManagement();
	public UInt8 targetCommunicationTechnology = UInt8.Zero;
	public Octet[]	targetUuid = Octet.Array(Constants.PTP_UUID_LENGTH);
	public UInt16 targetPortId = UInt16.Zero;
	public Int16 startingBoundaryHops = Int16.Zero;
	public Int16 boundaryHops = Int16.Zero;
	public UInt8 managementMessageKey = UInt8.Zero;
	public UInt16 parameterLength = UInt16.Zero;
	public UInt16 recordKey = UInt16.Zero;
	public MsgManagementPayload payload = new MsgManagementPayload();
}
