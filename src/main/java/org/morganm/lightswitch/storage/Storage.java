/**
 * 
 */
package org.morganm.lightswitch.storage;

import java.util.Set;

import org.bukkit.Location;
import org.morganm.lightswitch.entity.Circuit;
import org.morganm.lightswitch.entity.CircuitEntity;
import org.morganm.lightswitch.entity.CircuitSign;
import org.morganm.lightswitch.entity.CircuitSwitch;


/** Storage interface for stored objects this plugin uses.
 * 
 * @author morganm
 *
 */
public interface Storage {
	/* This method is called to intialize the storage system.  If using a DB back end, this
	 * is the method that should create the tables if they don't exist.
	 * 
	 * It is possible that this method could be called multiple times, so it is this methods
	 * responsibility to keep track of whether it has already initialized and deal with that
	 * situation appropriately. 
	 */
	public void initializeStorage();
	
	/** Notify the backing store that it should purge any in-memory cache it has.
	 */
	public void purgeCache();
	
	public Circuit getCircuitById(int circuitId);
	public Circuit getCircuitByName(String circuitName);
	public void writeCircuit(Circuit circuit);
	public void deleteCircuit(Circuit circuit);
	public Set<Circuit> getAllCircuits();
	
	public CircuitSign getCircuitSignByCircuitId(int circuitId);
	public void writeCircuitSign(CircuitSign circuitSign);
	
	public Set<CircuitEntity> getCircuitEntitiesByCircuitId(int circuitId);
	public void writeCircuitEntity(CircuitEntity circuitEntity);
	public void deleteCircuitEntity(CircuitEntity circuitEntity);
	public Set<CircuitEntity> getAllCircuitEntities();
	
	public CircuitSwitch getCircuitSwitchByCircuitId(int circuitId);
	public CircuitSwitch getCircuitSwitchByLocation(Location l);
	public Set<CircuitSwitch> getCircuitSwitchesByWorld(String worldName);
	public void writeCircuitSwitch(CircuitSwitch circuitSwitch);
	public void deleteCircuitSwitch(CircuitSwitch circuitSwitch);
	public Set<CircuitSwitch> getAllCircuitSwitches();
}
