/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.interfaces.IOutGeneralInterface;
import entities.interfaces.IOutEventInterface;
import data.structs.ForeignMasterRecord;
import data.structs.PortDataSet;

/**
 *
 * @author Marcel
 */
class ClockPort {

    final private OrdinaryClock mOwner;
    PortDataSet portDataSet;
    ForeignMasterRecord foreignMasterDataSet;
    IOutEventInterface eventInterface;
    IOutGeneralInterface generalInterface;

    public ClockPort(OrdinaryClock owner) {
        this.mOwner = owner;
    }
}
