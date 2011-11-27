/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author Marcel
 */
public class Boolean extends DataValue {
    public static final Boolean False = new Boolean(new byte[] { 0, 0, 0, 0 });
    
    public Boolean(byte[] data) {
        super(new byte[] { data[data.length - 1] }, 1);
    }
    
    public boolean getValue() {
        return this.ToValue(1) > 0;
    }
}
