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
public final class Parent  extends DataStruct {
	UInt8 parentCommunicationTechnology = UInt8.Zero;
	Octet[]	parentUuid = Octet.Array(Constants.PTP_UUID_LENGTH);
	UInt16 parentPortId = UInt16.Zero;
	UInt16 parentLastSyncSequenceNumber = UInt16.Zero;
	Bool	parentFollowupCapable = Bool.False;
	Bool	parentExternalTiming = Bool.False;
	Int16 parentVariance = Int16.Zero;
	Bool	parentStats = Bool.False;
	Int16 observedVariance = Int16.Zero;
	Int32 observedDrift = Int32.Zero;
	Bool	utcReasonable = Bool.False;
	UInt8 grandmasterCommunicationTechnology = UInt8.Zero;
	Octet[]	grandmasterUuidField = Octet.Array(Constants.PTP_UUID_LENGTH);
	UInt16 grandmasterPortIdField = UInt16.Zero;
	UInt8 grandmasterStratum = UInt8.Zero;
	Octet[]	grandmasterIdentifier = Octet.Array(Constants.PTP_CODE_STRING_LENGTH);
	Int16 grandmasterVariance = Int16.Zero;
	Bool	grandmasterPreferred = Bool.False;
	Bool	grandmasterIsBoundaryClock = Bool.False;
	UInt16 grandmasterSequenceNumber = UInt16.Zero;
}
