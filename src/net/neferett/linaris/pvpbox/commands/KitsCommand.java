package net.neferett.linaris.pvpbox.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.neferett.linaris.pvpbox.handlers.ConfigReader;
import net.neferett.linaris.pvpbox.handlers.kits.GuiKits;
import net.neferett.linaris.pvpbox.listeners.CancelledEvents;
import net.neferett.linaris.pvpbox.utils.GuiManager;

public class KitsCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (!(arg0 instanceof Player))
			return (false);
		if (arg1.getLabel().equalsIgnoreCase("kits") || arg1.getLabel().equalsIgnoreCase("kit")){
			Player p = (Player) arg0;
			if ((!ConfigReader.getInstance().getKitOutside() && CancelledEvents.cb.isInside(p.getLocation())) || ConfigReader.getInstance().getKitOutside())
				GuiManager.openGui(new GuiKits(p));
			else
				p.sendMessage("§cIl faut etre au spawn !");
		}
		return false;
	}

}
