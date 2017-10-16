package net.neferett.linaris.pvpbox.events.players;

import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.neferett.linaris.BukkitAPI;
import net.neferett.linaris.api.PlayerData;
import net.neferett.linaris.api.PlayerLocal;
import net.neferett.linaris.api.Rank;
import net.neferett.linaris.pvpbox.handlers.ConfigReader;
import net.neferett.linaris.pvpbox.handlers.DataReader;
import net.neferett.linaris.pvpbox.utils.Maths;
import net.neferett.linaris.utils.TitleUtils;

public abstract class PlayerActions {

	protected static HashMap<Player, Location>	oldpos	= new HashMap<>();
	protected String							name;
	protected Player							p;
	protected PlayerData						pd;
	protected PlayerLocal						pl;
	protected Rank								rank;
	protected DataReader						rd;

	public PlayerActions(final Player p) {
		this.p = p;
		this.name = p.getName().toLowerCase();
		this.pd = BukkitAPI.get().getPlayerDataManager().getPlayerData(this.name);
		this.pl = BukkitAPI.get().getPlayerLocalManager().getPlayerLocal(this.name);
		try {
			this.rd = new DataReader(p);
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.rank = this.pd.getPRank();
	}

	public abstract void Actions();

	public void addDeath() {
		this.rd.setDeaths(this.getDeaths() + 1);
	}

	public void addKill() {
		this.rd.setKills(this.getKills() + 1);
	}

	public void addMoney(final int value) {
		this.rd.setMoney(this.getMoney() + value);
	}

	public boolean CanLevelUp() {
		return this.rd.getScore() <= 0;
	}

	public void delMoney(final int value) {
		this.rd.setMoney(this.getMoney() - value);
	}

	public void delScore() {
		if (this.getScore() - 1 < 0)
			return;
		this.rd.setScore(this.getScore() - 1);
	}

	public Location getBackPos() {
		if (!oldpos.containsKey(this.p))
			oldpos.put(this.p, ConfigReader.getInstance().getSpawn());
		return oldpos.get(this.p);
	}

	public int getDeaths() {
		return this.rd.getDeaths();
	}

	public int getKills() {
		return this.rd.getKills();
	}

	public int getLevel() {
		return this.rd.getLevel();
	}

	public int getMoney() {
		return this.rd.getMoney();
	}

	public Player getPlayer() {
		return this.p;
	}

	public PlayerData getPlayerData() {
		return this.pd;
	}

	public PlayerLocal getPlayerLocal() {
		return this.pl;
	}

	public Rank getRank() {
		return this.rank;
	}

	public DataReader getRd() {
		return this.rd;
	}

	public int getScore() {
		return this.rd.getScore();
	}

	public void LevelUP() {
		this.rd.setLevel(this.getLevel() + 1);
		TitleUtils.sendTitle(this.getPlayer(), "§7Level §e" + this.getLevel(), "§6+ 1 Level");
		this.setScore(this.getLevel() * 5 * this.getLevel() * Maths.Rand(0, this.getLevel() * 2)
				+ Maths.Rand(0, this.getLevel() * 5));
	}

	public void setBackPos(final Location pos) {
		oldpos.put(this.p, pos);
	}

	public void setScore(final int a) {
		this.rd.setScore(a);
	}

	public void tp(final Location loc) {
		this.p.teleport(loc);
	}

}
