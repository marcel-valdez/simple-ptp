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
public final class UInt32 extends DataValue {
    public static final UInt32 Zero = new UInt32(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 });
    
    public UInt32(byte[] data) {
        super(ByteBuffer.wrap(data, 0, 4).array(), 32);
    }
    
    public long getValue() {
        return this.ToUValue(4);
    }
}
