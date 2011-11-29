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
public final class UInt32 extends DataValue<Long> {
    public static final UInt32 Zero = new UInt32(new byte[] { 0, 0, 0, 0 });
    
    public UInt32(byte... data) {
        super(new byte[] { data[data.length - 4], data[data.length - 3], data[data.length - 2], data[data.length - 1] }, 32);
    }
    
    @Override
    public Long getValue() {
        return this.ToUValue(4);
    }
}
