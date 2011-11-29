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
public final class MsgSync extends DataStruct {

    public static final MsgSync Empty = new MsgSync();
    public TimeRepresentation originTimestamp = new TimeRepresentation();
    public UInt16 epochNumber = UInt16.Zero;
    public Int16 currentUTCOffset = Int16.Zero;
    public UInt8 grandmasterCommunicationTechnology = UInt8.Zero;
    public Octet[] grandmasterClockUuid = Octet.Array(Constants.PTP_UUID_LENGTH);
    public UInt16 grandmasterPortId = UInt16.Zero;
    public UInt16 grandmasterSequenceId = UInt16.Zero;
    public UInt8 grandmasterClockStratum = UInt8.Zero;
    public Octet[] grandmasterClockIdentifier = Octet.Array(Constants.PTP_CODE_STRING_LENGTH);
    public Int16 grandmasterClockVariance = Int16.Zero;
    public Bool grandmasterPreferred = Bool.False;
    public Bool grandmasterIsBoundaryClock = Bool.False;
    public Octet syncInterval = Octet.Zero;
    public Int16 localClockVariance = Int16.Zero;
    public UInt16 localStepsRemoved = UInt16.Zero;
    public UInt8 localClockStratum = UInt8.Zero;
    public Octet[] localClockIdentifer = Octet.Array(Constants.PTP_CODE_STRING_LENGTH);
    public UInt8 parentCommunicationTechnology = UInt8.Zero;
    public Octet[] parentUuid = Octet.Array(Constants.PTP_UUID_LENGTH);
    public UInt16 parentPortField = UInt16.Zero;
    public Int16 estimatedMasterVariance = Int16.Zero;
    public Int32 estimatedMasterDrift = Int32.Zero;
    public Bool utcReasonable = Bool.False;
}