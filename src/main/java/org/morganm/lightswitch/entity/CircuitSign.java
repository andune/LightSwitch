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
}
