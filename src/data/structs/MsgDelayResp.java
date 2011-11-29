/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;

import data.types.Octet;
import data.types.UInt16;
import data.types.UInt8;
import data.constants.Constants;

/**
 *
 * @author Marcel
 */
public final class MsgDelayResp extends DataStruct {

    public static final MsgDelayResp Empty = new MsgDelayResp();
    public TimeRepresentation delayReceiptTimestamp = new TimeRepresentation();
    public UInt8 requestingSourceCommunicationTechnology = UInt8.Zero;
    public Octet[] requestingSourceUuid = Octet.Array(Constants.PTP_UUID_LENGTH);
    public UInt16 requestingSourcePortId = UInt16.Zero;
    public UInt16 requestingSourceSequenceId = UInt16.Zero;
}
