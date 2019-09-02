package net.neferett.linaris.pvpbox.listeners.events;

import java.util.HashMap;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import net.neferett.linaris.pvpbox.handlers.ConfigReader;
import net.neferett.linaris.pvpbox.listeners.CancelledEvents;
import net.neferett.linaris.utils.TimeUtils;

public class DamageEvent implements Listener {

	public static HashMap<Player, Long> time = new HashMap<>();

	@EventHandler
	public void damage(final EntityDamageByEntityEvent e) {
		if ((e.getDamager() instanceof Player || e.getDamager() instanceof CraftArrow)
				&& e.getEntity() instanceof Player) {
			final CraftArrow arr = e.getDamager() instanceof CraftArrow ? (CraftArrow) e.getDamager() : null;
			final Player p = arr != null && arr.getShooter() instanceof Player ? (Player) arr.getShooter()
					: (Player) e.getDamager();
			if (!ConfigReader.getInstance().getKitOutside()) {
				if (CancelledEvents.cb.isInside(e.getEntity().getLocation()))
					return;
				else {
					if (!time.containsKey(p)
							|| time.containsKey(p) && !TimeUtils.CreateTestCoolDown(15).test(time.get(p)))
						e.getDamager().sendMessage(
								"§cVous entrez en combat, vous devez attendre §e15 secondes§c pour pouvoir vous deconnecter !");
					if (!time.containsKey(e.getEntity()) || time.containsKey(e.getEntity())
							&& !TimeUtils.CreateTestCoolDown(15).test(time.get(e.getEntity())))
						e.getEntity().sendMessage(
								"§cVous entrez en combat, vous devez attendre §e15 secondes§c pour pouvoir vous deconnecter !");
					time.put(p, System.currentTimeMillis());
					time.put((Player) e.getEntity(), System.currentTimeMillis());
				}
			} else {
				if (e.getDamager().getLocation().getY() > ConfigReader.getInstance().getMaxHeight()
						|| e.getEntity().getLocation().getY() > ConfigReader.getInstance().getMaxHeight())
					return;
				if (!time.containsKey(p) || time.containsKey(p) && !TimeUtils.CreateTestCoolDown(15).test(time.get(p)))
					e.getDamager().sendMessage(
							"§cVous entrez en combat, vous devez attendre §e15 secondes§c pour pouvoir vous deconnecter !");
				if (!time.containsKey(e.getEntity()) || time.containsKey(e.getEntity())
						&& !TimeUtils.CreateTestCoolDown(15).test(time.get(e.getEntity())))
					e.getEntity().sendMessage(
							"§cVous entrez en combat, vous devez attendre §e15 secondes§c pour pouvoir vous deconnecter !");
				time.put(p, System.currentTimeMillis());
				time.put((Player) e.getEntity(), System.currentTimeMillis());
			}
		}
	}

	@EventHandler
	public void damageatspawn(final EntityDamageEvent e) {
		if (!ConfigReader.getInstance().getKitOutside())
			if (CancelledEvents.cb.isInside(e.getEntity().getLocation()))
				e.setCancelled(true);
	}
}
