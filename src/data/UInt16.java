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
public class UInt16 extends DataValue {
    public static final UInt16 Zero = new UInt16(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 });
    
    public UInt16(byte[] data) {
        super(ByteBuffer.wrap(data, 0, 2).array(), 16);
    }
    
    public int getValue() {
        return (int)this.ToUValue(2);
    }
}
