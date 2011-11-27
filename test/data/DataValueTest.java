/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import data.types.Int16;
import data.types.Int32;
import data.types.UInt8;
import data.types.DataValue;
import data.types.Octet;
import data.types.UInt16;
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
public class DataValueTest {
    
    public DataValueTest() {
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

    /**
     * Test of ToUValue method, of class DataValue.
     */
    @Test
    public void testAsUInt8() {
        System.out.println("AsUInt8");
        byte [] value = new byte[] { (byte)0x7c };
        DataValue instance = new UInt8(value);
        int expResult = 0x7c;
        int result = (int)instance.ToValue(1);
        System.out.println("  result: " + Integer.toHexString(result));
        assertEquals(expResult, result);        
    }

    /**
     * Test of ToValue method, of class DataValue.
     */
    @Test
    public void testAsInt8() {
        System.out.println("AsInt8");
        byte [] value = new byte[] { (byte)0xFc};
        DataValue instance = new Octet(value);
        long expResult = 0xfffffffffffffffcl;
        long result = instance.ToValue(1);
        System.out.println("  expected: " + Long.toHexString(expResult));
        System.out.println("  result: " + Long.toHexString(result));
        assertEquals(expResult, result);
    }

    /**
     * Test of ToUValue method, of class DataValue.
     */
    @Test
    public void testAsUInt16() {
        System.out.println("AsUInt16");
        byte [] value = new byte[] { 4, 4 };
        DataValue instance = new UInt16(value);
        long expResult = 0x00000404;
        long result = instance.ToUValue(2);
        System.out.println("  expected: " + Long.toHexString(expResult));
        System.out.println("  result: " + Long.toHexString(result));
        assertEquals(expResult, result);
    }
    
    @Test
    public void testAsInt16() {
        System.out.println("AsInt16");
        byte [] value = new byte[] { (byte)0xf4, 0x04 };
        DataValue instance = new Int16(value);
        long expResult = 0xfffffffffffff404l;
        long result = instance.ToValue(2);
        System.out.println("  expected: " + Long.toHexString(expResult));
        System.out.println("  result: " + Long.toHexString(result));
        assertEquals(expResult, result);
    }

    @Test
    public void testAsInt32() {
        System.out.println("AsInt32");
        byte [] value = new byte[] { (byte)0xff, (byte)0xff, (byte)0xf4, 0x04 };
        DataValue instance = new Int32(value);
        long expResult = 0xfffffffffffff404l;
        long result = instance.ToValue(4);
        System.out.println("  expected: " + Long.toHexString(expResult));
        System.out.println("  result: " + Long.toHexString(result));
        assertEquals(expResult, result);
    }
}
