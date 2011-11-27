/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.nio.ByteBuffer;

/**
 *
 * @author Marcel
 */
public final class Octet extends DataValue {
    public static final Octet Zero = new Octet(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 });
    
    public Octet(byte[] data) {
        super(ByteBuffer.wrap(data, 0, 1).array(), 8);
    }
    
    public byte getValue() {
        return (byte)this.ToValue(1);
    }
}
