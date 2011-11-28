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
        super(new byte[] { data[0] }, 8);
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
}
