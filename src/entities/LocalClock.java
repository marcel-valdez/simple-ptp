/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import data.structs.TimeRepresentation;
import data.types.DataValue;
import data.types.Int32;
import data.types.UInt32;
import timestamp.TimeStamp;

/**
 * Tiene la responsabilidad de generar la 'hora' local, aceptando modificaciones
 * a su offset de tiempo y posiblemente a la rapidez (oscilación) del mismo
 * @author Marcel
 */
public class LocalClock {

    long mOffSet = 0;
    long startTime = 0;
    TimeStamp tStamp;
    private boolean useTicks;

    public LocalClock(boolean useTicks) {
        tStamp = new TimeStamp();
        startTime = System.currentTimeMillis();
        this.useTicks = useTicks;
        if (useTicks) {
            tStamp.Start();
        }
    }

    /**
     * Sets the clock's offset in nanoseconds
     * @param nanos amount of nanoseconds to set off 
     */
    public void setOffset(long nanos) {
        this.mOffSet = nanos / 1000L;
    }

    /**
     * Gets the offset of this clock in nanoseconds
     * @return the amount of nano seconds the clock is offset
     */
    public long getOffset() {
        return this.mOffSet * 1000L;
    }

    public long getTimeMillis() {
        long time = System.currentTimeMillis();
        if (this.useTicks) {
            time = this.startTime + tStamp.GetElapsedMillis() + this.mOffSet;
        }

        return time;
    }

    public TimeRepresentation getTime() {
        long currTime = this.getTimeMillis();
        long nanos = (currTime % 1000) * 1000;
        long seconds = currTime / 1000;
        TimeRepresentation time = new TimeRepresentation();
        time.seconds = new UInt32(DataValue.ToData((int) seconds));
        time.nanoseconds = new Int32(DataValue.ToData((int) nanos));

        return time;
    }

    /**
     * Gets the accuracy in nanoseconds
     * @return nanoseconds of accuracy
     */
    public static long GetAccuracy() {
        return (1000L * 1000L * 1000L) / TimeStamp.GetFrequency();
    }

    public boolean IsHighResolution() {
        return this.useTicks;
    }
}