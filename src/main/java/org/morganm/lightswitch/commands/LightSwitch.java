/**
 * 
 */
package org.morganm.lightswitch.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.morganm.lightswitch.LightSwitchPlugin;
import org.morganm.lightswitch.entity.Circuit;
import org.morganm.lightswitch.entity.CircuitSwitch;

/**
 * @author morganm
 *
 */
public class LightSwitch extends Command {
	private LightSwitchPlugin plugin;
	
	public LightSwitch() {
		super("lightswitch");
	}
	
	/* (non-Javadoc)
	 * @see org.bukkit.command.Command#execute(org.bukkit.command.CommandSender, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean execute(CommandSender sender, String arg1, String[] arg2) {
		Player p = null;
		
		if( sender instanceof Player )
			p = (Player) sender;
		
		{
			// toggle lightswitch mode
//			plugin.getManager().toggleSwitchMode(p);
		}
		
		{
			// name current active circuit
			Circuit circuit = plugin.getStorage().getCircuitByName(arg1);
			if( circuit == null ) {
				// print error message or auto-create?
			}
			else
				plugin.getManager().setActiveCircuit(p, circuit);
		}
		
		{
			// create new circuit
			Circuit circuit = plugin.getStorage().getCircuitByName(arg1);
			if( circuit != null ) {
				// print error message, circuit already exists
			}
			else {
				circuit = new Circuit();
				circuit.setCircuitName(arg1);
				plugin.getStorage().writeCircuit(circuit);
				plugin.getManager().setActiveCircuit(p, circuit);
			}
		}
		
		{
			// toggle active circuit mode using closest circuit
			CircuitSwitch circuitSwitch = plugin.getManager().findNearestCircuitSwitch(p.getLocation());
			if( circuitSwitch != null ) {
				Circuit circuit = circuitSwitch.getCircuit();
				plugin.getManager().setActiveCircuit(p, circuit);
			}
			else
				; // print some error message
		}
		
		{
			// delete a circuit element
			plugin.getManager().setDeleteMode(p);
		}
		
		// TODO Auto-generated method stub;
		return false;
	}

}
