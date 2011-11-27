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
public final class UInt8 extends DataValue<Integer> {
    public static final UInt8 Zero = new UInt8(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 });
    
    public UInt8 (byte... data) {
        super(new byte[] { data[0] }, 8);
    }
    
    @Override
    public Integer getValue() {
        return (int)this.ToUValue(1);
    }
    
}
