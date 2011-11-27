/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;

import data.Int32;
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
public class DataStructTest {
    
    public DataStructTest() {
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
     * Test of serialize method, of class DataStruct.
     */
    @Test
    public void testSerialize() {
        System.out.println("serialize");
        IntervalTimer timer = new IntervalTimer();
        timer.expire = new data.Boolean(new byte[] { 1 });
        timer.interval = new Int32(new byte[] { 0, 0, 0, 64 });
        timer.left = new Int32(new byte[] { 0, 0, 0, 32 });
        
        DataStruct instance = timer;
        
        byte[] expResult = new byte[] { 0, 0, 0, 64, 0, 0, 0, 32, 1 };
        byte[] result = instance.serialize();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of load method, of class DataStruct.
     */
    @Test
    public void testLoad() throws Exception {
        System.out.println("load");
        byte[] data = new byte[] { 0, 0, 0, 64, 0, 0, 0, 32, 1 };
        IntervalTimer instance = new IntervalTimer();
        IntervalTimer expected = new IntervalTimer();
        expected.expire = new data.Boolean(new byte[] { 1 });
        expected.interval = new Int32(new byte[] { 0, 0, 0, 64 });
        expected.left = new Int32(new byte[] { 0, 0, 0, 32 });
        instance.load(data);
        
        assertEquals(expected.expire.getValue(), instance.expire.getValue());
        assertEquals(expected.interval.getValue(), instance.interval.getValue());
        assertEquals(expected.left.getValue(), instance.left.getValue());
        
    }
}
