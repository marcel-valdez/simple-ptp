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
public class UInt16 extends DataValue<Integer> {
    public static final UInt16 Zero = new UInt16(new byte[] { 0, 0 });
    
    public UInt16(byte... data) {
        super(new byte[] { data[data.length - 2], data[data.length - 1] }, 16);
    }
    
    @Override
    public Integer getValue() {
        return (int)this.ToUValue(2);
    }
}
