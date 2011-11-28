/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.interfaces;

import data.structs.MsgManagement;

/**
 *
 * @author Marcel
 */
public interface IGeneralMsgHandler {
    void ProcessMessage(MsgManagement message);
}
