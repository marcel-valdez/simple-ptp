/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;

import data.types.UInt16;

/**
 *
 * @author Marcel
 */
public final class MsgFollowUp extends DataStruct {
    
    public static final MsgFollowUp Empty = new MsgFollowUp();
    public UInt16 associatedSequenceId = UInt16.Zero;
    public TimeRepresentation preciseOriginTimestamp = new TimeRepresentation();
}