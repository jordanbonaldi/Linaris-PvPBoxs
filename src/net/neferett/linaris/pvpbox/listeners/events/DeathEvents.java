package net.neferett.linaris.pvpbox.listeners.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.neferett.linaris.pvpbox.events.players.PlayerActions;
import net.neferett.linaris.pvpbox.events.players.PlayerManagers;
import net.neferett.linaris.pvpbox.handlers.ConfigReader;
import net.neferett.linaris.utils.PlayerUtils;

public class DeathEvents extends PlayerManagers implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onFallDamage(final EntityDamageEvent event) {
		if (event.getEntity() instanceof Player && event.getCause() == DamageCause.FALL)
			event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerDeath(final PlayerDeathEvent e) {
		final Player p = e.getEntity();
		final Player killer = p.getKiller();

		e.setDeathMessage("");
		this.getPlayer(p).addDeath();
		if (e.getEntity() instanceof Player)
			PlayerUtils.sendForceRespawn(e.getEntity(), 1);
		if (!(e.getEntity().getKiller() instanceof Player))
			return;
		this.ActionOnPlayers((pl) -> {
			pl.sendMessage("§e" + p.getName() + " §7a été tué par §e" + killer.getName());
		});
		this.ActionOnPlayer(new PlayerActions(killer) {

			@Override
			public void Actions() {
				this.addKill();
				this.addMoney(10);
				this.delScore();
				if (this.CanLevelUp())
					this.LevelUP();
				if (ConfigReader.getInstance().isMoney())
					PlayerUtils.sendActionMessage(this.getPlayer(), "§6§l+ 10$");
			}
		});
		this.ActionOnPlayer(new PlayerActions(p) {

			@Override
			public void Actions() {
				DamageEvent.time.remove(this.getPlayer());
				if (this.getMoney() > 0 && this.getMoney() - 5 > 0)
					this.delMoney(5);
				this.setBackPos(this.getPlayer().getLocation());
				if (ConfigReader.getInstance().isMoney())
					PlayerUtils.sendActionMessage(this.getPlayer(), "§c§l- 5$");
			}
		});
	}

}
