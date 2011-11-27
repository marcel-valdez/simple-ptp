/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;
import data.*;
/**
 *
 * @author Marcel
 */
public final class GlobalTime  extends DataStruct {
	TimeRepresentation localTime = new TimeRepresentation();
	Int16 currentUtcOffset = Int16.Zero;
	Bool	leap59;
	Bool	leap61;
	UInt16 epochNumber = UInt16.Zero;
}
