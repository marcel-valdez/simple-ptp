/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;

import data.constants.Constants;
import data.*;
/**
 *
 * @author Marcel
 */
public final class MsgManagement  extends DataStruct {
	UInt8 targetCommunicationTechnology = UInt8.Zero;
	Octet[]	targetUuid = Octet.Array(Constants.PTP_UUID_LENGTH);
	UInt16 targetPortId = UInt16.Zero;
	Int16 startingBoundaryHops = Int16.Zero;
	Int16 boundaryHops = Int16.Zero;
	UInt8 managementMessageKey = UInt8.Zero;
	UInt16 parameterLength = UInt16.Zero;
	UInt16 recordKey = UInt16.Zero;

	MsgManagementPayload payload = new MsgManagementPayload();
}
