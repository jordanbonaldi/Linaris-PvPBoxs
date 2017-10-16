package net.neferett.linaris.pvpbox.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import net.neferett.linaris.pvpbox.handlers.ConfigReader;
import net.neferett.linaris.pvpbox.utils.CuboidRegion;

public class CancelledEvents implements Listener{
	
	public static CuboidRegion cb = new CuboidRegion(ConfigReader.getInstance().getPos1(), ConfigReader.getInstance().getPos2());
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) { e.setCancelled(true); }
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) { if (!e.getPlayer().isOp()) e.setCancelled(true); }
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) { if (!e.getPlayer().isOp()) e.setCancelled(true); }
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e) { e.setCancelled(true); }
	
	@EventHandler
	public void onDamageBelowMap(EntityDamageByEntityEvent e) {
		if (!ConfigReader.getInstance().checkInside()){
			if (e.getEntity() instanceof Player && ((Player)e.getEntity()).getLocation().getY() > ConfigReader.getInstance().getMaxHeight())
				e.setCancelled(true);
		} else {
			if (e.getEntity() instanceof Player && cb.isInside(e.getEntity().getLocation()))
				e.setCancelled(true);
		}
	}
}
