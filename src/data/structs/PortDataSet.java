/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;

import data.types.Bool;
import data.types.Octet;
import data.types.UInt16;
import data.types.UInt8;
import data.constants.Constants;
import entities.enums.PortState;

/**
 *
 * @author Marcel
 */
public final class PortDataSet extends DataStruct {

    public static final PortDataSet Empty = new PortDataSet();
    public UInt16 returnedPortNumber = UInt16.Zero;
    public UInt8 portState = PortState.INITIALIZING;
    public UInt16 lastSyncEventSequenceNumber = UInt16.Zero;
    public UInt16 lastGeneralEventSequenceNumber = UInt16.Zero;
    public UInt8 portCommunicationTechnology = UInt8.Zero;
    public Octet[] portUuidField = Octet.Array(Constants.PTP_UUID_LENGTH);
    public UInt16 portIdField = UInt16.Zero;
    public Bool burstEnabled = Bool.False;
    public UInt8 subdomainAddressOctet[] = UInt8.Array(Constants.SUBDOMAIN_ADDRESS_LENGTH);
    public UInt8 eventPortAddressOctet[] = UInt8.Array(Constants.PORT_ADDRESS_LENGTH);
    public UInt8 generalPortAddressOctet[] = UInt8.Array(Constants.PORT_ADDRESS_LENGTH);
    public Octet[] subdomainAddress = Octet.Array(Constants.PTP_SUBDOMAIN_NAME_LENGTH);
    public Octet[] eventPortAddress = Octet.Array(Constants.PORT_ADDRESS_LENGTH);
    public Octet[] generalPortAddress = Octet.Array(Constants.PORT_ADDRESS_LENGTH);
}
