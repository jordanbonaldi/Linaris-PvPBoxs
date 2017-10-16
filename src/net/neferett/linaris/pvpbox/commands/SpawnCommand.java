package net.neferett.linaris.pvpbox.commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.neferett.linaris.pvpbox.events.players.PlayerActions;
import net.neferett.linaris.pvpbox.events.players.PlayerManagers;
import net.neferett.linaris.pvpbox.handlers.ConfigReader;
import net.neferett.linaris.pvpbox.listeners.CancelledEvents;
import net.neferett.linaris.utils.tasksmanager.TaskManager;

public class SpawnCommand extends PlayerManagers implements CommandExecutor{

	public static HashMap<String, Boolean> waiting = new HashMap<>();
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (!(arg0 instanceof Player))
			return (false);
		if (!waiting.containsKey(arg0.getName().toLowerCase()) && arg1.getLabel().equalsIgnoreCase("spawn")){
			Player p = (Player) arg0;
			ActionOnPlayer(new PlayerActions(p) {
				
				@Override
				public void Actions() {
					if (!ConfigReader.getInstance().getKitOutside()){
						if (CancelledEvents.cb.isInside(p.getLocation())){
							tp(ConfigReader.getInstance().getSpawn());
							return;
						}
						getPlayer().sendMessage("§cNe bougez pas pendant environ 5 secondes !");
						waiting.put(getPlayer().getName().toLowerCase(), true);
						TaskManager.scheduleSyncRepeatingTask("tpspawn" + arg0, () -> {
							if (waiting.containsKey(getPlayer().getName().toLowerCase())){
								waiting.remove(getPlayer().getName().toLowerCase());
								TaskManager.cancelTaskByName("tpspawn" + arg0);
								tp(ConfigReader.getInstance().getSpawn());
							}
							else
								TaskManager.cancelTaskByName("tpspawn" + arg0);
						}, 5 * 20, 1);
					}
					else {
						if (getPlayer().getLocation().getY() > ConfigReader.getInstance().getMaxHeight()){
							tp(ConfigReader.getInstance().getSpawn());
							return;
						}
						getPlayer().sendMessage("§cNe bougez pas pendant environ 5 secondes !");
						waiting.put(getPlayer().getName().toLowerCase(), true);
						TaskManager.scheduleSyncRepeatingTask("tpspawn" + arg0, () -> {
							if (waiting.containsKey(getPlayer().getName().toLowerCase())){
								waiting.remove(getPlayer().getName().toLowerCase());
								TaskManager.cancelTaskByName("tpspawn" + arg0);
								tp(ConfigReader.getInstance().getSpawn());
							}
							else
								TaskManager.cancelTaskByName("tpspawn" + arg0);
						}, 5 * 20, 1);
					}
					
				}
			});
		}
		return false;
	}

}
