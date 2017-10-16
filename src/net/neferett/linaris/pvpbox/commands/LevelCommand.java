package net.neferett.linaris.pvpbox.commands;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.neferett.linaris.pvpbox.events.players.PlayerActions;
import net.neferett.linaris.pvpbox.events.players.PlayerManagers;
import net.neferett.linaris.pvpbox.handlers.DataReader;

public class LevelCommand extends PlayerManagers implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender arg0, final Command arg1, final String arg2, final String[] arg3) {
		if (!(arg0 instanceof Player))
			return false;
		if (arg1.getLabel().equalsIgnoreCase("level")) {
			final Player p = (Player) arg0;
			if (arg3.length != 1)
				this.ActionOnPlayer(new PlayerActions(p) {

					@Override
					public void Actions() {
						final float ratio = this.getDeaths() <= 0 ? this.getKills()
								: this.getKills() / (float) this.getDeaths();
						this.getPlayer().sendMessage("§7Vous êtes au level§f: §e" + this.getLevel());
						this.getPlayer().sendMessage("");
						this.getPlayer().sendMessage("§7Kills§f: §e" + this.getKills());
						this.getPlayer().sendMessage("§7Morts§f: §e" + this.getDeaths());
						this.getPlayer().sendMessage("");
						this.getPlayer().sendMessage("§7Ratio§f: §a" + (this.getDeaths() <= 0 ? this.getKills()
								: new DecimalFormat("####.##").format(ratio)));
						this.getPlayer().sendMessage("");
						this.getPlayer().sendMessage(
								"§7Il vous manque §e" + this.getScore() + " Kills§7 pour passer au level suivant !");
					}
				});
			else {
				final String player = arg3[0].toLowerCase();
				final DataReader rd = new DataReader(arg3[0]);
				if (!rd.isExists())
					p.sendMessage("§cLe joueur §e" + arg3[0] + " §cn'existe pas !");
				else {
					final int kill = rd.getKills();
					final int deaths = rd.getDeaths();
					final float ratio = deaths <= 0 ? kill : kill / (float) deaths;
					p.sendMessage("§e" + arg3[0] + "§7 est au Level §e" + rd.getLevel());
					p.sendMessage("");
					p.sendMessage("§7Kills§f: §e" + kill);
					p.sendMessage("§7Morts§f: §e" + deaths);
					p.sendMessage("");
					p.sendMessage("§7Ratio§f: §a" + (deaths <= 0 ? kill : new DecimalFormat("####.##").format(ratio)));
					p.sendMessage("");
					p.sendMessage("§7Prochain niveau dans §e" + rd.getScore() + " Kills");
					p.sendMessage("");
					p.sendMessage(
							"§7Actuellement§f: " + (Bukkit.getPlayer(player) == null ? "§cHors-Ligne" : "§aEn ligne"));
				}
			}
		}
		return false;
	}

}
