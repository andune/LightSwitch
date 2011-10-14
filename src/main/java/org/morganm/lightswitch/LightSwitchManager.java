/**
 * 
 */
package org.morganm.lightswitch;

import java.util.HashMap;
import java.util.Set;

import javax.media.j3d.Material;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.block.CraftSign;
import org.morganm.lightswitch.entity.Circuit;
import org.morganm.lightswitch.entity.CircuitEntity;
import org.morganm.lightswitch.entity.CircuitSwitch;

/**
 * @author morganm
 *
 */
public class LightSwitchManager {
	private LightSwitchPlugin plugin;
	private HashMap<Location, CircuitSwitch> circuitSwitches;
	
	public LightSwitchManager(LightSwitchPlugin plugin) {
		this.plugin = plugin;
	}
	
	/** Return the circuit associated with a given sign.
	 * 
	 * @param sign
	 * @return
	 */
	public Circuit getCircuit(CraftSign sign) {
		// TODO: decide how to use signs
		return null;
	}
	
	/** Given a location, return the CircuitSwitch associated with that location (if any).
	 * 
	 * @param l
	 * @return
	 */
	public CircuitSwitch getCircuitSwitch(Location l) {
		return plugin.getStorage().getCircuitSwitchByLocation(l);
	}
	
	public void toggleCircuit(Circuit circuit) {
		// first get the existing state of the Circuit and update the DB with the new state
		boolean state = !circuit.isOnState();
		circuit.setOnState(state);
		plugin.getStorage().writeCircuit(circuit);
		
		// now apply that state to all CircuitEntities associated with this Circuit
		Set<CircuitEntity> entities = plugin.getStorage().getCircuitEntitiesByCircuitId(circuit.getId());
		if( entities != null ) {
			for(CircuitEntity entity : entities) {
				entity.setState(state);
			}
		}
	}
	
	/** Given a typeId, return the typeId that represents it's toggled state.
	 * 
	 * @param typeId
	 * @return
	 */
	public int getToggledTypeId(int typeId) {
		// assume no change unless defined below
		int newType = typeId;
		
		switch(typeId) {
		
		}
		
		return newType;
	}
	
	public boolean getOnState(int typeId) {
		switch(typeId) {
		case 1:		// TORCH
			return true;
			
		case 2:		// disabled redstone torch
			return false;
			
		case 3:		// jack-o-lantern
			return true;
		
		case 4:		// pumpkin
			return false;
			
		default:
			throw new IllegalArgumentException("typeId "+typeId+" is not a valid type for method getOnState()");
		}
	}
	
	public void createNewCircuit(String circuitName) {
		Circuit circuit = new Circuit();
		circuit.setCircuitName(circuitName);
		plugin.getStorage().writeCircuit(circuit);
	}
	
	public void createNewCircuitEntity(Circuit circuit, Block b) {
		// basic validation check that we're allowed to create an entity of the given block type.
		getOnState(b.getTypeId());
		
		CircuitEntity entity = new CircuitEntity();
		entity.setCircuitId(circuit.getId());
		entity.setLocation(b.getLocation());
		plugin.getStorage().writeCircuitEntity(entity);
	}
	
	public void createNewCircuitSwitch(Circuit circuit, Block b) {
		// TODO: some switch sanity check
		
		CircuitSwitch circuitSwitch = new CircuitSwitch();
		circuitSwitch.setCircuitId(circuit.getId());
		circuitSwitch.setLocation(b.getLocation());
		plugin.getStorage().writeCircuitSwitch(circuitSwitch);		
	}
	
	public CircuitSwitch findNearestCircuitSwitch(Location l) {
		CircuitSwitch nearestSwitch = null;
		Set<CircuitSwitch> switches = plugin.getStorage().getCircuitSwitchesByWorld(l.getWorld().getName());
		if( switches != null ) {
			double distance = 1000;	// maximum distance for a switch, probably should be much less and defined in config
			for(CircuitSwitch circuitSwitch : switches) {
				Location switchLocation = circuitSwitch.getLocation();
				double d = l.distance(switchLocation);
				if( d < distance ) {
					distance = d;
					nearestSwitch = circuitSwitch;
				}
			}
		}
		return nearestSwitch;
	}
}
