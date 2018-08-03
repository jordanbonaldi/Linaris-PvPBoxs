package net.neferett.linaris.pvpbox.players;

import java.text.DecimalFormat;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.neferett.linaris.PlayersHandler.Players;
import net.neferett.linaris.api.ranks.RankAPI;
import net.neferett.linaris.pvpbox.handlers.ConfigReader;
import net.neferett.linaris.pvpbox.utils.Maths;
import net.neferett.linaris.utils.PlayerUtils;
import net.neferett.linaris.utils.TitleUtils;

public class M_Player extends Players {

	static String		_death	= ConfigReader.getInstance().getGameName() + "_deaths";
	static String		_kills	= ConfigReader.getInstance().getGameName() + "_kills";
	static String		_level	= ConfigReader.getInstance().getGameName() + "_level";
	static String		_money	= ConfigReader.getInstance().getGameName() + "_money";
	static String		_played	= ConfigReader.getInstance().getGameName() + "_played";
	static String		_score	= ConfigReader.getInstance().getGameName() + "_score";
	protected String	name;
	protected Location	old;
	private final long	tms;

	public M_Player(final Player p) {
		super(p);
		this.name = p.getName().toLowerCase();
		this.tms = System.currentTimeMillis();
	}

	public void addDeath() {
		this.getPlayerData().setInt(_death, this.getDeaths() + 1);
	}

	public void addKill() {
		this.getPlayerData().setInt(_kills, this.getKills() + 1);
	}

	public void addMoney(final float f, final boolean a) {
		if (a) {
			PlayerUtils.sendActionMessage(this.getPlayer(), "§6+" + f + " §f(§a+ " + (int) (f * this.getRate("1") - f)
					+ " §ebonus §" + this.getRank().getColor() + this.getRank().getName() + "§f) §6$");
			this.getPlayerData().setInt(_money, this.getMoney() + (int) f + (int) (f * this.getRate("1") - f));
		} else
			this.getPlayerData().setInt(_money, (int) (this.getMoney() + f));
	}

	public boolean CanLevelUp() {
		return this.getScore() <= 0;
	}

	public void delMoney(final int value, final boolean a) {
		if (a) {
			PlayerUtils.sendActionMessage(this.getPlayer(), "§6-" + value + "§f (§a+" + value * this.getRate("0")
					+ " §ebonus §" + this.getRank().getColor() + this.getRank().getName() + "§f) §6$");
			this.getPlayerData().setInt(_money, (int) (this.getMoney() - (value - value * this.getRate("0"))));
		} else
			this.getPlayerData().setInt(_money, this.getMoney() - value);
	}

	public void delScore() {
		if (this.getScore() - 1 < 0)
			return;
		this.setScore(this.getScore() - 1);
	}

	public Location getBackPos() {
		if (this.old == null)
			this.old = ConfigReader.getInstance().getSpawn();
		return this.old;
	}

	public int getDeaths() {
		return this.getPlayerData().getInt(_death, 0);
	}

	public int getKills() {
		return this.getPlayerData().getInt(_kills, 0);
	}

	@Override
	public int getLevel() {
		return this.getPlayerData().getInt(_level, 0);
	}

	public int getMoney() {
		return this.getPlayerData().getInt(_money, 0);
	}

	public RankAPI getRank() {
		return this.getPlayerData().getRank();
	}

	public float getRate(final String s) {
		final String number = s + "." + this.getRank().getVipLevel();
		return Float.parseFloat(number);
	}

	public String getRatio() {
		return new DecimalFormat("####.##").format(
				this.getDeaths() <= 0 ? (float) this.getKills() : (float) this.getKills() / (float) this.getDeaths());
	}

	public int getScore() {
		return this.getPlayerData().getInt(_score, 0);
	}

	public long getSeconds() {
		return (System.currentTimeMillis() - this.tms) / 1000;
	}

	public void LevelUP() {
		this.getPlayerData().setInt(_level, this.getPlayerData().getInt(_level, 0) + 1);
		TitleUtils.sendTitle(this.getPlayer(), "§7Level §e" + this.getLevel(), "§6+ 1 Level");
		this.addMoney(1000, true);
		this.setScore(this.getLevel() * 5 + this.getLevel() * Maths.Rand(0, this.getLevel() * 2)
				+ Maths.Rand(0, this.getLevel() * 5));
	}

	public void removePlayer() {
		this.getPlayerData().setLong(_played, this.getPlayerData().getLong(_played, 0) + this.getSeconds());
	}

	public void setBackPos(final Location pos) {
		this.old = pos.clone();
	}

	public void setMoney(final int i) {
		this.getPlayerData().setInt(_money, i);

	}

	public void setScore(final int a) {
		this.getPlayerData().setInt(_score, a);
	}

	public void tp(final Location loc) {
		this.getPlayer().teleport(loc);
	}

}
