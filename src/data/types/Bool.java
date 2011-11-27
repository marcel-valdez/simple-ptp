/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.types;

/**
 *
 * @author Marcel
 */
public class Bool extends DataValue<Boolean> {

    public static final Bool False = new Bool((byte) 0);
    public static final Bool True = new Bool((byte) 1);

    public Bool(byte... data) {
        super(new byte[]{data[0]}, 8);
    }

    @Override
    public Boolean getValue() {
        return this.ToValue(1) > 0;
    }
}
