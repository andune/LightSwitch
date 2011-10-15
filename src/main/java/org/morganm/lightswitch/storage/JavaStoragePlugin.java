/**
 * 
 */
package org.morganm.lightswitch.storage;

import java.util.Map;

import org.bukkit.Location;
import org.morganm.lightswitch.config.JavaConfigPlugin;

import com.avaje.ebean.EbeanServer;

/**
 * @author morganm
 *
 */
public interface JavaStoragePlugin extends JavaConfigPlugin {
	public String getPluginName();
	
	/** Method called to install the Database DDL.  Ordinarily this would not be an interface
	 * requirement of the plugin but rather that of the Storage subsystem, but since Bukkit
	 * ebeans provides an OOTB way to do this (via a protected method), we provide the
	 * interface as a way to allow the plugin to leverage that method.  The plugin is free
	 * to punt that back to the Storage interface if some other storage mechanism other than
	 * eBeans is being used.
	 * 
	 */
	public void installEbeansDatabaseDDL();
	
	public EbeanServer getDatabase();
	
	public Storage getStorage();

	public Map<Location, Object> getLocationMap();
}
