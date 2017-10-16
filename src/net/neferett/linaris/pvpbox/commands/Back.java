package net.neferett.linaris.pvpbox.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.neferett.linaris.pvpbox.events.players.PlayerActions;
import net.neferett.linaris.pvpbox.events.players.PlayerManagers;
import net.neferett.linaris.pvpbox.handlers.ConfigReader;

public class Back extends PlayerManagers implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (!(arg0 instanceof Player))
			return (false);
		if (getPlayer((Player)arg0).getRank().getVipLevel() < 3){
			arg0.sendMessage("§cIl faut être §aHéros §cpour executer cette commande !");
			return true;
		}
		if (arg1.getLabel().equalsIgnoreCase("back")){
			Player p = (Player) arg0;
			ActionOnPlayer(new PlayerActions(p) {
					
					@Override
					public void Actions() {
						if (getPlayer().getLocation().getY() > ConfigReader.getInstance().getMaxHeight()){
							tp(getBackPos());
							return;
						}else{
							getPlayer().sendMessage("§cVous devez être au spawn !");
						}
					}
			});
		}
		return false;
	}

}
