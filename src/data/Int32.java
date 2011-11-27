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
public final class Int32 extends DataValue<java.lang.Integer>{
    public static final Int32 Zero = new Int32(new byte[] { 0, 0, 0, 0 });
    
    public Int32(byte... data) {
        super(new byte[] { data[0], data[1], data[2], data[3] }, 32);
    }
    
    @Override
    public Integer getValue() {
        return (int)this.ToValue(4);
    }
}
