package net.neferett.linaris.pvpbox.commands;

import java.text.DecimalFormat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.neferett.linaris.pvpbox.players.M_Player;
import net.neferett.linaris.pvpbox.players.PlayerManagers;

public class LevelCommand implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender arg0, final Command arg1, final String arg2, final String[] arg3) {
		if (!(arg0 instanceof Player))
			return false;
		if (arg1.getLabel().equalsIgnoreCase("level"))
			if (arg3.length != 1) {
				final M_Player p = PlayerManagers.get().getPlayer((Player) arg0);
				final float ratio = p.getDeaths() <= 0 ? p.getKills() : p.getKills() / (float) p.getDeaths();
				p.sendMessage("§7Vous êtes au level§f: §e" + p.getLevel());
				p.sendMessage("");
				p.sendMessage("§7Kills§f: §e" + p.getKills());
				p.sendMessage("§7Morts§f: §e" + p.getDeaths());
				p.sendMessage("");
				p.sendMessage("§7Ratio§f: §a"
						+ (p.getDeaths() <= 0 ? p.getKills() : new DecimalFormat("####.##").format(ratio)));
				p.sendMessage("");
				p.sendMessage("§7Il vous manque §e" + p.getScore() + " Kills§7 pour passer au level suivant !");
			}
		return false;
	}

}
