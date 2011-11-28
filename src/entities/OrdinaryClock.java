/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.global.NetNodesRepository;
import data.structs.CurrentDataSet;
import data.structs.DefaultDataSet;
import data.structs.ParentDataSet;
import data.structs.TimeInternal;
import data.types.UInt16;
import data.types.UInt8;

/**
 * Es un reloj ordinario, el único tipo del cuál habrá en el sistema
 * @author Marcel
 */
public class OrdinaryClock {

    /*
     * El dataSet actual 
     */
    CurrentDataSet currentDataSet;
    /*
     * El dataSet del padre (masterclock) 
     */
    ParentDataSet parentDataSet;
    /*
     * Registro de tiempo del reloj (según yo debe ser un getter) 
     */
    TimeInternal timePropertiesDataSet;
    /*
     * El dataSet default del reloj (al inicializar) 
     */
    DefaultDataSet defaultDataSet;
    /* 
     * El puerto por el cuál se comunica el reloj 
     */
    ClockPort clockPort;
    /*
     * El reloj local, sincronizado en caso de que no sea maestro este reloj 
     */
    LocalClock clock;
    /* 
     * Prioridad del reloj 
     */
    UInt8 priority1;
    /* 
     * Desempate de prioridad del reloj 
     */
    UInt8 priority2;
    /* 
     * Clase de reloj (definido por el usuario) 
     */
    ClockClass clockClass;
    /* 
     * Calidad de la fuente de tiempo del reloj (si este es el gran-maestro,
     * entonces es la calidad del reloj local, sino, es la calidad del reloj
     * maestro de este reloj )*/
    TimeSource timeSource;
    /* 
     * El número de puertos siempre será 1, ya que
     * no habrá boundary clocks 
     */
    UInt16 numberPorts = new UInt16((byte) 1);
    /* 
     * El motor del protocolo, encargado de hacer las trancisiones de estado
     * del reloj PTP
     */
    PTPEngine engine;
    /* 
     * Contiene los nodos de la red, este objeto se actualiza automáticamente
     * a través de la red, no requiere de intervención interna para actualizarse 
     */
    NetNodesRepository networkNodes;
}
