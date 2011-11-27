/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;

import data.types.UInt16;
import data.types.Octet;
import data.types.UInt8;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Marcel
 */
public class MsgHeaderTest extends DataStructTest {

    public MsgHeaderTest() {
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
    public void testSerializeLoadMsgHeader() {
        System.out.println("Testing serialize <-> load for MsgHeader");
        MsgHeader header = new MsgHeader();
        header.control = new UInt8((byte)1);
        header.flags[0] = new Octet((byte) 2);
        header.flags[1] = new Octet((byte) 3);
        header.messageType = new UInt8((byte)4);
        header.sequenceId = new UInt16((byte)5, (byte)6);
        header.sourceCommunicationTechnology = new UInt8((byte)7);
        header.sourcePortId = new UInt16((byte) 8, (byte) 9);
        header.sourceUuid[0] = new Octet((byte) 10);
        header.sourceUuid[1] = new Octet((byte) 11);
        header.sourceUuid[2] = new Octet((byte) 12);
        header.sourceUuid[3] = new Octet((byte) 13);
        header.sourceUuid[4] = new Octet((byte) 14);
        header.sourceUuid[5] = new Octet((byte) 15);
        header.subdomain[0] = new Octet((byte) 16);
        header.subdomain[1] = new Octet((byte) 17);
        header.subdomain[2] = new Octet((byte) 18);
        header.subdomain[3] = new Octet((byte) 19);
        header.subdomain[4] = new Octet((byte) 20);
        header.subdomain[5] = new Octet((byte) 21);
        header.subdomain[6] = new Octet((byte) 22);
        header.subdomain[7] = new Octet((byte) 23);
        header.subdomain[8] = new Octet((byte) 24);
        header.subdomain[9] = new Octet((byte) 25);
        header.subdomain[10] = new Octet((byte) 26);
        header.subdomain[11] = new Octet((byte) 27);
        header.subdomain[12] = new Octet((byte) 28);
        header.subdomain[13] = new Octet((byte) 29);
        header.subdomain[14] = new Octet((byte) 30);
        header.subdomain[15] = new Octet((byte) 31);

        byte[] data = header.serialize();
        MsgHeader actual = new MsgHeader();
        try {
            actual.load(data);
            AssertStructsAreEqual(actual, header);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MsgHeaderTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MsgHeaderTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MsgHeaderTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MsgHeaderTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MsgHeaderTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MsgHeaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
