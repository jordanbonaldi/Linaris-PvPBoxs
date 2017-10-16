package net.neferett.linaris.pvpbox.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.neferett.linaris.pvpbox.events.players.PlayerActions;
import net.neferett.linaris.pvpbox.events.players.PlayerManagers;
import net.neferett.linaris.pvpbox.handlers.ConfigReader;

public class MoneyManagement extends PlayerManagers implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (!ConfigReader.getInstance().isMoney())
			return false;
		if (arg3.length != 3){
			ActionOnPlayer(new PlayerActions((Player) arg0) {
				
				@Override
				public void Actions() {
					getPlayer().sendMessage("§7Vous avez actuellement §e" + getMoney() + "$");
				}
			});
			return false;
		}
		if (arg0 instanceof Player && !((Player) arg0).isOp())
			return false;
		if (Bukkit.getPlayer(arg3[1]) == null)
			return false;
		if (arg1.getLabel().equalsIgnoreCase("money")){
			if (arg3[0].equalsIgnoreCase("add")){
				ActionOnPlayer(new PlayerActions(Bukkit.getPlayer(arg3[1])) {
					
					@Override
					public void Actions() {
						addMoney(Integer.parseInt(arg3[2]));
					}
				});
				arg0.sendMessage("§aCompte crédité de §e" + Integer.parseInt(arg3[2]) + "$");
			} else if (arg3[0].equalsIgnoreCase("del")){
				ActionOnPlayer(new PlayerActions(Bukkit.getPlayer(arg3[1])) {
					
					@Override
					public void Actions() {
						delMoney(Integer.parseInt(arg3[2]));
					}
				});
				arg0.sendMessage("§aCompte crédité de §e" + Integer.parseInt(arg3[2]) + "$");
			} 
		}
		return false;
	}

}
