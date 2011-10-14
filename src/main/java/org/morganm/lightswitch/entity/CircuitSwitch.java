/**
 * 
 */
package org.morganm.lightswitch.entity;

import org.bukkit.Location;
import org.bukkit.World;
import org.morganm.lightswitch.LightSwitchPlugin;

/**
 * @author morganm
 *
 */
public class CircuitSwitch {
	private int id;
	private int circuitId;
	
	private String world;
	private int x;
	private int y;
	private int z;
	
	private transient Location location;
	
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

	/** Return the Circuit associated with this CircuitSwitch.
	 * 
	 * @return
	 */
	public Circuit getCircuit() {
		return LightSwitchPlugin.getInstance().getStorage().getCircuitById(circuitId);
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
