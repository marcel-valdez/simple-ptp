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
public final class ClockIdentity extends DataStruct {
    public static final ClockIdentity Empty = new ClockIdentity();
    public UInt8 clockCommunicationTechnology = UInt8.Zero;
    public Octet[] clockUuidField = Octet.Array(Constants.PTP_UUID_LENGTH);
    public UInt16 clockPortField = UInt16.Zero;
    public Octet[] manufacturerIdentity = Octet.Array(Constants.MANUFACTURER_ID_LENGTH);
}
