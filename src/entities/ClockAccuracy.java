/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import data.structs.TimeRepresentation;
import data.types.UInt8;

/**
 *
 * @author Marcel
 */
public class ClockAccuracy extends UInt8 {

    public static final ClockAccuracy Nano25 = new ClockAccuracy(0x20);
    public static final ClockAccuracy Nano100 = new ClockAccuracy(0x21);
    public static final ClockAccuracy Nano250 = new ClockAccuracy(0x22);
    public static final ClockAccuracy Micro1 = new ClockAccuracy(0x23);
    public static final ClockAccuracy Micro2p5 = new ClockAccuracy(0x24);
    public static final ClockAccuracy Micro10 = new ClockAccuracy(0x25);
    public static final ClockAccuracy Micro25 = new ClockAccuracy(0x26);
    public static final ClockAccuracy Micro100 = new ClockAccuracy(0x27);
    public static final ClockAccuracy Micro250 = new ClockAccuracy(0x28);
    public static final ClockAccuracy Milli1 = new ClockAccuracy(0x29);
    public static final ClockAccuracy Milli2p5 = new ClockAccuracy(0x2A);
    public static final ClockAccuracy Milli10 = new ClockAccuracy(0x2B);
    public static final ClockAccuracy Milli25 = new ClockAccuracy(0x2C);
    public static final ClockAccuracy Milli100 = new ClockAccuracy(0x2D);
    public static final ClockAccuracy Milli250 = new ClockAccuracy(0x2E);
    public static final ClockAccuracy Second1 = new ClockAccuracy(0x2F);
    public static final ClockAccuracy Second10 = new ClockAccuracy(0x30);
    public static final ClockAccuracy SecondGT10 = new ClockAccuracy(0x31);

    public ClockAccuracy(byte... arg) {
        super(arg);
    }

    public ClockAccuracy(int hexValue) {
        super((byte) hexValue);
    }

    public static ClockAccuracy GetAccuracy(TimeRepresentation time) {
        long nanos = time.nanoseconds.getValue();
        long seconds = time.seconds.getValue();
        
        if (seconds == 1) {
            return Second1;
        } else if (seconds < 10) {
            // Cualquier número menor a 10 segundos, pero mayor a 1 segundo,
            // es Long.MAX_VALUE - 1
            return Second10;
        } else if (seconds > 10) {
            // Cualquier número que resulte mayor a 10 segundos, es
            // Long.MAX_VALUE
            return SecondGT10;
        }
        
        if (nanos <= 25) {
            return Nano25;
        } else if (nanos <= 100) {
            return Nano100;
        } else if (nanos <= 250) {
            return Nano250;
        } else if (nanos <= 1000) {
            return Micro1;
        } else if (nanos <= 2500) {
            return Micro2p5;
        } else if (nanos <= 10000) {
            return Micro10;
        } else if (nanos <= 25000) {
            return Micro25;
        } else if (nanos <= 100000) {
            return Micro100;
        } else if (nanos <= 250000) {
            return Micro250;
        } else if (nanos <= 1000000) {
            return Milli1;
        } else if (nanos <= 2500000) {
            return Milli2p5;
        } else if (nanos <= 10000000) {
            return Milli10;
        } else if (nanos <= 25000000) {
            return Milli25;
        } else if (nanos <= 100000000) {
            return Milli100;
        } else if (nanos <= 250000000) {
            return Milli250;
        }
        
        return SecondGT10;
    }
}
