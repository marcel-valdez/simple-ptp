/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;

import data.Bool;
import data.Octet;
import data.UInt16;
import data.UInt8;
import data.constants.Constants;

/**
 *
 * @author Marcel
 */
public final class Port extends DataStruct {

    UInt16 returnedPortNumber = UInt16.Zero;
    UInt8 portState = UInt8.Zero;
    UInt16 lastSyncEventSequenceNumber = UInt16.Zero;
    UInt16 lastGeneralEventSequenceNumber = UInt16.Zero;
    UInt8 portCommunicationTechnology = UInt8.Zero;
    Octet[] portUuidField = Octet.Array(Constants.PTP_UUID_LENGTH);
    UInt16 portIdField = UInt16.Zero;
    Bool burstEnabled = Bool.False;
    UInt8 subdomainAddressOctet[] = UInt8.Array(Constants.SUBDOMAIN_ADDRESS_LENGTH);
    UInt8 eventPortAddressOctet[] = UInt8.Array(Constants.PORT_ADDRESS_LENGTH);
    UInt8 generalPortAddressOctet[] = UInt8.Array(Constants.PORT_ADDRESS_LENGTH);
    Octet[] subdomainAddress = Octet.Array(Constants.PTP_SUBDOMAIN_NAME_LENGTH);
    Octet[] eventPortAddress = Octet.Array(Constants.PORT_ADDRESS_LENGTH);
    Octet[] generalPortAddress = Octet.Array(Constants.PORT_ADDRESS_LENGTH);
}
