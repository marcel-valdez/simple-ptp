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
public final class MsgHeader extends DataStruct {
    	UInt16 versionPTP = new UInt16((byte)0x0, (byte)0x2);
	UInt16 versionNetwork = new UInt16((byte) 0x0, (byte)0x2);
	Octet[]	subdomain = Octet.Array(Constants.PTP_SUBDOMAIN_NAME_LENGTH);
	UInt8 messageType = UInt8.Zero;
	UInt8 sourceCommunicationTechnology = UInt8.Zero;
	Octet[]	sourceUuid = Octet.Array(Constants.PTP_UUID_LENGTH);
	UInt16 sourcePortId = UInt16.Zero;
	UInt16 sequenceId = UInt16.Zero;
	UInt8 control = UInt8.Zero;
	Octet[]	flags = Octet.Array(2);
}
