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
public final class GlobalTime  extends DataStruct {
        public static final GlobalTime Empty = new GlobalTime();
	TimeRepresentation localTime = new TimeRepresentation();
	Int16 currentUtcOffset = Int16.Zero;
	Bool	leap59;
	Bool	leap61;
	UInt16 epochNumber = UInt16.Zero;
}
