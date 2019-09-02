package net.neferett.linaris.pvpbox.handlers.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.neferett.linaris.pvpbox.handlers.ConfigReader;
import net.neferett.linaris.pvpbox.utils.GuiManager;
import net.neferett.linaris.pvpbox.utils.GuiScreen;
import net.neferett.linaris.utils.ItemBuilder;

public class GuiShowKits extends GuiScreen{

	protected String name;
	protected GuiScreen last;
	
	public GuiShowKits(String name, Player p, GuiScreen last) {
		super("Items dans Kit " + name, 5, p, false);
		this.name = name;
		this.last = last;
		build();
	}

	@Override
	public void drawScreen() {
		KitsManager.getInstance().getKits().forEach((kit) -> {
			if (kit.getName().equals(name)){
				kit.getItems().forEach((item) -> {
					if (item != null && item.getType() != null)
						if (ConfigReader.getInstance().getGameName().contains("Cheat") && item.getType().equals(Material.GOLDEN_APPLE)){
							item.setDurability((short) 1);
							addItem(item);
						}
						else
							addItem(item);
				});
			}
		});
		setItemLine(new ItemBuilder(Material.ARROW).setTitle("§6Retour").build(), 5, 9);
	}

	@Override
	public void onClick(ItemStack item, InventoryClickEvent e) {
		if (e.getCurrentItem().getType().equals(Material.ARROW) && e.getCurrentItem().getItemMeta().getDisplayName().contains("Retour")){
			GuiManager.openGui(last);
		}
	}

	@Override
	public void onClose() {}

	@Override
	public void onOpen() {}

}
