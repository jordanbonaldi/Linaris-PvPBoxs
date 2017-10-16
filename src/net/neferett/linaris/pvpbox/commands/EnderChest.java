package net.neferett.linaris.pvpbox.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.neferett.linaris.pvpbox.events.players.PlayerManagers;
import net.neferett.linaris.pvpbox.handlers.ConfigReader;
import net.neferett.linaris.pvpbox.listeners.CancelledEvents;

public class EnderChest extends PlayerManagers implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (!(arg0 instanceof Player))
			return (false);
		if (getPlayer((Player)arg0).getRank().getVipLevel() < 3){
			arg0.sendMessage("§cIl faut être §aHéros §cpour executer cette commande !");
			return (true);
		}
		if (arg1.getLabel().equalsIgnoreCase("ec") || arg1.getLabel().equalsIgnoreCase("enderchest")){
			Player p = (Player) arg0;
			if ((!ConfigReader.getInstance().getKitOutside() && CancelledEvents.cb.isInside(p.getLocation())) || ConfigReader.getInstance().getKitOutside())
				p.openInventory(p.getEnderChest());
			else
				p.sendMessage("§cIl faut etre au spawn !");
		}
		return false;
	}

}
