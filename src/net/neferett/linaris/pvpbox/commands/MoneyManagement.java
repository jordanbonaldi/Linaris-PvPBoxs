package net.neferett.linaris.pvpbox.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.neferett.linaris.pvpbox.handlers.ConfigReader;
import net.neferett.linaris.pvpbox.players.M_Player;
import net.neferett.linaris.pvpbox.players.PlayerManagers;

public class MoneyManagement implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender arg0, final Command arg1, final String arg2, final String[] arg3) {
		if (!ConfigReader.getInstance().isMoney())
			return false;
		if (arg3.length != 3) {
			final M_Player p = PlayerManagers.get().getPlayer((Player) arg0);
			p.sendMessage("§7Vous avez actuellement §e" + p.getMoney() + "$");
			return false;
		}
		if (arg0 instanceof Player && !((Player) arg0).isOp())
			return false;
		if (Bukkit.getPlayer(arg3[1]) == null)
			return false;
		if (arg1.getLabel().equalsIgnoreCase("money")) {
			final M_Player p = PlayerManagers.get().getPlayer(Bukkit.getPlayer(arg3[1]));
			if (arg3[0].equalsIgnoreCase("add")) {
				p.addMoney(Integer.parseInt(arg3[2]), false);
				arg0.sendMessage("§aCompte crédité de §e" + Integer.parseInt(arg3[2]) + "$");
			} else if (arg3[0].equalsIgnoreCase("del")) {
				p.delMoney(Integer.parseInt(arg3[2]), false);
				arg0.sendMessage("§aCompte crédité de §e" + Integer.parseInt(arg3[2]) + "$");
			}
		}
		return false;
	}

}
