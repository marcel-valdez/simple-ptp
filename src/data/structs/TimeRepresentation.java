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
public final class TimeRepresentation extends DataStruct{
        
        public static final TimeRepresentation Empty = new TimeRepresentation();
	public UInt32 seconds = UInt32.Zero;
	public Int32 nanoseconds = Int32.Zero;
}
