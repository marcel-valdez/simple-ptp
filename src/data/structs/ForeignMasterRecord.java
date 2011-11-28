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
public final class ForeignMasterRecord extends DataStruct {
    public static final ForeignMasterRecord Empty = new ForeignMasterRecord();
    UInt8 foreign_master_communication_technology = UInt8.Zero;
    Octet[] foreign_master_uuid = Octet.Array(Constants.PTP_UUID_LENGTH);
    UInt16 foreign_master_port_id = UInt16.Zero;
    UInt16 foreign_master_syncs = UInt16.Zero;
    MsgHeader header = new MsgHeader();
    MsgSync sync = new MsgSync();
}