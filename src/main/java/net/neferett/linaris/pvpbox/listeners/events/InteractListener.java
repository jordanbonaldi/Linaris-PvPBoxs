package net.neferett.linaris.pvpbox.listeners.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.neferett.linaris.pvpbox.handlers.ConfigReader;

public class InteractListener implements Listener {

	@EventHandler
	public void blockChestInterract(final PlayerInteractEvent event) {

		if (event.getPlayer().getLocation().getY() < 10) {
			event.setCancelled(true);
			return;
		}
		if (event.getItem() != null && event.getItem().getType() != null
				&& event.getItem().getType().equals(Material.LAVA_BUCKET))
			event.setCancelled(true);
		if (!ConfigReader.getInstance().getChectClickable() && event.getAction() == Action.RIGHT_CLICK_BLOCK)
			if (event.getClickedBlock().getType().equals(Material.CHEST))
				event.setCancelled(true);
	}

	@EventHandler
	public void bucket(final PlayerBucketFillEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onDoubleClick(final InventoryClickEvent e) {
		e.setCancelled(e.getClick().isLeftClick() && e.getClick().equals(ClickType.DOUBLE_CLICK));
	}

	@EventHandler
	public void PlayerBuckEmpty(final PlayerBucketEmptyEvent e) {
		if (e.getBucket() != null && (e.getBucket().equals(Material.LAVA) || e.getBucket().equals(Material.WATER)))
			e.setCancelled(true);
		else if (e.getBucket() != null && e.getBucket().equals(Material.MILK_BUCKET))
			e.getPlayer().getInventory().remove(e.getBucket());
	}

}
