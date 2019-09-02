package net.neferett.linaris.pvpbox.listeners.events;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import net.neferett.linaris.pvpbox.handlers.ConfigReader;

public class MoveListener implements Listener {

	@EventHandler
	public void onMove(final PlayerMoveEvent e) {

		if (ConfigReader.getInstance().getGameName().equals("PvPBox") && e.getPlayer().getLocation().getBlock()
				.getRelative(BlockFace.DOWN).getType() == Material.STONE_PLATE) {

			e.getPlayer()
					.setVelocity(e.getPlayer().getLocation().getDirection().multiply(4.0).add(new Vector(0, 8, 0)));
			e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.FIREWORK_LAUNCH, 5.0f, 2.0f);

		}
	}

}
