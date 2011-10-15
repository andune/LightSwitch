/**
 * 
 */
package org.morganm.lightswitch;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.morganm.lightswitch.config.Config;
import org.morganm.lightswitch.config.ConfigException;
import org.morganm.lightswitch.config.ConfigFactory;
import org.morganm.lightswitch.config.JavaConfigPlugin;
import org.morganm.lightswitch.storage.JavaStoragePlugin;
import org.morganm.lightswitch.storage.Storage;
import org.morganm.lightswitch.storage.StorageException;
import org.morganm.lightswitch.storage.StorageFactory;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

/**
 * @author morganm
 *
 */
public class LightSwitchPlugin extends JavaPlugin implements JavaConfigPlugin, JavaStoragePlugin {
	public static final Logger log = Logger.getLogger(LightSwitchPlugin.class.toString());
    private static LightSwitchPlugin instance; 
	
	// yellow
    private static final String MOD_COLOR = "\u00A7e";
	
	private String logPrefix;
	private String pluginName;
	private Config config;
    private PermissionHandler permissionHandler;
    private Storage storage;
    private LightSwitchManager lightSwitchManager;
    
    /* Hashmap which contains all entities we track that are associated with a physical
     * location. This map is managed by the Storage facility as objects are changing.
     */
	private final HashMap<Location, Object> locationMap = new HashMap<Location, Object>();
	
    public static LightSwitchPlugin getInstance() {
    	return instance;
    }
    
	@Override
	public void onEnable() {
		instance = this;
		boolean loadError = false;
		
    	pluginName = getDescription().getName();
    	logPrefix = "[" + pluginName + "]";
    	
		initPermissions();
		
    	try {
    		loadConfig();
    		loadStorage();
    	}
    	catch(Exception e) {
    		loadError = true;
    		log.severe("Error loading plugin: "+pluginName);
    		e.printStackTrace();
    	}
    	
    	if( loadError ) {
    		log.severe("Error detected when loading plugin "+ pluginName +", plugin shutting down.");
    		shutdownPlugin();
    		return;
    	}
    	
    	lightSwitchManager = new LightSwitchManager(this);
    	
//        getServer().getPluginManager().registerEvent(Event.Type.BLOCK_BREAK, blockListener, Priority.Monitor, this);
        
        log.info( logPrefix + " version [" + getDescription().getVersion() + "] loaded" );
	}

	@Override
	public void onDisable() {
		getServer().getScheduler().cancelTasks(this);
        log.info( logPrefix + " version [" + getDescription().getVersion() + "] unloaded" );
	}
	
	public void loadConfig() throws ConfigException, IOException {
		boolean firstTime = true;
		if( config != null )
			firstTime = false;
		
		config = ConfigFactory.getInstance(ConfigFactory.Type.YAML, this, "plugins/"+pluginName+"/config.yml");
		config.load();
		

		/*
		if( blockListener != null )
			blockListener.reloadConfig();
		if( oreProcessor != null )
			oreProcessor.reloadConfig();
			*/
		
		if( !firstTime )
			log.info(logPrefix + " config live reload complete");
	}
	
	public void loadStorage() throws StorageException, IOException {
		storage = StorageFactory.getInstance(StorageFactory.STORAGE_TYPE_EBEANS, this);
	}
	
	public void shutdownPlugin() {
		getServer().getPluginManager().disablePlugin(this);		
	}
		
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		String commandName = command.getName().toLowerCase();
		
		if( commandName.equals("lo") ) {
			// TODO: should implement Bukkit command pattern
		}
		
		return false;
	}
	
    /** Initialize permission system.
     * 
     */
    private void initPermissions() {
        Plugin permissionsPlugin = getServer().getPluginManager().getPlugin("Permissions");
        if( permissionsPlugin != null )
        	permissionHandler = ((Permissions) permissionsPlugin).getHandler();
        else
	    	log.warning(logPrefix+" Permissions (Yeti) system not detected, using Bukkit superperms instead.");
    }
    
    public boolean hasPermission(CommandSender sender, String permissionNode) {
		if( sender instanceof ConsoleCommandSender )
			return true;
		
		if( sender instanceof Player ) {
	    	if( permissionHandler != null ) 
	    		return permissionHandler.has((Player) sender, permissionNode);
	    	else
	    		return ((Player) sender).hasPermission(permissionNode);
		}
		
		return false;
    }
	
	/** Write a mod message to the target, using our preferred color.
	 * 
	 * @param target
	 */
	public void sendMessage(CommandSender target, String message) {
		target.sendMessage(MOD_COLOR + message);
	}
	
    public void installEbeansDatabaseDDL() {
        installDDL();
    }
    
    /** Define the Entity classes that we want serialized to the database.
     */
    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> classList = new LinkedList<Class<?>>();
        classList.add(org.morganm.lightswitch.entity.Circuit.class);
        classList.add(org.morganm.lightswitch.entity.CircuitEntity.class);
        classList.add(org.morganm.lightswitch.entity.CircuitSign.class);
        classList.add(org.morganm.lightswitch.entity.Version.class);
        return classList;
    }
    
    public LightSwitchManager getManager() { return lightSwitchManager; }
    
	public Config getConfig() { return config; }
	
	public File getJarFile() {
		return super.getFile();
	}
	
	@Override
	public String getLogPrefix() {
		return logPrefix;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	public String getPluginName() {
		return pluginName;
	}
	
	@Override
	public Storage getStorage() {
		return storage;
	}
	
	@Override
	public Map<Location, Object> getLocationMap() { return locationMap; }
}
