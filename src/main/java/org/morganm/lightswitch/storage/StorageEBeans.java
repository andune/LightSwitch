/**
 * 
 */
package org.morganm.lightswitch.storage;

import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.PersistenceException;

import org.morganm.lightswitch.entity.Circuit;
import org.morganm.lightswitch.entity.CircuitEntity;
import org.morganm.lightswitch.entity.CircuitSign;
import org.morganm.lightswitch.entity.CircuitSwitch;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Query;

/** Implements storage using Bukkit Ebeans system.  This can be backed by either MySQL
 * or sqlLite depending on what the admin has configured in their bukkit.yml: it makes no
 * difference to us, the API is the same.
 * 
 * @author morganm
 *
 */
public class StorageEBeans implements Storage {
    private final Logger log;
	@SuppressWarnings("unused")
	private final String logPrefix;
	private final JavaStoragePlugin plugin;
	
	public StorageEBeans(JavaStoragePlugin plugin) {
		this.plugin = plugin;
		this.log = plugin.getLogger();
		this.logPrefix = plugin.getLogPrefix();
		
		initializeStorage();
	}
	
	/* (non-Javadoc)
	 * @see org.morganm.homespawnplus.IStorage#initializeStorage
	 */
	public void initializeStorage() {
		// Check that our tables exist - if they don't, then install the database. 
        try {
            EbeanServer db = plugin.getDatabase();
            if( db == null )
            	throw new NullPointerException("plugin.getDatabase() returned null EbeanServer!");
            
            db.find(Circuit.class).findRowCount();
        } catch (PersistenceException ex) {
            log.info("Installing database for "
                    + plugin.getPluginName()
                    + " due to first time usage");
            plugin.installEbeansDatabaseDDL();
        }
        
        try {
        	upgradeDatabase();
        } catch(Exception e) { e.printStackTrace(); }
	}
	
	private void upgradeDatabase() {
		// no upgrades needed for now - still on first version  :)
	}
	
	@Override
	public Circuit getCircuitById(int circuitId) {
		EbeanServer db = plugin.getDatabase();
		String q = "find circuit where id = :id";
		
		Query<Circuit> query = db.createQuery(Circuit.class, q);
		query.setParameter("id", circuitId);
		
		return query.findUnique();
	}
	
	@Override
	public CircuitSign getCircuitSignByCircuitId(int circuitId) {
		EbeanServer db = plugin.getDatabase();
		String q = "find circuitsign where circuitid = :id";
		
		Query<CircuitSign> query = db.createQuery(CircuitSign.class, q);
		query.setParameter("id", circuitId);
		
		return query.findUnique();		
	}
	
	@Override
	public Circuit getCircuitByName(String circuitName) {
		EbeanServer db = plugin.getDatabase();
		String q = "find circuit where circuitname = :name";
		
		Query<Circuit> query = db.createQuery(Circuit.class, q);
		query.setParameter("name", circuitName);
		
		return query.findUnique();		
	}
	
	@Override
	public Set<CircuitEntity> getCircuitEntitiesByCircuitId(int circuitId) {
		EbeanServer db = plugin.getDatabase();
		String q = "find circuitentity where circuitid = :id";
		
		Query<CircuitEntity> query = db.createQuery(CircuitEntity.class, q);
		query.setParameter("id", circuitId);
		
		return query.findSet();		
	}
	
	@Override
	public CircuitSwitch getCircuitSwitchByCircuitId(int circuitId) {
		EbeanServer db = plugin.getDatabase();
		String q = "find circuitswitch where circuitid = :id";
		
		Query<CircuitSwitch> query = db.createQuery(CircuitSwitch.class, q);
		query.setParameter("id", circuitId);
		
		return query.findUnique();	
	}
	
	@Override
	public CircuitSwitch getCircuitSwitchByLocation(org.bukkit.Location l) {
		EbeanServer db = plugin.getDatabase();
		String q = "find circuitswitch where world = :world and x = :x and y = :y and z = :z";
		
		Query<CircuitSwitch> query = db.createQuery(CircuitSwitch.class, q);
		query.setParameter("world", l.getWorld().getName());
		query.setParameter("x", l.getBlockX());
		query.setParameter("y", l.getBlockY());
		query.setParameter("z", l.getBlockZ());
		
		return query.findUnique();	
	}
	
	@Override
	public java.util.Set<CircuitSwitch> getCircuitSwitchesByWorld(String worldName) {
		EbeanServer db = plugin.getDatabase();
		String q = "find circuitswitch where world = :world";
		
		Query<CircuitSwitch> query = db.createQuery(CircuitSwitch.class, q);
		query.setParameter("world", worldName);
		
		return query.findSet();	
	}
	
	@Override
	public void writeCircuit(Circuit circuit) {
		plugin.getDatabase().save(circuit);
	}
	
	@Override
	public void writeCircuitEntity(CircuitEntity circuitEntity) {
		plugin.getDatabase().save(circuitEntity);
	}
	
	@Override
	public void writeCircuitSign(CircuitSign circuitSign) {
		plugin.getDatabase().save(circuitSign);
	}
	
	@Override
	public void writeCircuitSwitch(CircuitSwitch circuitSwitch) {
		plugin.getDatabase().save(circuitSwitch);
	}
	
	@Override
	public void purgeCache() {
		// in theory we should pass this call through to the EBEAN server, but it doesn't
		// offer any support for this functionality.  So we do nothing.
	}

	@Override
	public void deleteCircuit(Circuit circuit) {
		plugin.getDatabase().delete(circuit);
	}

	@Override
	public void deleteCircuitEntity(CircuitEntity circuitEntity) {
		plugin.getDatabase().delete(circuitEntity);
	}

	@Override
	public void deleteCircuitSwitch(CircuitSwitch circuitSwitch) {
		plugin.getDatabase().delete(circuitSwitch);
	}

	@Override
	public Set<Circuit> getAllCircuits() {
		return plugin.getDatabase().find(Circuit.class).findSet();
	}
	
	@Override
	public Set<CircuitEntity> getAllCircuitEntities() {
		return plugin.getDatabase().find(CircuitEntity.class).findSet();
	}

	@Override
	public Set<CircuitSwitch> getAllCircuitSwitches() {
		return plugin.getDatabase().find(CircuitSwitch.class).findSet();
	}
}
