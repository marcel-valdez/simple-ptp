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

    public static final UInt8 Zero = new UInt8(new byte[]{0, 0, 0, 0, 0, 0, 0, 0});

    public UInt8(byte... data) {
        super(new byte[]{data[0]}, 8);
    }

    @Override
    public Integer getValue() {
        return (int) this.ToUValue(1);
    }

    public static UInt8[] Array(int length) {
        UInt8[] result = new UInt8[length];
        for (int i = 0; i < length; i++) {
            result[i] = UInt8.Zero;
        }

        return result;
    }
}
