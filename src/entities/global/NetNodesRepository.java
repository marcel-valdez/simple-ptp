/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.global;

import entities.interfaces.IInEventInterface;
import entities.interfaces.IInInterface;
import entities.interfaces.INetNodesRepository;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcel
 */
public class NetNodesRepository implements INetNodesRepository {

    /*
     * Se mantiene una lista con referencia débil, porque no es necesario
     * que aquí se mantenga una referencia al objeto, tal referencia ya
     * se mantiene por el objeto en la aplicación donde corre.
     */
    final List<IInInterface> nodes = new ArrayList<>();

    @Override
    public void Register(IInInterface external) {
        synchronized (nodes) {
            this.nodes.add(external);
        }
    }

    @Override
    public void Unregister(IInInterface external) {
        synchronized (nodes) {
            IInInterface[] objects = nodes.toArray(new IInInterface[]{});
            IInInterface toRemove = null;
            for (IInInterface obj : objects) {
                if (obj.equals(external)) {
                    this.nodes.remove(obj);
                    break;
                }
            }
        }
    }


    /*
     * Esta pensado para que siempre se obtenga la lista de nodos por medio de este método,
     * y no para que se mantenga una lista externa de los nodos.
     */
    @Override
    public IInInterface[] GetNodes() {
        IInInterface[] array = null;
        synchronized (nodes) {
            array = nodes.toArray(new IInInterface[]{});
        }

        return array;
    }
}
