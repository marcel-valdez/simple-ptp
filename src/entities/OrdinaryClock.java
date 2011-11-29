/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import data.structs.ClockIdentity;
import entities.enums.TimeSource;
import entities.enums.ClockClass;
import data.structs.CurrentDataSet;
import data.structs.DefaultDataSet;
import data.structs.ParentDataSet;
import data.structs.PortDataSet;
import data.structs.TimeInternal;
import data.types.Bool;
import data.types.DataValue;
import data.types.Octet;
import data.types.UInt16;
import data.types.UInt8;
import entities.factory.Factory;
import java.util.Random;

/**
 * Es un reloj ordinario, el único tipo del cuál habrá en el sistema
 * @author Marcel
 */
public class OrdinaryClock {

    private static Random random = new Random();
    /**
     * El dataSet actual 
     */
    CurrentDataSet currentDataSet;
    /**
     * El dataSet del padre (masterclock) 
     */
    ParentDataSet parentDataSet;
    /**
     * Registro de tiempo del reloj (según yo debe ser un getter) 
     */
    TimeInternal timePropertiesDataSet;
    /**
     * El dataSet default del reloj (al inicializar) 
     */
    static DefaultDataSet defaultDataSet;
    /**
     * El número de puertos siempre será 1, ya que
     * no habrá boundary clocks 
     */
    UInt16 numberPorts = new UInt16((byte) 1);
    /**
     * El motor del protocolo, encargado de hacer las trancisiones de estado
     * del reloj PTP
     */
    private final PTPEngine engine;
    
    public final ClockIdentity ClockId;

    static {
        defaultDataSet = new DefaultDataSet();

    }

    public OrdinaryClock() {
        this.ClockId = new ClockIdentity();
        ClockId.clockUuidField[0] = new Octet((byte) random.nextInt(127));
        ClockId.clockUuidField[1] = new Octet((byte) random.nextInt(127));
        ClockId.clockUuidField[2] = new Octet((byte) random.nextInt(127));
        ClockId.clockUuidField[3] = new Octet((byte) random.nextInt(127));
        PortDataSet portDS = new PortDataSet();
        portDS.burstEnabled = Bool.False;
        portDS.portUuidField[0] = new Octet((byte) random.nextInt(127));
        portDS.portUuidField[1] = new Octet((byte) random.nextInt(127));
        portDS.portUuidField[2] = new Octet((byte) random.nextInt(127));
        portDS.portUuidField[3] = new Octet((byte) random.nextInt(127));
        portDS.portIdField = new UInt16(DataValue.ToData(random.nextInt(0xFFFF)));
        this.engine = Factory.getInstance().CreateEngine(this.ClockId, portDS);
    }

    /**
     * @return the engine
     */
    public PTPEngine getEngine() {
        return engine;
    }
}
