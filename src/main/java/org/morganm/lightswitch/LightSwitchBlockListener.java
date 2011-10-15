/**
 * 
 */
package org.morganm.lightswitch;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.morganm.lightswitch.entity.Circuit;
import org.morganm.lightswitch.entity.CircuitSwitch;

/**
 * @author morganm
 *
 */
public class LightSwitchBlockListener extends BlockListener {
	private LightSwitchPlugin plugin;
	private LightSwitchManager manager;
	
	public LightSwitchBlockListener(LightSwitchPlugin plugin) {
		this.plugin = plugin;
		this.manager = plugin.getManager();
	}
	
	@Override
	public void onBlockPlace(BlockPlaceEvent event) {
		if( event.isCancelled() )
			return;
		
		Player p = event.getPlayer();
		if( !manager.isInActiveCircuitMode(p) )
			return;

		Block b = event.getBlock();
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
	
	@Override
	public void onBlockRedstoneChange(BlockRedstoneEvent event) {
		Block b = event.getBlock();
		
		CircuitSwitch circuitSwitch = manager.getCircuitSwitchByLocation(b.getLocation());
		if( circuitSwitch != null ) {
			int oldCurrent = event.getOldCurrent();
			int newCurrent = event.getNewCurrent();
			
			// we only respond to binary current changes - off/on. This means if this event
			// represents a current change from 5 to 7 (both "ON" state), we ignore it.
			if( oldCurrent == 0 && newCurrent > 0 ||
				newCurrent == 0 && oldCurrent > 0 )
			{
				manager.toggleCircuit(circuitSwitch.getCircuit());
			}
		}
		
		// TODO: if implementing signs, detect changes to sign as a switch event
	}
	
	@Override
	public void onBlockBreak(BlockBreakEvent event) {
		if( event.isCancelled() )
			return;
		
		if( manager.isCircuitLocation(event.getBlock().getLocation()) ) {
			manager.deleteEntity(event.getBlock().getLocation());
			plugin.sendMessage(event.getPlayer(), "Circuit entity removed");
		}
		
		/*
		String owner = manager.getProtectedOwner(event.getBlock().getLocation());
		if( owner == null )
			return;
		
		// only the owner can destroy a block they own
		if( !owner.equals(event.getPlayer().getName()) ) {
			event.setCancelled(true);
		}
		else {
			// do the delete
		}
		*/
	}
}
