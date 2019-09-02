package net.neferett.linaris.pvpbox.handlers.kits;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.inventory.ItemStack;

import net.neferett.linaris.utils.ItemBuilder;

public class KitsManager {

	protected static KitsManager instance;

	public static KitsManager getInstance() {
		return instance;
	}

	protected List<Kits> kits = new ArrayList<>();

	public KitsManager() {
		instance = this;
	}

	public void addKits(final String name, final int slot, final ItemBuilder disp, final int price, final String perm,
			final long cooldown, final ItemStack... items) {
		this.kits.add(new Kits(name, slot, disp, price, perm, cooldown, items));
	}

	public Kits getKitByName(final String name) {
		return this.kits.stream().filter(kit -> kit.getName().equals(name)).collect(Collectors.toList()).get(0);
	}

	public Kits getKitByNameReal(final String name) {
		return this.kits.stream().filter(kit -> kit.getName().substring(2).equals(name)).collect(Collectors.toList())
				.get(0);
	}

	public List<Kits> getKits() {
		return this.kits;
	}

}
