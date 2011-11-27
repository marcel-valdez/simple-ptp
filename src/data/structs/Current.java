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
public final class Current  extends DataStruct {
	UInt16 stepsRemoved = UInt16.Zero;
	TimeRepresentation offsetFromMaster = new TimeRepresentation();
	TimeRepresentation oneWayDelay = new TimeRepresentation();
}
