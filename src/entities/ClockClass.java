/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import data.types.UInt8;

/**
 * Va a ser un enumerador, básicamente, con la clase de relojes disponibles
 * @author Marcel
 */
public class ClockClass extends UInt8 {

    // Start master clocks
    /*
     * Shall designate a clock that is synchronized to a primary reference time source. The timescale distributed shall 
     * be PTP. A clockClass 6 clock shall not be a slave to another clock in the domain. 
     */
    public static final ClockClass PrimarySync = new ClockClass(6);
    /*
     * Shall designate a clock that has previously been designated as clockClass 6 but that has lost the ability to 
     * synchronize to a primary reference time source and is in holdover mode and within holdover specifications. The 
     * timescale distributed shall be PTP. A clockClass 7 clock shall not be a slave to another clock in the domain.
     */
    public static final ClockClass LostPrimarySync = new ClockClass(7);
    /*
     * Shall designate a clock that is synchronized to an application-specific source of time. The timescale distributed 
     * shall be ARB. A clockClass 13 clock shall not be a slave to another clock in the domain.
     */
    public static final ClockClass SpecificTimeSource = new ClockClass(13);
    /*
     * Shall designate a clock that has previously been designated as clockClass 13 but that has lost the ability to 
     * synchronize to an application-specific source of  time and is in holdover mode and within holdover 
     * specifications. The timescale distributed shall be ARB.  A clockClass 14 clock shall not be a slave to another 
     * clock in the domain.
     */
    public static final ClockClass LostSpecificTimeSource = new ClockClass(14);
    /*
     * Degradation alternative A for a clock of clockClass  7 that is not within holdover specification. A clock of 
     * clockClass 52 shall not be a slave to another clock in the domain.
     */
    public static final ClockClass DegradationAlternativeA = new ClockClass(52);
    /*
     * Degradation alternative A for a clock of clockClass  14 that is not within holdover specification. A clock of 
     * clockClass 58 shall not be a slave to another clock in the domain. 
     */
    public static final ClockClass DegradationAlternativeA2 = new ClockClass(58);
    // Start slave clocks
    /*
     * Degradation alternative B for a clock of clockClass  7 that is not within holdover specification. A clock of 
     * clockClass 187 may be a slave to another clock in the domain
     */
    public static final ClockClass DegradationAlternativeB = new ClockClass(187);
    /*
     * Degradation alternative B for a clock of clockClass 14 that is not within holdover specification. A clock of 
     * clockClass 193 may be a slave to another clock in the domain.
     */
    public static final ClockClass DegradationAlternativeB2 = new ClockClass(193);
    // Default. This clockClass shall be used if none of the other clockClass definitions apply.
    public static final ClockClass Default = new ClockClass(248);
    // Slave only clock, can't turn into master clock
    public static final ClockClass SlaveOnly = new ClockClass(255);

    public ClockClass(byte... args) {
        super(args);
    }

    public ClockClass(int hexValue) {
        super((byte) hexValue);
    }
}
