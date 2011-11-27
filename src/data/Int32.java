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
public final class Int32 extends DataValue {
    public static final Int32 Zero = new Int32(new byte[] { 0, 0, 0, 0 });
    
    public Int32(byte[] data) {
        super(ByteBuffer.wrap(data, 0, 4).array(), 32);
    }
    
    public int getValue() {
        return (int)this.ToValue(4);
    }
}
