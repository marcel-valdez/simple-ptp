/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import data.types.UInt8;

/**
 * Encargado de representar la clasificación del reloj local (o fuente) de donde
 * se obtiene la hora
 * @author Marcel
 */
public final class TimeSource extends UInt8 {

    /*
     * Any device, or device directly connected to such a 
     * device, that is based  on atomic resonance for 
     * frequency and that has been calibrated against 
     * international standards for frequency and, if the PTP 
     * timescale is used, time
     */
    public final static TimeSource ATOMIC_CLOCK = new TimeSource(0x10);
    /*
     * Any device synchronized to a satellite system that 
     * distribute time and frequency tied to international
     * standards
     */
    public final static TimeSource GPS = new TimeSource(0x20);
    /*
     * Any device synchronized via any of the radio 
     * distribution systems that distribute time and frequency 
     * tied to international standards
     */
    public final static TimeSource TERRESTRIAL_RADIO = new TimeSource(0x30);
    /*
     * Any device synchronized to a PTP-based source of 
     * time external to the domain 
     */
    public final static TimeSource PTP = new TimeSource(0x40);
    /*
     * Any device synchronized via NTP or Simple Network 
     * Time Protocol (SNTP) to  servers that distribute time 
     * and frequency tied to international standards 
     */
    public final static TimeSource NTP = new TimeSource(0x50);
    /*
     * Used for any device whose time has been set by means 
     * of a human interface based on observation of an 
     * international standards source of time to within the 
     * claimed clock accuracy
     */
    public final static TimeSource HAND_SET = new TimeSource(0x60);
    /*
     * Other source of time and/or frequency not covered by 
     * other values 
     */
    public final static TimeSource OTHER = new TimeSource(0x90);
    /*
     * Any device whose frequency is not based on atomic 
     * resonance nor calibrated against international 
     * standards for frequency, and whose time is based on a 
     * free-running oscillator with epoch determined in an 
     * arbitrary or unknown manner
     */
    public final static TimeSource INTERNAL_OSCILLATOR = new TimeSource(0xA0);
    public final static TimeSource RESERVED = new TimeSource(0xFF);

    public TimeSource(byte... values) {
        super(values);
    }

    public TimeSource(int hexValue) {
        super((byte) hexValue);
    }
}
