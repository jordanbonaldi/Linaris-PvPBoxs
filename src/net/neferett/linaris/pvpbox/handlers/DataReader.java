package net.neferett.linaris.pvpbox.handlers;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class DataReader {

	protected FileConfiguration	configfile;

	protected File				data;
	private final String		p;

	public DataReader(final Player p) throws IOException {
		this.p = p.getName().toLowerCase();
		this.data = new File("plugins/PvPBox/data/" + this.p + ".dat");
		this.configfile = YamlConfiguration.loadConfiguration(this.data);
		this.configfile.save(this.data);
	}

	public DataReader(final String p) {
		this.p = p.toLowerCase();
	}

	public int getDeaths() {
		return this.configfile.getInt(this.p + ".deaths");
	}

	public int getKills() {
		return this.configfile.getInt(this.p + ".kills");
	}

	public int getLevel() {
		return this.configfile.getInt(this.p + ".level");
	}

	public int getMoney() {
		return this.configfile.getInt(this.p + ".money");
	}

	public int getScore() {
		return this.configfile.getInt(this.p + ".score");
	}

	public boolean isExists() {
		return new File("plugins/PvPBox/data/" + this.p + ".dat").length() > 0;
	}

	public void save() {
		try {
			this.configfile.save(this.data);
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setDeaths(final int i) {
		this.configfile.set(this.p + ".deaths", i);
		this.save();
	}

	public void setKills(final int i) {
		this.configfile.set(this.p + ".kills", i);
		this.save();
	}

	public void setLevel(final int i) {
		this.configfile.set(this.p + ".level", i);
		this.save();
	}

	public void setMoney(final int i) {
		this.configfile.set(this.p + ".money", i);
		this.save();
	}

	public void setScore(final int i) {
		this.configfile.set(this.p + ".score", i);
		this.save();
	}

}
