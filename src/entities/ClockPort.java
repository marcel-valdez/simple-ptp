/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.interfaces.IEventInterface;
import data.structs.ForeignMasterRecord;
import data.structs.PortDataSet;
import entities.factory.Factory;
import entities.interfaces.IGeneralInterface;
import entities.interfaces.INetNodesRepository;

/**
 *
 * @author Marcel
 */
public class ClockPort {

    private PortDataSet portDataSet;
    private ForeignMasterRecord foreignMasterDataSet;
    private final IEventInterface eventInterface;
    private final IGeneralInterface generalInterface;
    private final INetNodesRepository repo;
    
    public ClockPort(PTPEngine engine) {
        repo = Factory.getInstance().CreateNetNodesRepo();
        INetNodesRepository repo = Factory.getInstance().CreateNetNodesRepo();
        IEventInterface eInterface =  Factory.getInstance().CreateEventInterface(this, repo);
        eInterface.setHandler(engine);
        this.eventInterface = eInterface;
        this.portDataSet = new PortDataSet();
        
        IGeneralInterface gInterface =  Factory.getInstance().CreateGeneralInterface(this, repo);
        gInterface.setHandler(engine);
        this.generalInterface = gInterface;
        
        /**
         * TODO: Registrar las interfaces en la variable global (CORBA)
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
}
