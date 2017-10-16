package net.neferett.linaris.pvpbox.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.neferett.linaris.pvpbox.events.players.PlayerActions;
import net.neferett.linaris.pvpbox.events.players.PlayerManagers;
import net.neferett.linaris.pvpbox.handlers.ConfigReader;

public class PayCommand extends PlayerManagers implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender arg0, final Command arg1, final String arg2, final String[] arg3) {
		if (!ConfigReader.getInstance().isMoney())
			return false;
		if (!(arg0 instanceof Player))
			return false;
		if (arg3.length != 2) {
			arg0.sendMessage("§c/pay <player> <montant>");
			return false;
		} else if (arg3[1].length() > 8 || arg3[1].charAt(0) == '-')
			return false;
		if (Bukkit.getPlayer(arg3[0]) == null || Bukkit.getPlayer(arg3[0]).getName().equalsIgnoreCase(arg0.getName())) {
			arg0.sendMessage("§cLe joueur " + arg3[1] + " n'existe pas !");
			return false;
		}
		if (arg1.getLabel().equalsIgnoreCase("pay"))
			this.ActionOnPlayer(new PlayerActions(Bukkit.getPlayer(arg0.getName())) {

				@Override
				public void Actions() {
					final int montant = Integer.parseInt(arg3[1]);
					if (this.getMoney() - montant >= 0) {
						this.delMoney(montant);
						arg0.sendMessage("§7Paiement de §e" + montant + "$ §7envoyé a §e" + arg3[0]);
						new PlayerManagers().ActionOnPlayer(new PlayerActions(Bukkit.getPlayer(arg3[0])) {

							@Override
							public void Actions() {
								this.getPlayer().sendMessage("§7Vous venez de recevoir §e" + Integer.parseInt(arg3[1])
										+ "$§7 de la part de §e" + arg0.getName());
								this.addMoney(Integer.parseInt(arg3[1]));
							}
						});
					} else {
						arg0.sendMessage(
								"§cIl vous manque §e" + (montant - this.getMoney()) + "$§c pour faire ce paiement !");
						return;
					}
				}
			});
		return false;
	}

}
