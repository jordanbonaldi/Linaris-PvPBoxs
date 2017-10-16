package net.neferett.linaris.pvpbox.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.neferett.linaris.pvpbox.events.players.PlayerManagers;

public class Classement extends PlayerManagers implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (!(arg0 instanceof Player))
			return (false);
		if (arg1.getLabel().equalsIgnoreCase("Classement")){
			Player p = (Player) arg0;
			net.neferett.linaris.pvpbox.handlers.Classement.getInstance().getClassement(p);
		}
		return false;
	}

}
