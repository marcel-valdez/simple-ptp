/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;

import data.*;
import data.constants.Constants;

/**
 *
 * @author Marcel
 */
public final class DefaultData extends DataStruct {

    UInt8 clockCommunicationTechnology = UInt8.Zero;
    Octet[] clockUuidField = Octet.Array(Constants.PTP_UUID_LENGTH);
    //[6];
    UInt16 clockPortField = UInt16.Zero;
    UInt8 clockStratum = UInt8.Zero;
    Octet[] clockIdentifier = Octet.Array(Constants.PTP_CODE_STRING_LENGTH);
	Int16 clockVariance = Int16.Zero;
    Bool clockFollowupCapable = Bool.False;
    Bool preferred = Bool.False;
    Bool initializable = Bool.False;
    Bool externalTiming = Bool.False;
    Bool isBoundaryClock = Bool.False;
    Octet syncInterval = Octet.Zero;
    Octet[] subdomainName = Octet.Array(Constants.PTP_SUBDOMAIN_NAME_LENGTH);
    UInt16 numberPorts = UInt16.Zero;
    UInt16 numberForeignRecords = UInt16.Zero;
}
