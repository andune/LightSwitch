/**
 * 
 */
package org.morganm.lightswitch;

import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityListener;

/**
 * @author morganm
 *
 */
public class LightSwitchEntityListener extends EntityListener {
	@SuppressWarnings("unused")
	private LightSwitchPlugin plugin;
	private LightSwitchManager manager;
	
	public LightSwitchEntityListener(LightSwitchPlugin plugin) {
		this.plugin = plugin;
		this.manager = plugin.getManager();
	}
	
	@Override
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if( manager.isInNoSpawnZone(event.getLocation()) )
			event.setCancelled(true);
	}
}
