package net.neferett.linaris.pvpbox.listeners.events;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.neferett.linaris.pvpbox.Main;
import net.neferett.linaris.pvpbox.handlers.ConfigReader;
import net.neferett.linaris.pvpbox.handlers.kits.KitsManager;
import net.neferett.linaris.pvpbox.players.M_Player;
import net.neferett.linaris.pvpbox.players.PlayerManagers;
import net.neferett.linaris.utils.PlayerUtils;

public class DeathEvents implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onFallDamage(final EntityDamageEvent event) {
		if (event.getEntity() instanceof Player && event.getCause() == DamageCause.FALL)
			event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerDeath(final PlayerDeathEvent e) {
		final M_Player p = PlayerManagers.get().getPlayer(e.getEntity());

		e.setDeathMessage("");
		p.addDeath();
		if (e.getEntity() instanceof Player)
			PlayerUtils.sendForceRespawn(e.getEntity(), 1);
		if (!(e.getEntity().getKiller() instanceof Player))
			return;
		final M_Player killer = PlayerManagers.get().getPlayer(e.getEntity().getKiller());

		if (!ConfigReader.getInstance().isDefault())
			PlayerManagers.get().ActionOnPlayers((pl) -> {
				pl.sendMessage("§e" + p.getName() + " §7a été tué par §e" + killer.getName());
			});

		p.setMaxHealth(20);

		if (killer != null) {
			killer.addKill();
			killer.addMoney(150, true);
			killer.delScore();
			killer.getPlayer().setMaxHealth(killer.getPlayer().getMaxHealth() + 1);
			killer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 1));
			killer.getPlayer().setLevel(killer.getPlayer().getLevel() + 2);
			if (killer.CanLevelUp())
				killer.LevelUP();
		}

		if (ConfigReader.getInstance().isDefault()) {
			final List<ItemStack> list = e.getDrops();
			final Iterator<ItemStack> i = list.iterator();
			while (i.hasNext()) {
				final ItemStack item = i.next();
				if (item.getType().equals(Material.GOLD_HELMET) || item.getType().equals(Material.GOLD_BOOTS)
						|| item.getType().equals(Material.CHAINMAIL_CHESTPLATE)
						|| item.getType().equals(Material.CHAINMAIL_LEGGINGS)
						|| item.getType().equals(Material.IRON_SWORD) || item.getType().equals(Material.BOW)
						|| item.getType().equals(Material.ARROW) || item.getType().equals(Material.COOKED_BEEF))
					i.remove();
			}
		}

		DamageEvent.time.remove(p.getPlayer());
		if (p.getMoney() > 0 && p.getMoney() - 5 > 0)
			p.delMoney(5, true);
		p.setBackPos(p.getLocation());

	}

	@EventHandler
	public void onRespawnEvent(final PlayerRespawnEvent e) {
		if (!ConfigReader.getInstance().isDefault())
			return;
		Main.getInstanceMain().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstanceMain(), () -> {
			KitsManager.getInstance().getKits().get(0).giveKits(e.getPlayer());
		}, 20);
	}

}
