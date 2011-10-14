/**
 * 
 */
package org.morganm.lightswitch.storage;

import java.io.IOException;


/**
 * @author morganm
 *
 */
public class StorageFactory {
	public static final int STORAGE_TYPE_EBEANS = 0;
	
	public static Storage getInstance(int storageType, JavaStoragePlugin plugin)
		throws StorageException, IOException
	{
		if ( storageType == STORAGE_TYPE_EBEANS ) {
			return new StorageEBeans(plugin);
		}
		else {
			throw new StorageException("Unable to create Storage interface, invalid type given: "+storageType);
		}
	}

}
