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
public final class UInt8 extends DataValue {
    public static final UInt8 Zero = new UInt8(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 });
    
    public UInt8 (byte[] data) {
        super(ByteBuffer.wrap(data, 0, 1).array(), 8);
    }
    
    public int getValue() {
        return (int)this.ToUValue(1);
    }
    
}
