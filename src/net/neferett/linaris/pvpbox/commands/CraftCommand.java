package net.neferett.linaris.pvpbox.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.neferett.linaris.pvpbox.players.PlayerManagers;

public class CraftCommand extends PlayerManagers implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (!(arg0 instanceof Player))
			return (false);
		if (getPlayer((Player)arg0).getRank().getVipLevel() < 2){
			arg0.sendMessage("§cIl faut être §bMegaVIP §c ou §a+ §cpour executer cette commande !");
			return true;
		}
		if (arg1.getLabel().equalsIgnoreCase("craft")){
			Player p = (Player) arg0;
			p.openWorkbench(null, true);
		}
		return false;
	}

}
