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

    void Register(IInInterface external);

    void Unregister(IInInterface external);
    
    IInInterface[] GetNodes();
}
