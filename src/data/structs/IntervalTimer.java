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
public final class IntervalTimer extends DataStruct {
    
    public static final IntervalTimer Empty = new IntervalTimer();
    public Int32 interval = Int32.Zero;
    public Int32 left = Int32.Zero;
    public Bool expire = Bool.False;
}
