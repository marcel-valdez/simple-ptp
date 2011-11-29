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
public class Int16 extends DataValue<Short> {
    public static final Int16 Zero = new Int16(new byte[] { 0, 0 });
    
    public Int16(byte... data) {
        super(new byte[] { data[data.length - 2], data[data.length - 1] }, 16);
    }
    
    @Override
    public Short getValue() {
        return (short)this.ToValue(2);
    }
}