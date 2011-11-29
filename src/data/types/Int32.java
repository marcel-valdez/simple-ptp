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
public final class Int32 extends DataValue<java.lang.Integer>{
    public static final Int32 Zero = new Int32(new byte[] { 0, 0, 0, 0 });
    
    public Int32(byte... data) {
        super(new byte[] { data[data.length - 4], data[data.length - 3],
            data[data.length - 2], data[data.length - 1] }, 32);
    }
    
    @Override
    public Integer getValue() {
        return (int)this.ToValue(4);
    }
}
