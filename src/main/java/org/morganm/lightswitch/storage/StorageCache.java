/**
 * 
 */
package org.morganm.lightswitch.storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.morganm.lightswitch.entity.Circuit;
import org.morganm.lightswitch.entity.CircuitEntity;
import org.morganm.lightswitch.entity.CircuitSign;
import org.morganm.lightswitch.entity.CircuitSwitch;

/** This object wraps a Storage implementation and maintains an in-memory cache of
 * all objects.
 * 
 * Possible better (easier to maintain) implementation would be to use a proxy object
 * for all the getXXX() methods that are just pass-through anyway. 
 * 
 * @author morganm
 *
 */
public class StorageCache implements Storage {
	private Set<Circuit> circuits = new HashSet<Circuit>();
	private Set<CircuitSwitch> circuitSwitches = new HashSet<CircuitSwitch>();
	private Set<CircuitEntity> circuitEntities = new HashSet<CircuitEntity>();
	private final Map<Location, Object> locationToObjectMap;
	private final HashMap<Object, Location> objectToLocationMap;
	
	private Storage backingStore;
	
	public StorageCache(Storage backingStore, Map<Location, Object> locationMap) {
		this.backingStore = backingStore;
		this.locationToObjectMap = locationMap;
		objectToLocationMap = new HashMap<Object, Location>();
	}
	
	@Override
	public void initializeStorage() {
		backingStore.initializeStorage();
		loadAll();
	}
	@Override
	public void purgeCache() {
		circuits.clear();
		circuitSwitches.clear();
		circuitEntities.clear();
		
		loadAll();
	}
	
	private void loadAll() {
		circuits = backingStore.getAllCircuits();
		
		circuitSwitches = backingStore.getAllCircuitSwitches();
		if( circuitSwitches != null )
			for(CircuitSwitch circuitSwitch : circuitSwitches) {
				updateLocation(circuitSwitch);
			}
					
		circuitEntities = backingStore.getAllCircuitEntities();
		if( circuitEntities != null )
			for(CircuitEntity circuitEntity : circuitEntities) {
				updateLocation(circuitEntity);
			}
	}
	
	private void deleteLocation(Object o) {
		Location oldLocation = objectToLocationMap.get(o);
		if( oldLocation != null )
			locationToObjectMap.remove(oldLocation);
	}
	private void updateLocation(Object o, Location newLocation) {
		Location oldLocation = objectToLocationMap.get(o);
		
		// do nothing if location hasn't changed
		if( oldLocation.equals(newLocation) )
			return;
		
		deleteLocation(oldLocation);
		
		locationToObjectMap.put(newLocation, o);
		objectToLocationMap.put(o, newLocation);
	}
	
	private void updateLocation(CircuitEntity circuitEntity) {
		updateLocation(circuitEntity, circuitEntity.getLocation());
	}
	private void updateLocation(CircuitSwitch circuitSwitch) {
		updateLocation(circuitSwitch, circuitSwitch.getLocation());
	}
	
	@Override
	public Circuit getCircuitById(int circuitId) {
		return backingStore.getCircuitById(circuitId);
	}
	@Override
	public Circuit getCircuitByName(String circuitName) {
		return backingStore.getCircuitByName(circuitName);
	}
	@Override
	public void writeCircuit(Circuit circuit) {
		backingStore.writeCircuit(circuit);
		circuits.add(circuit);
	}
	@Override
	public void deleteCircuit(Circuit circuit) {
		backingStore.deleteCircuit(circuit);
		circuits.remove(circuit);
	}
	
	@Override
	public CircuitSign getCircuitSignByCircuitId(int circuitId) {
		return backingStore.getCircuitSignByCircuitId(circuitId);
	}
	@Override
	public void writeCircuitSign(CircuitSign circuitSign) {
		backingStore.writeCircuitSign(circuitSign);
	}
	
	@Override
	public Set<CircuitEntity> getCircuitEntitiesByCircuitId(int circuitId) {
		return backingStore.getCircuitEntitiesByCircuitId(circuitId);
	}
	@Override
	public void writeCircuitEntity(CircuitEntity circuitEntity) {
		backingStore.writeCircuitEntity(circuitEntity);
		circuitEntities.add(circuitEntity);
		updateLocation(circuitEntity);
	}
	@Override
	public void deleteCircuitEntity(CircuitEntity circuitEntity) {
		backingStore.deleteCircuitEntity(circuitEntity);
		deleteLocation(circuitEntity);
	}
	
	@Override
	public CircuitSwitch getCircuitSwitchByCircuitId(int circuitId) {
		return backingStore.getCircuitSwitchByCircuitId(circuitId);
	}
	@Override
	public CircuitSwitch getCircuitSwitchByLocation(Location l) {
		Object o = locationToObjectMap.get(l);
		if( o != null && o instanceof CircuitSwitch ) {
			return (CircuitSwitch) o;
		}
		else
			return null;
	}
	@Override
	public Set<CircuitSwitch> getCircuitSwitchesByWorld(String worldName) {
		return backingStore.getCircuitSwitchesByWorld(worldName);
	}
	@Override
	public void writeCircuitSwitch(CircuitSwitch circuitSwitch) {
		backingStore.writeCircuitSwitch(circuitSwitch);
		circuitSwitches.add(circuitSwitch);
		updateLocation(circuitSwitch);
	}
	@Override
	public void deleteCircuitSwitch(CircuitSwitch circuitSwitch) {
		backingStore.deleteCircuitSwitch(circuitSwitch);
		circuitSwitches.remove(circuitSwitch);
		deleteLocation(circuitSwitch);
	}

	@Override
	public Set<Circuit> getAllCircuits() {
		return circuits;
	}

	@Override
	public Set<CircuitEntity> getAllCircuitEntities() {
		return circuitEntities;
	}

	@Override
	public Set<CircuitSwitch> getAllCircuitSwitches() {
		return circuitSwitches;
	}
}