/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;

import data.types.Bool;
import data.types.Int16;
import data.types.Int32;
import data.types.Octet;
import data.types.UInt16;
import data.types.UInt8;
import data.constants.Constants;

/**
 *
 * @author Marcel
 */
public class MsgDelayReq extends DataStruct {

    public static final MsgDelayReq Empty = new MsgDelayReq();
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
