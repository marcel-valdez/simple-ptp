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
public final class Int64 extends DataValue<Long> {
    public static final Int64 Zero = new Int64(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 });
    
    public Int64(byte... data) {
        super(new byte[] { data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7] }, 64);
    }
    
    @Override
    public Long getValue() {
        return this.ToValue(8);
    }
}
