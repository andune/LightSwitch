/**
 * 
 */
package org.morganm.lightswitch.config;

import java.util.List;

/** Our configuration which determines how a number of things about how this mod operates.
 * Intended to be used with ConfigOptions class for setting nodes as String keys.
 * 
 * @author morganm
 *
 */
public interface Config {
	/** Should be called after the config option is created to allow it to do any loading
	 * it needs to do.
	 * 
	 * @throws ConfigException
	 */
	public void load() throws ConfigException;
	
	/** Should be called whenever the config options change and you want them serialized
	 * out to the backing store.
	 * 
	 * @throws ConfigException
	 */
	public boolean save() throws ConfigException;
	
    /**
     * Gets a boolean for a given node. This will either return an boolean
     * or the default value. If the object at the particular location is not
     * actually a boolean, the default value will be returned.
     *
     * @param path path to node (dot notation)
     * @param def  default value
     * @return boolean or default
     */
    public boolean getBoolean(String node, boolean def);
    
    /**
     * Gets a string at a location. This will either return an String
     * or null, with null meaning that no configuration value exists at
     * that location. If the object at the particular location is not actually
     * a string, it will be converted to its string representation.
     *
     * @param path path to node (dot notation)
     * @return string or null
     */
    public String getString(String path);

    /**
     * Gets a string at a location. This will either return an String
     * or the default value. If the object at the particular location is not
     * actually a string, it will be converted to its string representation.
     *
     * @param path path to node (dot notation)
     * @param def  default value
     * @return string or default
     */
    public String getString(String path, String def);
    
    /**
     * Gets an integer at a location. This will either return an integer
     * or the default value. If the object at the particular location is not
     * actually a integer, the default value will be returned. However, other
     * number types will be casted to an integer.
     *
     * @param path path to node (dot notation)
     * @param def  default value
     * @return int or default
     */
    public int getInt(String path, int def);

    /**
     * Gets a list of strings. Non-valid entries will not be in the list.
     * There will be no null slots. If the list is not defined, the
     * default will be returned. 'null' can be passed for the default
     * and an empty list will be returned instead. If an item in the list
     * is not a string, it will be converted to a string. The node must be
     * an actual list and not just a string.
     *
     * @param path path to node (dot notation)
     * @param def default value or null for an empty list as default
     * @return list of strings
     */
    public List<String> getStringList(String path, List<String> def);
}
