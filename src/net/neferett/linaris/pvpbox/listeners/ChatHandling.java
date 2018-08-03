package net.neferett.linaris.pvpbox.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.neferett.linaris.BukkitAPI;
import net.neferett.linaris.api.PlayerData;
import net.neferett.linaris.pvpbox.players.PlayerManagers;

public class ChatHandling extends PlayerManagers implements Listener {

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerChat(final AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();

		event.setCancelled(true);
		final PlayerData pd = BukkitAPI.get().getPlayerDataManager().getPlayerData(player.getName());
		if (pd.getRank().getModerationLevel() < 1) this.ActionOnPlayers((p) -> {
			p.sendMessage("§" + pd.getRank().getColor() + "Lvl" + this.getPlayer(player).getLevel() + " "
					+ pd.getRank().getPrefix(pd) + player.getName() + "§" + pd.getRank().getColor() + " : "
					+ event.getMessage().trim());
		});
		else this.ActionOnPlayers((p) -> {
			p.sendMessage("§" + pd.getRank().getColor() + "Lvl" + this.getPlayer(player).getLevel() + " "
					+ pd.getRank().getPrefix(pd) + player.getName() + "§" + pd.getRank().getColor() + " : "
					+ event.getMessage().trim().replace("&", "§"));
		});

	}

}
