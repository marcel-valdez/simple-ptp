/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.interfaces;

/**
 *
 * @author Marcel
 */
public interface INetNodesRepository {

    void RegisterClock(IInInterface external);

    void UnregisterClock(IInInterface external);
    
    IInInterface[] GetNodes();
}
