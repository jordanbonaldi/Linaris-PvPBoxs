package net.neferett.linaris.pvpbox.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.neferett.linaris.pvpbox.handlers.kits.KitsManager;

public class GiveKits implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender arg0, final Command arg1, final String arg2, final String[] arg3) {
		if (arg0 instanceof Player)
			return false;
		if (arg1.getLabel().equalsIgnoreCase("givekits")) {
			final Player p = Bukkit.getPlayer(arg3[0]);

			if (p == null)
				return false;
			KitsManager.getInstance().getKitByNameReal(arg3[1]).giveKits(p);
		}
		return false;
	}

}
