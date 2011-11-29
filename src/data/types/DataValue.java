/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.types;

import java.lang.reflect.Array;

/**
 *
 * @author Marcel
 */
public abstract class DataValue<T> {
    // Se deben guardar los datos así: 
    // si viene un número: 1011 1100 0011 1011
    // Se debe guardar:
    // dataValue[0] = 1011, dataValue[1] = 0011, dataValue[2] = 1100, dataValue[3] = 1011

    protected byte[] dataValue;
    public final int Length;

    public DataValue(byte[] value) {
        this.Length = value.length * 8;
        this.dataValue = value;
    }

    protected DataValue(byte[] value, int length) {
        this.Length = length;
        this.dataValue = value;
    }

    protected long ToUValue(int bytes) {
        long value = 0;
        for (int i = 0; i < bytes; i++) {
            // value += ((long) (this.dataValue[i] & 0xFF)) << (i * 8);
            value = (value << 8) | (this.dataValue[i] & 0xFF);
        }

        return value;
    }

    protected long ToValue(int bytes) {
        int length = this.dataValue.length;
        boolean isNegative = this.dataValue[0] < 0;
        long value = isNegative ? 0xffffffffffffffffl : 0;
        for (int i = 0; i < bytes; i++) {
            if (i < (bytes - length)) {
                value = (value << 8) | (isNegative ? 0xFFL : 0x00L);
            } else {
                value = (value << 8) | (this.dataValue[i - (bytes - length)] & 0xFFL);
            }
        }

        return value;
    }

    public byte[] serialize() {
        byte[] copy = new byte[this.dataValue.length];
        System.arraycopy(this.dataValue, 0, copy, 0, copy.length);
        return copy;
    }

    public static byte[] ToData(long value) {
        byte[] data = new byte[8];

        data[0] = (byte) (value >> 56);
        value = value & 0x00FFFFFFFFFFFFFFl;
        data[1] = (byte) (value >> 48);
        value = value & 0x0000FFFFFFFFFFFFl;
        data[2] = (byte) (value >> 40);
        value = value & 0x000000FFFFFFFFFFl;
        data[3] = (byte) (value >> 32);
        value = value & 0x00000000FFFFFFFFl;
        data[4] = (byte) (value >> 24);
        value = value & 0x0000000000FFFFFFl;
        data[5] = (byte) (value >> 16);
        value = value & 0x000000000000FFFFl;
        data[6] = (byte) (value >> 8);
        value = value & 0x00000000000000FFl;
        data[7] = (byte) value;

        return data;
    }

    public static byte[] ToData(int value) {
        byte[] data = new byte[4];

        data[0] = (byte) (value >> 24);
        value = value & 0x00FFFFFF;
        data[1] = (byte) (value >> 16);
        value = value & 0x0000FFFF;
        data[2] = (byte) (value >> 8);
        value = value & 0x000000FF;
        data[3] = (byte) value;

        return data;
    }

    public static byte[] ToData(short value) {
        byte[] data = new byte[2];

        data[0] = (byte) (value >> 8);
        value = (short) (value & 0x00FF);
        data[1] = (byte) value;

        return data;
    }

    public boolean equals(DataValue data) {
        return this.getValue() == data.getValue();
    }

    public abstract T getValue();
}
