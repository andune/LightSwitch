/**
 * 
 */
package org.morganm.lightswitch.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/** Class that defines the location of a Sign that declares the circuit.  Provides
 * some convenience methods for extracting information about that sign.
 * 
 * @author morganm
 *
 */
@Entity()
@Table(name="ls_sign")
public class CircuitSign {
	private int id;
	private int circuitId;

	private String world;
	private int x;
	private int y;
	private int z;
	
	public String getOwner() {
		// TODO
		return null;
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
