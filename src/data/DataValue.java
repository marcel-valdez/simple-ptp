/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

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
        boolean isNegative = this.dataValue[0] < 0;
        long value = isNegative ? 0xffffffffffffffffl : 0;
        for (int i = 0; i < bytes; i++) {
            value = (value << 8) | (this.dataValue[i] & 0xFF);
        }

        return value;
    }
    
    public byte[] serialize() {
        byte[] copy = new byte[this.dataValue.length];
        System.arraycopy(this.dataValue, 0, copy, 0, copy.length);
        return copy;
    }
    
    public abstract T getValue();
}
