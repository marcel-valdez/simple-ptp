/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;

import data.UInt16;

/**
 *
 * @author Marcel
 */
public final class MsgFollowUp extends DataStruct {
    UInt16 associatedSequenceId = UInt16.Zero;
    TimeRepresentation preciseOriginTimestamp = new TimeRepresentation();
}