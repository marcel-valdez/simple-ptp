/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;

import data.DataValue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcel
 */
public class DataStruct {
    public byte[] serialize() {
        Field[] fields = this.getClass().getDeclaredFields();
        byte[] buffer = new byte[512];
        int length = 0;
        for(Field field : fields) {
            try {
                DataValue value = (DataValue)field.get(this);
                byte[] data = value.serialize();
                System.arraycopy(data, 0, buffer, length, data.length);
                length += data.length;
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(DataStruct.class.getName()).log(Level.WARNING, "Expected all fields to be DataValue", ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(DataStruct.class.getName()).log(Level.WARNING, "Expected all fields to be DataValue", ex);
            }
        }
        
        byte[] result = new byte[length];
        System.arraycopy(buffer, 0, result, 0, length);
        
        return result;
    }
    
    public void load (byte[] data) throws NoSuchMethodException, InstantiationException, InvocationTargetException {
        Field[] fields = this.getClass().getDeclaredFields();
        ByteBuffer buffer = ByteBuffer.wrap(data);
        
        for(Field field : fields) {
            try {
                DataValue value = (DataValue)field.get(this);
                int length = value.Length / 8;
                byte[] valueData = new byte[length];
                buffer.get(valueData, 0, length);
                Constructor<?> ctor = field.getType().getConstructors()[0];
                
                field.set(this, ctor.newInstance(valueData));
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(DataStruct.class.getName()).log(Level.WARNING, "Expected all fields to be DataValue", ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(DataStruct.class.getName()).log(Level.WARNING, "Expected all fields to be DataValue", ex);
            }
        }
    }
}
