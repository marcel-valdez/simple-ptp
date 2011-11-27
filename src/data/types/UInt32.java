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
        super(new byte[] { data[0], data[1], data[2], data[3] }, 32);
    }
    
    @Override
    public Long getValue() {
        return this.ToUValue(4);
    }
}
