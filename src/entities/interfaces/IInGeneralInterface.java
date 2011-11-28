/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.interfaces;

import data.structs.MsgManagement;
import data.structs.PortDataSet;

/**
 *
 * @author Marcel
 */
public interface IInGeneralInterface {

    void InAnnounce(MsgManagement mngMsg);
    PortDataSet GetPortDataSet();
}
