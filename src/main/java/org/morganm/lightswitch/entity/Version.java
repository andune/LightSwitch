/**
 * 
 */
package org.morganm.lightswitch.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.validation.NotNull;

/**
 * @author morganm
 *
 */
@Entity()
@Table(name="ls_version")
public class Version {
    @Id
    private int id;
    
    @NotNull
    private int databaseVersion;

	public Version() {}
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDatabaseVersion() {
		return databaseVersion;
	}

	public void setDatabaseVersion(int databaseVersion) {
		this.databaseVersion = databaseVersion;
	}
}
