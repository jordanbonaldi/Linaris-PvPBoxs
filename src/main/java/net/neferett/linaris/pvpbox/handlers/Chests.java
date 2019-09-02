package net.neferett.linaris.pvpbox.handlers;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.neferett.linaris.BukkitAPI;
import net.neferett.linaris.api.PlayerData;
import net.neferett.linaris.utils.TimeUtils;

public class Chests {

	public static int					id			= 0;
	private final HashMap<String, Long>	cooldown	= new HashMap<>();
	private final int					i;
	protected ItemStack[]				items;
	protected Location					loc;

	public Chests(final Location loc, final ItemStack[] items) {
		this.loc = loc;
		this.i = id++;
		this.items = items;
	}

	public int getId() {
		return this.i;
	}

	public ItemStack[] getItems() {
		return this.items;
	}

	public Location getLoc() {
		return this.loc;
	}

	public int getTime(final Player p) {
		final PlayerData data = BukkitAPI.get().getPlayerDataManager().getPlayerData(p.getName().toLowerCase());
		return data.getRank().getVipLevel() > 3 ? 2
				: data.getRank().getVipLevel() > 2 ? 3 : data.getRank().getVipLevel() > 0 ? 4 : 5;
	}

	public String getTimeLeftForPlayer(final Player p) {
		final int minutes = (int) (TimeUtils.getTimeLeft(this.cooldown.get(p.getName().toLowerCase()),
				this.getTime(p) * 60) / 60);
		final int secondes = (int) (TimeUtils.getTimeLeft(this.cooldown.get(p.getName().toLowerCase()),
				this.getTime(p) * 60) % 60);
		return "§7Tu dois attente encore §e" + (minutes != 0 ? minutes + " minute" + (minutes > 1 ? "s " : " ") : " ")
				+ secondes + " seconde" + (secondes > 1 ? "s" : "");
	}

	public boolean isCoolDownOK(final Player p) {
		if (!this.cooldown.containsKey(p.getName().toLowerCase()))
			return true;
		return !TimeUtils.CreateTestCoolDown(this.getTime(p) * 60).test(this.cooldown.get(p.getName().toLowerCase()));
	}

	public void SetCooldown(final Player p) {
		this.cooldown.put(p.getName().toLowerCase(), System.currentTimeMillis());
	}

}
