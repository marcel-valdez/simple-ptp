/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.constants;

/**
 *
 * @author Marcel
 */
public final class Constants {

    public static final String MANUFACTURER_ID = "PTPD;1.2\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";
    public static final int DEFAULT_SYNC_INTERVAL = 1;
    public static final int DEFAULT_UTC_OFFSET = 0;
    public static final int DEFAULT_CLOCK_VARIANCE = (-4000);
    public static final int DEFAULT_CLOCK_STRATUM = 4;
    public static final int DEFAULT_INBOUND_LATENCY = 0;	/* in nsec */

    public static final int DEFAULT_OUTBOUND_LATENCY = 0;	/* in nsec */

    public static final boolean DEFAULT_NO_RESET_CLOCK = false;
    public static final int DEFAULT_AP = 10;
    public static final int DEFAULT_AI = 1000;
    public static final int DEFAULT_DELAY_S = 6;
    public static final int DEFAULT_MAX_FOREIGN_RECORDS = 5;

    /* features, only change to refelect changes in implementation */
    public static final boolean CLOCK_FOLLOWUP = true;
    public static final boolean INITIALIZABLE = true;
    public static final boolean BURST_ENABLED = false;
    public static final boolean EXTERNAL_TIMING = false;
    public static final boolean BOUNDARY_CLOCK = false;
    public static final int NUMBER_PORTS = 1;
    public static final int VERSION_PTP = 1;
    public static final int VERSION_NETWORK = 1;

    /* spec defined constants  */
    public static final String DEFAULT_PTP_DOMAIN_NAME = "_DFLT\0\0\0\0\0\0\0\0\0\0\0";
    public static final String ALTERNATE_PTP_DOMAIN1_NAME = "_ALT1\0\0\0\0\0\0\0\0\0\0\0";
    public static final String ALTERNATE_PTP_DOMAIN2_NAME = "_ALT2\0\0\0\0\0\0\0\0\0\0\0";
    public static final String ALTERNATE_PTP_DOMAIN3_NAME = "_ALT3\0\0\0\0\0\0\0\0\0\0\0";
    public static final String IDENTIFIER_ATOM = "ATOM";
    public static final String IDENTIFIER_GPS = "GPS\0";
    public static final String IDENTIFIER_NTP = "NTP\0";
    public static final String IDENTIFIER_HAND = "HAND";
    public static final String IDENTIFIER_INIT = "INIT";
    public static final String IDENTIFIER_DFLT = "DFLT";

    /* ptp constants */
    public static final int PTP_UUID_LENGTH = 4;
    public static final int PTP_CODE_STRING_LENGTH = 4;
    public static final int PTP_SUBDOMAIN_NAME_LENGTH = 16;
    public static final int PTP_MAX_MANAGEMENT_PAYLOAD_SIZE = 90;
    public static final int PTP_DELAY_REQ_INTERVAL = 30;
    public static final int PTP_FOREIGN_MASTER_THRESHOLD = 2;
    /* no support for intervals less than one */
    public static final int PTP_RANDOMIZING_SLOTS = 18;
    public static final int PTP_LOG_VARIANCE_THRESHOLD = 256;
    public static final int PTP_LOG_VARIANCE_HYSTERESIS = 128;
    /* used in spec but not named */
    public static final int MANUFACTURER_ID_LENGTH = 48;
    
    /* UDP/IPv4 dependent */
    public static final int SUBDOMAIN_ADDRESS_LENGTH = 4;
    public static final int PORT_ADDRESS_LENGTH = 2;
    public static final int PACKET_SIZE = 300;
    public static final int PTP_EVENT_PORT = 319;
    public static final int PTP_GENERAL_PORT = 320;
    public static final String DEFAULT_PTP_DOMAIN_ADDRESS = "192.168.0.129";
    public static final String DEFAULT_PTP_DOMAIN_ADDRESS1 = "192.168.0.130";
    public static final String DEFAULT_PTP_DOMAIN_ADDRESS2 = "192.168.0.131";
    public static final String DEFAULT_PTP_DOMAIN_ADDRESS3 = "192.168.0.132";
    public static final int HEADER_LENGTH = 40;
    public static final int SYNC_PACKET_LENGTH = 124;
    public static final int DELAY_REQ_PACKET_LENGTH = 124;
    public static final int FOLLOW_UP_PACKET_LENGTH = 52;
    public static final int DELAY_RESP_PACKET_LENGTH = 60;
    public static final int MANAGEMENT_PACKET_LENGTH = 136;
    public static final int MM_STARTING_BOUNDARY_HOPS = 0x7fff;

    public static int PTP_SYNC_INTERVAL_TIMEOUT(int x) {
        return (1 << ((x) < 0 ? 1 : (x)));
    }

    public static int PTP_SYNC_RECEIPT_TIMEOUT(int x) {
        return (10 * (1 << ((x) < 0 ? 0 : (x))));
    }

    public static int PTP_FOREIGN_MASTER_TIME_WINDOW(int x) {
        return (4 * (1 << ((x) < 0 ? 0 : (x))));
    }
}
