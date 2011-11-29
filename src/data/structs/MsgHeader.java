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

    public static final MsgHeader Empty = new MsgHeader();
    public UInt16 versionPTP = new UInt16((byte) 0x0, (byte) 0x2);
    public UInt16 versionNetwork = new UInt16((byte) 0x0, (byte) 0x2);
    public Octet[] subdomain = Octet.Array(Constants.PTP_SUBDOMAIN_NAME_LENGTH);
    public UInt8 messageType = UInt8.Zero;
    public UInt8 sourceCommunicationTechnology = UInt8.Zero;
    public Octet[] sourceUuid = Octet.Array(Constants.PTP_UUID_LENGTH);
    public UInt16 sourcePortId = UInt16.Zero;
    public UInt16 sequenceId = UInt16.Zero;
    public UInt8 control = UInt8.Zero;
    public Octet[] flags = Octet.Array(2);
}
