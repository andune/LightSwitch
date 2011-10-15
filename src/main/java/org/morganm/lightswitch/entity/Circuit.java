/**
 * 
 */
package org.morganm.lightswitch.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.validation.Length;
import com.avaje.ebean.validation.NotEmpty;

/**
 * @author morganm
 *
 */
@Entity()
@Table(name="ls_circuit")
public class Circuit {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
    
    @NotEmpty
    @Length(max=32)
	private String circuitName;
    
    private boolean onState;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCircuitName() {
		return circuitName;
	}

	public void setCircuitName(String circuitName) {
		this.circuitName = circuitName;
	}
	
	public boolean isOnState() {
		return onState;
	}

	public void setOnState(boolean onState) {
		this.onState = onState;
	}
}
