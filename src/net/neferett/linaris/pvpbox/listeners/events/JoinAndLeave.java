package net.neferett.linaris.pvpbox.listeners.events;

import java.io.IOException;
import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.line.TextLine;

import net.neferett.linaris.pvpbox.Main;
import net.neferett.linaris.pvpbox.events.players.PlayerActions;
import net.neferett.linaris.pvpbox.events.players.PlayerManagers;
import net.neferett.linaris.pvpbox.handlers.ConfigReader;
import net.neferett.linaris.utils.ScoreboardSign;
import net.neferett.linaris.utils.TimeUtils;
import net.neferett.linaris.utils.tasksmanager.TaskManager;

public class JoinAndLeave extends PlayerManagers implements Listener {

	static String				footer	= "§6Notre site§f: §bhttp://linaris.fr/ §f- §6Notre Twitter§f: §c@LinarisMC \n §6Boutique sur§f: §b§nhttp://linaris.fr/shop/";
	static String				header	= "§eVous êtes connecté sur §aplay.linaris.fr \n §6Rejoignez nous sur TeamSpeak§f: §bts.linaris.fr";
	protected static boolean	spawned	= false;

	Hologram createMagicBoxHolo(final String name, final Location lc) {
		final Hologram holo = new Hologram(name, lc);
		Main.getInstanceMain().getHologramManager().addActiveHologram(holo);
		holo.addLine(new TextLine(holo, "§dBoite Mystere"));
		holo.addLine(new TextLine(holo, "§eCrate §f- §aClique droit pour ouvrir"));
		holo.addLine(new TextLine(holo, "§eClique gauche §7pour regarder les §cGains"));
		return holo;
	}

	@EventHandler
	public void JoinEvent(final PlayerJoinEvent e) throws IOException {
		e.setJoinMessage("");
		this.ActionOnPlayer(new PlayerActions(e.getPlayer()) {

			@Override
			public void Actions() {

				if (!this.getPlayerData().contains("connected-" + ConfigReader.getInstance().getGameName())
						|| !this.rd.isExists()) {
					this.getPlayerData().setBoolean("connected-" + ConfigReader.getInstance().getGameName(), true);
					this.setScore(5);
					this.rd.setKills(0);
					this.rd.setDeaths(0);
					this.rd.setLevel(0);
					this.rd.setMoney(0);
				}
				this.getPlayer().sendMessage("§7Fais /kit pour choisir un §cKIT §7!");
				this.tp(ConfigReader.getInstance().getSpawn());
			}
		});

		if (!spawned) {
			TaskManager.runTaskLater(() -> {
				this.createMagicBoxHolo("loc1", ConfigReader.getInstance().getLocation("config.holo1"));
				this.createMagicBoxHolo("loc2", ConfigReader.getInstance().getLocation("config.holo2"));
				this.createMagicBoxHolo("loc2", ConfigReader.getInstance().getLocation("config.holo3"));
			}, 20);
			spawned = true;
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	public void onPlayerLeft(final PlayerQuitEvent event) {
		if (DamageEvent.time.containsKey(event.getPlayer())
				&& TimeUtils.CreateTestCoolDown(15).test(DamageEvent.time.get(event.getPlayer()))) {
			event.getPlayer().getInventory().forEach(item -> {
				if (item != null && item.getType() != null && !item.getType().equals(org.bukkit.Material.AIR)
						&& item.getTypeId() != 397)
					event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), item);
			});
			Arrays.asList(event.getPlayer().getInventory().getArmorContents()).forEach(item -> {
				if (item != null && item.getType() != null && !item.getType().equals(org.bukkit.Material.AIR))
					event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), item);
			});
			event.getPlayer().getInventory().setBoots(null);
			event.getPlayer().getInventory().setHelmet(null);
			event.getPlayer().getInventory().setChestplate(null);
			event.getPlayer().getInventory().setLeggings(null);
			event.getPlayer().getInventory().clear();
		}
		final ScoreboardSign bar = ScoreboardSign.get(event.getPlayer());
		if (bar != null)
			bar.destroy();
		event.setQuitMessage("");
	}

}
