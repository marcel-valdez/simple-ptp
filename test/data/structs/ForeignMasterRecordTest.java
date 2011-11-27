/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;

import data.Octet;
import data.UInt16;
import data.UInt8;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Marcel
 */
public class ForeignMasterRecordTest {
    
    public ForeignMasterRecordTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSerializeDeserialize() {
        ForeignMasterRecord expected = new ForeignMasterRecord();
        expected.foreign_master_communication_technology = new UInt8((byte)0);
        expected.foreign_master_port_id = new UInt16((byte)1, (byte)2);
        expected.foreign_master_syncs = new UInt16((byte)3, (byte)4);
        expected.foreign_master_uuid[0] = new Octet((byte)5);
        expected.foreign_master_uuid[1] = new Octet((byte)6);
        expected.foreign_master_uuid[2] = new Octet((byte)7);
        expected.foreign_master_uuid[3] = new Octet((byte)8);
        expected.foreign_master_uuid[4] = new Octet((byte)9);
        expected.foreign_master_uuid[5] = new Octet((byte)10);
        expected.header = new MsgHeader();
                
    }
}
