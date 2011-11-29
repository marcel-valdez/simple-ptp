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
public final class TimeInternal extends DataStruct{
    
    public static final TimeInternal Empty = new TimeInternal();
    public Int32 seconds = Int32.Zero;
    public Int32 nanoseconds = Int32.Zero;
}
