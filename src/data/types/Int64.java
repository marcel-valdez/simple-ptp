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
public final class Int64 extends DataValue<Long> {

    public static final Int64 Zero = new Int64(new byte[]{0, 0, 0, 0, 0, 0, 0, 0});
    
    public Int64(byte... data) {
        super(new byte[]{data[data.length - 8], data[data.length - 7],
            data[data.length - 6], data[data.length - 5], data[data.length - 4],
            data[data.length - 3], data[data.length - 2], data[data.length - 1]}, 64);
    }
    
    @Override
    public Long getValue() {
        return this.ToValue(8);
    }
}
