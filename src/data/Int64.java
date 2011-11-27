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
public final class Int64 extends DataValue {
    public static final Int64 Zero = new Int64(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 });
    
    public Int64(byte[] data) {
        super(ByteBuffer.wrap(data, 0, 8).array(), 64);
    }
    
    public long getValue() {
        return this.ToValue(8);
    }
}
