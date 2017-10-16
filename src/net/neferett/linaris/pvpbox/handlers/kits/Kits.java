package net.neferett.linaris.pvpbox.handlers.kits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.neferett.linaris.api.Rank;
import net.neferett.linaris.pvpbox.handlers.ConfigReader;
import net.neferett.linaris.utils.ItemBuilder;

public class Kits {

	protected String 			name;
	protected String			rank;
	protected int				price;
	protected long				cooldown;
	protected ItemBuilder		kitdisp;
	protected int				slot;
	protected List<ItemStack>	items = new ArrayList<>();
	
	public Kits(String name, int slot, ItemBuilder kitdisplay, int price, String rank, long cooldown, ItemStack... items){
		this.name = name;
		this.rank = rank;
		this.price = price;
		this.slot = slot;
		this.cooldown = cooldown;
		this.kitdisp = kitdisplay;
		this.items = Arrays.asList(items);
	}
	
	public long getCooldown() {
		return cooldown;
	}
	
	public int getSlot() {
		return slot;
	}
	
	public ItemStack getKitdisp() {
		kitdisp.clear();
		kitdisp.setTitle(name);
		kitdisp.addLores("", "§7Prix§f: §e" + (getPrice() == 0 ? "§cGratuit" : (getPrice() + "$")), "");
		if (!getRank().equals("none"))
			kitdisp.addLores("§7Grade§f: " + "§" + Rank.get(getRank()).getColor() + getRank(), "");
		kitdisp.addLores("§bClique droit pour voir le kit !", "§aClique gauche pour acheter !");
		return kitdisp.build();
	}
	
	public void giveKits(Player p){
		getItems().forEach(item -> {
			if (item != null && item.getType() != null)
				if (ConfigReader.getInstance().getGameName().contains("Cheat") && item.getType().equals(Material.GOLDEN_APPLE)){
					item.setDurability((short) 1);
					p.getInventory().addItem(item);
				}
				else
					p.getInventory().addItem(item);
		});
	}
	
	public int getPrice() {
		return price;
	}
	
	public String getName() {
		return name;
	}
	
	public String getRank() {
		return rank;
	}
	
	public List<ItemStack> getItems() {
		return items;
	}
	
}
