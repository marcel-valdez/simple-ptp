/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.global;

import entities.interfaces.IInInterface;
import entities.interfaces.INetNodesRepository;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase debe estar en el servidor central de CORBA, del cuál solamente habrá
 * uno, y de ahí obtienen los clientes los demás nodos de la red para ejecutar
 * el protocolo PTP
 * 
 * Esta clase contiene todas las interfaces Generales y de Eventos de todos los nodos
 * de la red.
 * @author Marcel
 */
public class GlobalNodesRepository implements INetNodesRepository {

    final List<INetNodesRepository> repos = new ArrayList<>();
    NetNodesRepository globalRepo = new NetNodesRepository();

    public void RegisterRepository(INetNodesRepository repo) {
        synchronized (repos) {
            this.repos.add(repo);
        }
    }

    public void UnregisterRepository(INetNodesRepository repo) {
        synchronized (repos) {
            INetNodesRepository toRemove = null;
            for (INetNodesRepository value : this.repos) {
                if (value.equals(repo)) {
                    toRemove = value;
                    break;
                }
            }

            this.repos.remove(toRemove);
        }
    }

    @Override
    /*
     * Registra un nuevo nodo en el dominio (del cuál solamente hay 1), normalmente
     * un nodo se registra a sí mismo.
     */
    public void Register(IInInterface external) {
        synchronized (repos) {
            globalRepo.Register(external);
            INetNodesRepository[] copy = repos.toArray(null);
            for (int i = 0; i < copy.length; i++) {
                INetNodesRepository value = copy[i];
                value.Register(external);
            }
        }
    }

    /*
     * Quita del registro global un nodo PTP, normalmente los nodos morirán solos,
     * sin poder quitar su registro. Quiénes están encargados de quitar los
     * registros de nodos muertos, son los demás nodos PTP que se den cuenta
     * que un Nodo está deshabilitado.
     */
    @Override
    public void Unregister(IInInterface external) {
        synchronized (repos) {
            globalRepo.Unregister(external);
            INetNodesRepository[] copy = repos.toArray(null);
            for (int i = 0; i < copy.length; i++) {
                INetNodesRepository value = copy[i];
                repos.remove(copy[i]);
            }
        }
    }

    /*
     * Este método regresa todos los nodos registrados en la red, esta pensado para
     * ser utilizado solamente la primera vez que un nodo PTP inicia, para que tenga
     * todos los nodos de su dominio (en esta implementación solamente hay 1 dominio)
     */
    @Override
    public IInInterface[] GetNodes() {
        synchronized (repos) {
            return globalRepo.GetNodes();
        }
    }
}
