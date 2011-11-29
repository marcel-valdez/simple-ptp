/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;

import data.types.*;

/**
 *
 * @author Marcel
 */
public final class GlobalTime extends DataStruct {

    public static final GlobalTime Empty = new GlobalTime();
    public TimeRepresentation localTime = new TimeRepresentation();
    public Int16 currentUtcOffset = Int16.Zero;
    public Bool leap59;
    public Bool leap61;
    public UInt16 epochNumber = UInt16.Zero;
}
