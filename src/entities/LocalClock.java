/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import data.structs.TimeRepresentation;
import data.types.DataValue;
import data.types.Int32;
import data.types.UInt32;

/**
 * Tiene la responsabilidad de generar la 'hora' local, aceptando modificaciones
 * a su offset de tiempo y posiblemente a la rapidez (oscilación) del mismo
 * @author Marcel
 */
class LocalClock {

    long mOffSet = 0;

    public void setOffset(long offset) {
        this.mOffSet = offset;
    }

    public long getOffset() {
        return this.mOffSet;
    }

    public long getTime() {
        return System.nanoTime() + this.mOffSet;
    }
    
    public TimeRepresentation getTimeRepr() {
        long nanos = System.nanoTime() + this.mOffSet;
        TimeRepresentation time = new TimeRepresentation();
        time.seconds = new UInt32(DataValue.ToData((int)(nanos / 1000000000)));
        time.nanoseconds = new Int32(DataValue.ToData((int)(nanos % 1000000000)));
        
        return time;
    }
}