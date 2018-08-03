package net.neferett.linaris.pvpbox.handlers;

import java.text.DecimalFormat;
import java.util.HashMap;

import org.bukkit.entity.Player;

import net.neferett.linaris.BukkitAPI;
import net.neferett.linaris.api.GameServer;
import net.neferett.linaris.api.PlayerData;
import net.neferett.linaris.pvpbox.players.M_Player;
import net.neferett.linaris.pvpbox.players.PlayerManagers;
import net.neferett.linaris.pvpbox.utils.RainbowEffect;
import net.neferett.linaris.utils.ScoreBoardModule;
import net.neferett.linaris.utils.ScoreboardSign;

public class ScoreBoard extends ScoreBoardModule {

	RainbowEffect	NAME;
	String			title;

	public ScoreBoard(final BukkitAPI game) {
		super(game);

		this.NAME = new RainbowEffect("UniverSeven", "§f§l", "§e§l", 40);

	}

	public int getOnlinePlayers() {
		int count = 0;
		for (final GameServer s : BukkitAPI.get().getProxyDataManager().getServers().values())
			count += s.getPlayers();
		return count;
	}

	public String getTimeFormat(final int time) {

		final int minutes = time / 60;
		final int seconds = time % 60;

		return String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
	}

	@Override
	public void onUpdate() {
		this.title = this.NAME.next();
	}

	@Override
	public void onUpdate(final Player p) {
		ScoreboardSign bar = ScoreboardSign.get(p);
		if (bar == null) {
			bar = new ScoreboardSign(p, this.title);
			bar.create();
		}

		final HashMap<Integer, String> lines = new HashMap<>();

		final PlayerData data = BukkitAPI.get().getPlayerDataManager().getPlayerData(p.getName());

		final M_Player pa = PlayerManagers.get().getPlayer(p);
		bar.setObjectiveName(this.title);
		lines.put(14, "§e");
		lines.put(13, "§fRang:" + (data.getRank().getColor() == '7' ? "§7" : "§" + data.getRank().getColor()) + " "
				+ data.getRank().getName());
		lines.put(12, "§4");
		final float ratio = pa.getDeaths() <= 0 ? pa.getKills() : pa.getKills() / (float) pa.getDeaths();
		if (ConfigReader.getInstance().isMoney()) {
			lines.put(11, "§fMonnaie: §e" + pa.getMoney() + "$");
			lines.put(10, "§3");
			lines.put(9, "§fRatio§f: §e" + (pa.getDeaths() <= 0 ? pa.getKills() == 0 ? "§cN/A" : pa.getKills()
					: new DecimalFormat("####.##").format(ratio)));
		} else
			lines.put(9, "§fRatio§f: §e" + (pa.getDeaths() <= 0 ? pa.getKills() == 0 ? "§cN/A" : pa.getKills()
					: new DecimalFormat("####.##").format(ratio)));
		lines.put(8, "§d");
		lines.put(7, "§fKills§f: §c" + (pa.getKills() == 0 ? "§cAucun" : pa.getKills()));
		lines.put(6, "§2");
		lines.put(5, "§fClassement§f: §a" + Classement.getInstance().getPlayerClassement(p) + "e");
		lines.put(4, "§1");
		lines.put(3, "§fLevel§f: §b" + pa.getLevel());
		lines.put(2, "§f");
		lines.put(1, "§e► play.universeven.fr");

		if (lines.isEmpty())
			return;
		for (int i = 1; i < 16; i++)
			if (!lines.containsKey(i)) {
				if (bar.getLine(i) != null)
					bar.removeLine(i);
			} else if (bar.getLine(i) == null)
				bar.setLine(i, lines.get(i));
			else if (!bar.getLine(i).equals(lines.get(i)))
				bar.setLine(i, lines.get(i));

	}

}
