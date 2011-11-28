/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.structs;

import data.types.DataValue;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
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
        byte[] buffer = new byte[1024];
        int length = 0;
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            try {
                byte[] data = null;
                Object fieldValue = field.get(this);
                if (DataStruct.class.isAssignableFrom(field.getType())) {
                    data = ((DataStruct) fieldValue).serialize();
                } else {
                    if (fieldValue.getClass().isArray()) {
                        int arrayLength = Array.getLength(fieldValue);
                        int typeLength = ((DataValue) Array.get(fieldValue, 0)).Length;
                        data = new byte[arrayLength * (typeLength / 8)];
                        for (int i = 0; i < arrayLength; i++) {
                            DataValue dataValue = (DataValue) Array.get(fieldValue, i);
                            byte[] serialized = dataValue.serialize();
                            System.arraycopy(serialized, 0, data, i * (typeLength / 8), serialized.length);
                        }
                    } else {
                        DataValue value = (DataValue) fieldValue;
                        data = value.serialize();
                    }
                }

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

    public void load(byte[] data) throws NoSuchMethodException, InstantiationException, InvocationTargetException {
        Field[] fields = this.getClass().getDeclaredFields();
        ByteBuffer buffer = ByteBuffer.wrap(data);

        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            
            try {
                if (DataStruct.class.isAssignableFrom(field.getType())) {
                    DataStruct objectValue = (DataStruct) field.get(this);
                    int structLength = objectValue.serialize().length;
                    byte[] bufferData = new byte[structLength];
                    buffer.get(bufferData, 0, structLength);
                    objectValue.load(bufferData);
                } else {
                    if (field.getType().isArray()) {
                        Object fieldArray = field.get(this);
                        DataValue dataValueType = (DataValue) Array.get(fieldArray, 0);
                        int dataValueLength = dataValueType.Length / 8;
                        Constructor<?> dataValueCtor = dataValueType.getClass().getConstructors()[0];

                        for (int i = 0; i < Array.getLength(fieldArray); i++) {
                            dataValueLength = dataValueLength == 0 ? 1 : dataValueLength;
                            byte[] dataValueBytes = new byte[dataValueLength];
                            buffer.get(dataValueBytes, 0, dataValueLength);
                            Array.set(fieldArray, i, dataValueCtor.newInstance(dataValueBytes));
                        }
                    } else {
                        DataValue value = (DataValue) field.get(this);
                        int length = value.Length / 8;
                        length = length == 0 ? 1 : length;
                        byte[] valueData = new byte[length];
                        buffer.get(valueData, 0, length);
                        Constructor<?> ctor = field.getType().getConstructors()[0];
                        field.set(this, ctor.newInstance(valueData));
                    }
                }
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(DataStruct.class.getName()).log(Level.WARNING, "Expected all fields to be DataValue", ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(DataStruct.class.getName()).log(Level.WARNING, "Expected all fields to be DataValue", ex);
            }
        }
    }
}
