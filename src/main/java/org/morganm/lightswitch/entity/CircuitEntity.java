/**
 * 
 */
package org.morganm.lightswitch.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.bukkit.Location;
import org.bukkit.World;
import org.morganm.lightswitch.LightSwitchPlugin;

/** A single entity that is part of a circuit.
 * 
 * @author morganm
 *
 */
@Entity()
@Table(name="ls_entity")
public class CircuitEntity {
	private int id;
	private int circuitId;
	
	private String world;
	private int x;
	private int y;
	private int z;
	
	private transient Location location;
	
	/** Set the state of this entity to the new state.
	 * 
	 * @param state
	 */
	public void setState(boolean state) {
		// TODO: add check for ignoring entities in unloaded chunks
		
		if( state == getState() )
			return;		// do nothing if we're already in the requested state
		
		Location l = getLocation();
		World w = l.getWorld();
		int typeId = w.getBlockTypeIdAt(l);
		int newTypeId = 0; // TODO: get TypeId
		
		if( typeId != newTypeId ) {
			l.getBlock().setTypeId(newTypeId);
		}
	}
	
	/** Return the current state of this entity.
	 * 
	 * @return
	 */
	public boolean getState() {
		// TODO: do something
		return false;
	}
	
	public Location getLocation() {
		if( location == null ) {
			World w = LightSwitchPlugin.getInstance().getServer().getWorld(world);
			location = new Location(w, (double)x, (double)y, (double)z);
		}
		
		return location;
	}
	
	public void setLocation(Location l) {
		location = l;
		setWorld(l.getWorld().getName());
		setX(l.getBlockX());
		setY(l.getBlockY());
		setZ(l.getBlockZ());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCircuitId() {
		return circuitId;
	}

	public void setCircuitId(int circuitId) {
		this.circuitId = circuitId;
	}

	public String getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = world;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
}
