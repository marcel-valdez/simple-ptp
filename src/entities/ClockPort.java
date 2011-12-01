/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import data.structs.ForeignMasterRecord;
import data.structs.PortDataSet;
import data.types.Octet;
import entities.factory.Factory;
import entities.interfaces.IEventInterface;
import entities.interfaces.IGeneralInterface;
import entities.interfaces.IGlobalNodesRegistry;
import entities.interfaces.IInEventInterface;
import entities.interfaces.IInInterface;
import entities.interfaces.INetNodesRepository;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcel
 */
public class ClockPort {

    final private PortDataSet portDataSet;
    private final IEventInterface eventInterface;
    private final IGeneralInterface generalInterface;
    private final INetNodesRepository repo;
    private ForeignMasterRecord foreignMasterDataSet;

    public ClockPort(PTPEngine engine) {
        this.repo = Factory.getInstance().CreateNetNodesRepo();
        this.portDataSet = new PortDataSet();
        IEventInterface eInterface = Factory.getInstance().CreateEventInterface(this, this.repo);
        eInterface.setHandler(engine);
        this.eventInterface = eInterface;

        IGeneralInterface gInterface = Factory.getInstance().CreateGeneralInterface(this, this.repo);
        gInterface.setHandler(engine);
        this.generalInterface = gInterface;

        /**
         * Registra las interfaces en la variable global (CORBA)
         * Usar variables estáticas que designe el main(string[] args) en
         * la inicialización, dónde también incializará el j4n,
         * nimodo, tendré que accesar todo lo generado por CORBA, con
         * variables estáticas, CORBA será meramente un repositorio
         * global, o la otra es que sí sea una interfaz definida, que 
         * envíe byte[] de ida y regreso, y los serialize/deserialize,
         * ya que todos los mensajes son serializables byte[]
         **/
    }

    /**
     * @return the portDataSet
     */
    public PortDataSet getPortDataSet() {
        return portDataSet;
    }

    /**
     * @return the foreignMasterDataSet
     */
    public ForeignMasterRecord getForeignMasterDataSet() {
        return foreignMasterDataSet;
    }

    /**
     * @return the eventInterface
     */
    public IEventInterface getEventInterface() {
        return eventInterface;
    }

    /**
     * @return the generalInterface
     */
    public IGeneralInterface getGeneralInterface() {
        return generalInterface;
    }

    /**
     * @return the repo
     */
    public INetNodesRepository getRepo() {
        return repo;
    }

    public boolean SetMaster(Octet[] uuid) {
        IInEventInterface master = this.getClockFromRepo(uuid);
        if (master != null) {
            this.eventInterface.SetMaster(master);
            return true;
        } else {
            IGlobalNodesRegistry registry = Factory.getInstance().GetGlobalNodesRegistry();
            // Se intenta sincronizar el repositorio actual
            this.SynchronizeRepo(registry);
            master = this.getClockFromRepo(uuid);
            if (master == null) {
                return false;
            } else {
                return true;
            }
        }
    }

    public IInEventInterface getClockFromRepo(Octet[] uuid) {
        IInInterface result = null;
        for (IInInterface clock : this.repo.GetNodes()) {
            try {
                if (Octet.Compare(uuid, clock.GetPortDataSet().portUuidField)) {
                    result = clock;
                    break;
                }
            } catch (Exception e) {
                Logger.getGlobal().log(Level.WARNING,
                        "Había un elemento nulo o desconectado en el repositorio\nSe elmin\u00f3 el elemento con uuid: {0}{1}{2}{3}",
                        new Object[]{uuid[0].getValue(), uuid[1].getValue(), uuid[2].getValue(), uuid[3].getValue()});
                this.repo.UnregisterClock(clock);
            }
        }

        return result;
    }

    /**
     * Sincroniza el repositorio local con el global
     * @param registry el registro de relojes global
     */
    public void SynchronizeRepo(IGlobalNodesRegistry registry) {
        IInInterface[] nodes = registry.GetNodes();
        this.ClearRepo();
        for (IInInterface node : nodes) {
            try {
                if (!Octet.Compare(
                        node.GetPortDataSet().portUuidField,
                        this.portDataSet.portUuidField)) {
                    this.repo.RegisterClock(node);
                }
            } catch (Exception e) {
                Logger.getGlobal().log(Level.WARNING, "Error al sincronizar el repositorio local de relojes", e);
            }
        }
    }

    public void ClearRepo() {
        for (IInInterface node : this.repo.GetNodes()) {
            repo.UnregisterClock(node);
        }
    }
}
