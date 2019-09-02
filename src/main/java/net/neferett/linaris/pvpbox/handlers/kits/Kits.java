package net.neferett.linaris.pvpbox.handlers.kits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.neferett.linaris.api.ranks.RankManager;
import net.neferett.linaris.pvpbox.handlers.ConfigReader;
import net.neferett.linaris.utils.ItemBuilder;

public class Kits {

	private long				cooldown;
	private List<ItemStack>	items;
	private ItemBuilder		kitdisp;
	protected String			name;
	private int				price;
	private String			rank;
	private int				slot;

	public Kits(final String name, final int slot, final ItemBuilder kitdisplay, final int price, final String rank,
			final long cooldown, final ItemStack... items) {
		this.name = name;
		this.rank = rank;
		this.price = price;
		this.slot = slot;
		this.cooldown = cooldown;
		this.kitdisp = kitdisplay;
		this.items = Arrays.asList(items);
	}

	public long getCooldown() {
		return this.cooldown;
	}

	public List<ItemStack> getItems() {
		return this.items;
	}

	public ItemStack getKitdisp() {
		this.kitdisp.clear();
		this.kitdisp.setTitle(this.name);
		this.kitdisp.addLores("", "§7Prix§f: §e" + (this.getPrice() == 0 ? "§cGratuit" : this.getPrice() + "$"), "");
		if (!this.getRank().equals("none"))
			this.kitdisp.addLores(
					"§7Grade§f: " + "§" + RankManager.getInstance().getRank(this.getRank()).getColor() + this.getRank(),
					"");
		this.kitdisp.addLores("§bClique droit pour voir le kit !", "§aClique gauche pour acheter !");
		return this.kitdisp.build();
	}

	public String getName() {
		return this.name;
	}

	public int getPrice() {
		return this.price;
	}

	public String getRank() {
		return this.rank;
	}

	public int getSlot() {
		return this.slot;
	}

	public void giveKits(final Player p) {
		this.getItems().forEach(item -> {
			if (item != null && item.getType() != null)
				if (ConfigReader.getInstance().getGameName().contains("Cheat")
						&& item.getType().equals(Material.GOLDEN_APPLE)) {
					item.setDurability((short) 1);
					p.getInventory().addItem(item);
				} else if (ConfigReader.getInstance().isDefault()) {
					p.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
					p.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
					p.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
					p.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
					p.getInventory().addItem(item);
				} else
					p.getInventory().addItem(item);
		});
	}

}
