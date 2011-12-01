/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.types;

import java.nio.ByteBuffer;

/**
 *
 * @author Marcel
 */
public class Octet extends DataValue<Byte> {
    public static final Octet Zero = new Octet(new byte[] { 0 });
    
    public Octet(byte... data) {
        super(new byte[] { data[data.length - 1] }, 8);
    }
    
    @Override
    public Byte getValue() {
        return (byte)this.ToValue(1);
    }
    
    public static Octet[] Array(int length) {
        Octet[] result = new Octet[length];
        for(int i = 0; i < length; i++) {
            result[i] = Octet.Zero;
        }
        
        return result;
    }
    
    public static Octet[] Array(byte[] data) {
        Octet[] result = new Octet[data.length];
        for(int i = 0; i < data.length; i++) {
            result[i] = new Octet(data[i]);
        }
        
        return result;
    }
    
    public static byte[] ToValue(Octet[] data) {
        byte[] result = new byte[data.length];
        for(int i = 0; i < result.length; i++) {
            result[i] = data[i].getValue();
        }
        
        return result;
    }
    
    public static boolean Compare(Octet[] a, Octet[] b) {
        for(int i = 0; i < a.length; i++) {
            if(a[i].getValue() != b[i].getValue())
                return false;
        }
        return true;
    }
}
