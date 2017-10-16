package net.neferett.linaris.pvpbox.handlers;

import java.util.HashMap;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import net.neferett.linaris.BukkitAPI;
import net.neferett.linaris.pvpbox.events.players.PlayerManagers;

public class Classement extends PlayerManagers {

	public static Classement instance;

	public static Classement getInstance() {
		return instance == null ? new Classement() : instance;
	}

	HashMap<Player, Integer>	classement	= new HashMap<>();

	int							i			= 0;

	public Classement() {
		instance = this;
	}

	public int CalcClassement() {
		this.i = 0;
		this.ConvertAllPlayer().stream().sorted((pa1, pa2) -> pa2.getKills() - pa1.getKills())
				.collect(Collectors.toList()).forEach((pa) -> {
					++this.i;
					this.classement.put(pa.getPlayer(), this.i);
				});
		return this.i;
	}

	public void getClassement(final Player p) {
		this.i = 0;
		p.sendMessage("§b§m===================================");
		this.ConvertAllPlayer().stream().sorted((pa1, pa2) -> pa2.getKills() - pa1.getKills())
				.filter(pa -> !pa.getPlayerData().contains("invisible") || !pa.getPlayerData().getBoolean("invisible"))
				.collect(Collectors.toList()).forEach((pa) -> {
					p.sendMessage("§e" + ++this.i + "§b. §7"
							+ (pa.getRank().getVipLevel() > 0 ? pa.getRank()
									.getPrefix(BukkitAPI.get().getPlayerDataManager().getPlayerData(p.getName())) + "§"
									+ pa.getRank().getColor() : "")
							+ pa.getPlayer().getName() + "§e avec §c" + pa.getKills() + " Kills");
				});
		p.sendMessage("§b§m===================================");
	}

	public int getPlayerClassement(final Player p) {
		this.CalcClassement();
		return this.classement.get(p);
	}

}
