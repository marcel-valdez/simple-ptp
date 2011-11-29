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
    public UInt8 foreign_master_communication_technology = UInt8.Zero;
    public Octet[] foreign_master_uuid = Octet.Array(Constants.PTP_UUID_LENGTH);
    public UInt16 foreign_master_port_id = UInt16.Zero;
    public UInt16 foreign_master_syncs = UInt16.Zero;
    public MsgHeader header = new MsgHeader();
    public MsgSync sync = new MsgSync();
}