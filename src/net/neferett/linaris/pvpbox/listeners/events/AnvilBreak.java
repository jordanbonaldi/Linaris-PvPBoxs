package net.neferett.linaris.pvpbox.listeners.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class AnvilBreak implements Listener {

	@EventHandler
	public void onAnvilBreak(final PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_AIR) return;
		if (e.getClickedBlock().getType() != null && e.getClickedBlock().getType().equals(Material.ANVIL))
			e.getClickedBlock().getLocation().getBlock().setType(Material.ANVIL);
	}

}
