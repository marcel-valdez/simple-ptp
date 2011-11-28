/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;

import java.lang.reflect.Modifier;
import data.types.DataValue;
import java.lang.reflect.Field;
import data.types.Int32;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
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
        timer.expire = new data.types.Bool(new byte[]{1});
        timer.interval = new Int32(new byte[]{0, 0, 0, 64});
        timer.left = new Int32(new byte[]{0, 0, 0, 32});
        byte[] expResult = new byte[]{0, 0, 0, 64, 0, 0, 0, 32, 1};
        testSerializeHelper(timer, expResult);
    }

    protected void testLoadHelper(DataStruct instance, byte[] data, DataStruct expected)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, NoSuchMethodException, InvocationTargetException {
        instance.load(data);
        AssertStructsAreEqual(instance, expected);
    }

    protected void AssertStructsAreEqual(DataStruct actual, DataStruct expected) throws IllegalArgumentException, IllegalAccessException, SecurityException {
        Field[] fields = actual.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            
            if (DataStruct.class.isAssignableFrom(field.getType())) {
                DataStruct actualField = (DataStruct)field.get(actual);
                DataStruct expectedField = (DataStruct)field.get(expected);
                AssertStructsAreEqual(actualField, expectedField);
            } else if (field.getType().isArray()) {
                Object actualArray = field.get(actual);
                Object expectedArray = field.get(expected);
                int length = Array.getLength(actualArray);
                for (int i = 0; i < length; i++) {
                    DataValue actualValue = (DataValue) Array.get(actualArray, i);
                    DataValue expectedValue = (DataValue) Array.get(expectedArray, i);

                    System.out.println("  expected: " + expectedValue.getValue());
                    System.out.println("  result: " + actualValue.getValue());
                    System.out.println("");
                    assertEquals(expectedValue.getValue(), actualValue.getValue());
                }
            } else {
                DataValue actualValue = (DataValue) field.get(actual);
                DataValue expectedValue = (DataValue) field.get(expected);
                System.out.println("  expected: " + expectedValue.getValue());
                System.out.println("  result: " + actualValue.getValue());
                System.out.println("");
                assertEquals(expectedValue.getValue(), actualValue.getValue());
            }
        }
    }

    protected void testSerializeHelper(DataStruct struct, byte[] expResult) {
        DataStruct instance = struct;
        byte[] result = instance.serialize();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of load method, of class DataStruct.
     */
    @Test
    public void testLoad() throws Exception {
        System.out.println("load");
        byte[] data = new byte[]{0, 0, 0, 64, 0, 0, 0, 32, 1};
        IntervalTimer instance = new IntervalTimer();
        IntervalTimer expected = new IntervalTimer();
        expected.expire = new data.types.Bool(new byte[]{1});
        expected.interval = new Int32(new byte[]{0, 0, 0, 64});
        expected.left = new Int32(new byte[]{0, 0, 0, 32});
        testLoadHelper(instance, data, expected);
    }
}
