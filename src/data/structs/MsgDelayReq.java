/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;

import data.Bool;
import data.Int16;
import data.Int32;
import data.Octet;
import data.UInt16;
import data.UInt8;
import data.constants.Constants;

/**
 *
 * @author Marcel
 */
public class MsgDelayReq {
    TimeRepresentation originTimestamp = new TimeRepresentation();
    UInt16 epochNumber = UInt16.Zero;
    Int16 currentUTCOffset = Int16.Zero;
    UInt8 grandmasterCommunicationTechnology = UInt8.Zero;
    Octet[] grandmasterClockUuid = Octet.Array(Constants.PTP_UUID_LENGTH);
    UInt16 grandmasterPortId = UInt16.Zero;
    UInt16 grandmasterSequenceId = UInt16.Zero;
    UInt8 grandmasterClockStratum = UInt8.Zero;
    Octet[] grandmasterClockIdentifier = Octet.Array(Constants.PTP_CODE_STRING_LENGTH);
    Int16 grandmasterClockVariance = Int16.Zero;
    Bool grandmasterPreferred = Bool.False;
    Bool grandmasterIsBoundaryClock = Bool.False;
    Octet syncInterval = Octet.Zero;
    Int16 localClockVariance = Int16.Zero;
    UInt16 localStepsRemoved = UInt16.Zero;
    UInt8 localClockStratum = UInt8.Zero;
    Octet[] localClockIdentifer = Octet.Array(Constants.PTP_CODE_STRING_LENGTH);
    UInt8 parentCommunicationTechnology;
    Octet[] parentUuid = Octet.Array(Constants.PTP_UUID_LENGTH);
    UInt16 parentPortField = UInt16.Zero;
    Int16 estimatedMasterVariance = Int16.Zero;
    Int32 estimatedMasterDrift = Int32.Zero;
    Bool utcReasonable = Bool.False;
}
