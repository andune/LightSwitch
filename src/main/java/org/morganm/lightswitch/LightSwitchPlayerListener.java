/**
 * 
 */
package org.morganm.lightswitch;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.morganm.lightswitch.entity.Circuit;

/**
 * @author morganm
 *
 */
public class LightSwitchPlayerListener extends PlayerListener {
	private LightSwitchPlugin plugin;
	private LightSwitchManager manager;
	
	public LightSwitchPlayerListener(LightSwitchPlugin plugin) {
		this.plugin = plugin;
		this.manager = plugin.getManager();
	}
	
	@Override
	public void onPlayerInteract(PlayerInteractEvent event) {
		if( event.isCancelled() )
			return;
		
		Player p = event.getPlayer();
		
		if( event.getAction() == Action.RIGHT_CLICK_BLOCK ) {
			Block b = event.getClickedBlock();
			
			if( manager.isInDeleteCircuitMode(p) ) {
				if( manager.isCircuitLocation(b.getLocation()) ) {
					manager.deleteEntity(b.getLocation());
					plugin.sendMessage(event.getPlayer(), "Circuit entity removed");
				}
			}
			else if( manager.isInActiveCircuitMode(p) ) {
				int typeId = b.getTypeId();
				Circuit circuit = manager.getActiveCircuit(p);
				
				if( manager.isValidEntityType(typeId) ) {
					manager.createNewCircuitEntity(circuit, b);
					plugin.sendMessage(p, "Block "+b+" is now associated with circuit "+circuit.getCircuitName());
				}
				else if( manager.isValidSwitchType(typeId) ) {
					manager.createNewCircuitSwitch(circuit, b);
					plugin.sendMessage(p, "Block "+b+" is now associated with circuit "+circuit.getCircuitName());
				}
				else {
					plugin.sendMessage(p, "That block is not a valid switch or light source");
				}
			}
		}
	}
}
