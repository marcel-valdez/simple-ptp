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
public class Int16 extends DataValue {
    public static final Int16 Zero = new Int16(new byte[] { 0, 0, 0, 0 });
    
    public Int16(byte[] data) {
        super(ByteBuffer.wrap(data, 0, 2).array(), 16);
    }
    
    public short getValue() {
        return (short)this.ToValue(2);
    }
}