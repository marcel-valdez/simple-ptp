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
public final class Foreign extends DataStruct {

    public static final Foreign Empty = new Foreign();
    public UInt16 returnedPortNumber = UInt16.Zero;
    public UInt16 returnedRecordNumber = UInt16.Zero;
    public UInt8 foreignMasterCommunicationTechnology = UInt8.Zero;
    public Octet[] foreignMasterUuid = Octet.Array(Constants.PTP_UUID_LENGTH);
    public UInt16 foreignMasterPortId = UInt16.Zero;
    public UInt16 foreignMasterSyncs = UInt16.Zero;
}