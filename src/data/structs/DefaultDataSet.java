/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;

import data.types.*;
import data.constants.Constants;
import entities.LocalClock;
import entities.enums.ClockAccuracy;
import entities.enums.ClockClass;
import entities.enums.TimeSource;

/**
 *
 * @author Marcel
 */
public final class DefaultDataSet extends DataStruct {

    public static final DefaultDataSet Empty = new DefaultDataSet();
    public UInt8 clockCommunicationTechnology = UInt8.Zero;
    public Octet[] clockUuidField = Octet.Array(Constants.PTP_UUID_LENGTH);
    public UInt16 clockPortField = UInt16.Zero;
    public UInt8 clockVariance = ClockAccuracy.GetAccuracy(new TimeRepresentation(LocalClock.GetAccuracy()));
    public Bool clockFollowupCapable = Bool.False;
    public Bool preferred = Bool.False;
    public Bool initializable = Bool.False;
    public Bool externalTiming = Bool.False;
    public Bool isBoundaryClock = Bool.False;
    public Octet syncInterval = Octet.Zero;
    public Octet[] subdomainName = Octet.Array(Constants.PTP_SUBDOMAIN_NAME_LENGTH);
    public UInt16 numberPorts = UInt16.Zero;
    public UInt16 numberForeignRecords = UInt16.Zero;
    /**
     * Prioridad del reloj 
     */
    public UInt8 priority1 = new UInt8((byte) 1);
    /**
     * Desempate de prioridad del reloj 
     */
    public UInt8 priority2 = new UInt8((byte) 2);
    /**
     * Clase de reloj (definido por el usuario) 
     */
    public UInt8 clockClass = ClockClass.DegradationAlternativeB;
    /**
     * Calidad de la fuente de tiempo del reloj (si este es el gran-maestro,
     * entonces es la calidad del reloj local, sino, es la calidad del reloj
     * maestro de este reloj )
     */
    public UInt8 timeSource = TimeSource.INTERNAL_OSCILLATOR;
}
