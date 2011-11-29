/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;

import data.types.UInt16;
import data.types.UInt8;
import entities.enums.ClockClass;
import entities.enums.TimeSource;

/**
 *
 * @author Marcel
 */
public final class CurrentDataSet extends DataStruct {

    public static final CurrentDataSet Empty = new CurrentDataSet();
    public UInt16 stepsRemoved = UInt16.Zero;
    public TimeRepresentation offsetFromMaster = new TimeRepresentation();
    public TimeRepresentation oneWayDelay = new TimeRepresentation();
}
