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
public final class ParentDataSet  extends DataStruct {
    
        public static final ParentDataSet Empty = new ParentDataSet();
	public UInt8 parentCommunicationTechnology = UInt8.Zero;
	public Octet[]	parentUuid = Octet.Array(Constants.PTP_UUID_LENGTH);
	public UInt16 parentPortId = UInt16.Zero;
	public UInt16 parentLastSyncSequenceNumber = UInt16.Zero;
	public Bool	parentFollowupCapable = Bool.False;
	public Bool	parentExternalTiming = Bool.False;
	public Int16 parentVariance = Int16.Zero;
	public Bool	parentStats = Bool.False;
	public Int16 observedVariance = Int16.Zero;
	public Int32 observedDrift = Int32.Zero;
	public Bool	utcReasonable = Bool.False;
	public UInt8 grandmasterCommunicationTechnology = UInt8.Zero;
	public Octet[]	grandmasterUuidField = Octet.Array(Constants.PTP_UUID_LENGTH);
	public UInt16 grandmasterPortIdField = UInt16.Zero;
	public UInt8 grandmasterStratum = UInt8.Zero;
	public Octet[]	grandmasterIdentifier = Octet.Array(Constants.PTP_CODE_STRING_LENGTH);
	public Int16 grandmasterVariance = Int16.Zero;
	public Bool	grandmasterPreferred = Bool.False;
	public Bool	grandmasterIsBoundaryClock = Bool.False;
	public UInt16 grandmasterSequenceNumber = UInt16.Zero;
}
