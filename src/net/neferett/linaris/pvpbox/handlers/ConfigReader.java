package net.neferett.linaris.pvpbox.handlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import net.neferett.linaris.pvpbox.handlers.kits.KitsManager;
import net.neferett.linaris.utils.ItemBuilder;

public class ConfigReader {

	protected static ConfigReader instance;

	public static ConfigReader getInstance() {
		return instance == null ? new ConfigReader() : instance;
	}

	protected FileConfiguration configfile;

	public ConfigReader() {
		instance = this;
		final File configFile = new File("plugins/PvPBox/config.yml");
		this.configfile = YamlConfiguration.loadConfiguration(configFile);
		try {
			this.configfile.save(configFile);
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean checkInside() {
		return this.configfile.getBoolean("config.check");
	}

	public boolean getChectClickable() {
		return this.configfile.getBoolean("config.chestclick");
	}

	public String getGameName() {
		return this.configfile.getString("config.gamename");
	}

	@SuppressWarnings("deprecation")
	public ItemBuilder getItem(final int pos) {
		ItemBuilder tmp;

		tmp = new ItemBuilder(new ItemStack(this.configfile.getInt("kits.kit" + pos + ".disp.item")));
		if (this.configfile.getInt("kits.kit" + pos + ".disp.level") != 0)
			tmp.addEnchantment(Enchantment.getById(this.configfile.getInt("kits.kit" + pos + ".disp.enchantment")),
					this.configfile.getInt("kits.kit" + pos + ".disp.level"));
		return tmp;
	}

	@SuppressWarnings("deprecation")
	public ItemStack[] getItems(final int pos) {
		final int itemsnb = this.configfile.getInt("kits.kit" + pos + ".itemnb");
		final ItemStack[] items = new ItemStack[itemsnb + 1];
		ItemBuilder tmp;

		for (int i = 1; i <= itemsnb; i++) {
			tmp = new ItemBuilder(new ItemStack(this.configfile.getInt("kits.kit" + pos + ".Item." + i + ".id"),
					this.configfile.getInt("kits.kit" + pos + ".Item." + i + ".nb")));
			if (this.configfile.getInt("kits.kit" + pos + ".Item." + i + ".level") != 0) tmp.addEnchantment(
					Enchantment.getById(this.configfile.getInt("kits.kit" + pos + ".Item." + i + ".enchantment")),
					this.configfile.getInt("kits.kit" + pos + ".Item." + i + ".level"));
			items[i - 1] = tmp.build();
		}
		return items;
	}

	public boolean getKitOutside() {
		return this.configfile.getBoolean("config.inside.useoutside");
	}

	public void getKits() {
		final int kitsnb = this.configfile.getInt("kits.nb");

		for (int i = 1; i <= kitsnb; i++)
			KitsManager.getInstance().addKits(this.configfile.getString("kits.kit" + i + ".name"),
					this.configfile.getInt("kits.kit" + i + ".slots"), this.getItem(i),
					this.configfile.getInt("kits.kit" + i + ".price"),
					this.configfile.getString("kits.kit" + i + ".rank"),
					this.configfile.getLong("kits.kit" + i + ".cooldown"), this.getItems(i));
	}

	public int getKitSlots() {
		return this.configfile.getInt("kits.slots");
	}

	public Location getLocation(final String configpath) {
		return new Location(Bukkit.getWorld(this.configfile.getString(configpath + ".world")),
				this.configfile.getDouble(configpath + ".x"), this.configfile.getDouble(configpath + ".y"),
				this.configfile.getDouble(configpath + ".z"), this.configfile.getInt(configpath + ".yaw"),
				this.configfile.getInt(configpath + ".pitch"));
	}

	public List<Location> getLocationChest() {
		final List<Location> locs = new ArrayList<>();
		this.configfile.getList("chests").forEach(loc -> {
			final String[] locarray = loc.toString().split(":");
			locs.add(new Location(Bukkit.getWorld(locarray[0]), Integer.parseInt(locarray[1]),
					Integer.parseInt(locarray[2]), Integer.parseInt(locarray[3])));
		});
		return locs;
	}

	public double getMaxHeight() {
		return this.configfile.getDouble("config.maxheight");
	}

	public Location getPos1() {
		return this.getLocation("config.inside.p1");
	}

	public Location getPos2() {
		return this.getLocation("config.inside.p2");
	}

	public Location getSpawn() {
		return this.getLocation("config.spawn");
	}

	public boolean isMoney() {
		return this.configfile.getBoolean("config.money");
	}
}
