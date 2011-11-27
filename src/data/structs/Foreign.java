/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;
import data.types.*;
import data.constants.Constants;
/**
 *
 * @author Marcel
 */
public final class Foreign  extends DataStruct {
	UInt16 returnedPortNumber = UInt16.Zero;
	UInt16 returnedRecordNumber = UInt16.Zero;
	UInt8 foreignMasterCommunicationTechnology = UInt8.Zero;
	Octet[]	foreignMasterUuid = Octet.Array(Constants.PTP_UUID_LENGTH);
	UInt16 foreignMasterPortId = UInt16.Zero;
	UInt16 foreignMasterSyncs = UInt16.Zero;
}