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
public final class TimeRepresentation extends DataStruct {

    public static final TimeRepresentation Empty = new TimeRepresentation();
    public UInt32 seconds = UInt32.Zero;
    public Int32 nanoseconds = Int32.Zero;

    public TimeRepresentation() {
    }

    public TimeRepresentation(long nanos) {
        this.seconds = new UInt32(DataValue.ToData(nanos / 1000000000L));
        this.nanoseconds = new Int32(DataValue.ToData(nanos % 1000000000L));
    }

    public long toNanos() {
        long result = this.seconds.getValue() * 1000000000L;
        result += this.nanoseconds.getValue();
        return result;
    }
}
