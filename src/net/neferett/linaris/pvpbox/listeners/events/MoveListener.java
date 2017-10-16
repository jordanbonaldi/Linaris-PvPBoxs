package net.neferett.linaris.pvpbox.listeners.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import net.neferett.linaris.pvpbox.commands.SpawnCommand;
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

		if (SpawnCommand.waiting.containsKey(e.getPlayer().getName().toLowerCase())) {
			final Location from = new Location(Bukkit.getWorld("world"), (int) e.getFrom().getX(),
					(int) e.getFrom().getY(), (int) e.getFrom().getZ());
			final Location to = new Location(Bukkit.getWorld("world"), (int) e.getTo().getX(), (int) e.getTo().getY(),
					(int) e.getTo().getZ());

			if (!from.equals(to)) {
				e.getPlayer().sendMessage("§cTéléportation annulé !");
				SpawnCommand.waiting.remove(e.getPlayer().getName().toLowerCase());
			}
		}
	}

}
