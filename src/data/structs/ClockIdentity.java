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

    UInt8 clockCommunicationTechnology = UInt8.Zero;
    Octet[] clockUuidField = Octet.Array(Constants.PTP_UUID_LENGTH);
    UInt16 clockPortField = UInt16.Zero;
    Octet[] manufacturerIdentity = Octet.Array(Constants.MANUFACTURER_ID_LENGTH);
}
