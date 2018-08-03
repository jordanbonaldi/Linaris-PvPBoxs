package net.neferett.linaris.pvpbox.listeners.events;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.neferett.linaris.pvpbox.handlers.kits.KitsManager;
import net.neferett.linaris.pvpbox.players.M_Player;
import net.neferett.linaris.pvpbox.players.PlayerManagers;

public class SignListener implements Listener {

	static boolean					loaded		= false;

	HashMap<String, Enchantment>	enchants	= new HashMap<>();
	HashMap<String, Material>		materials	= new HashMap<>();

	public void ApplyBuy(final ItemStack item, final int amount, final int price, final M_Player p) {
		if (p.getMoney() < price) {
			p.sendMessage("§cVous n'avez pas assez d'argent !");
			return;
		}

		p.delMoney(price, false);

		for (int i = 0; i < amount; i++)
			p.getInventory().addItem(item);

		p.sendMessage("§aVous venez de recevoir votre item !");
	}

	public void ApplyEnchant(final ItemStack item, final Enchantment e, final int lvl, final int price,
			final M_Player p) {
		if (p.getMoney() < price) {
			p.sendMessage("§cVous n'avez pas assez d'argent !");
			return;
		}

		p.delMoney(price, false);

		if (lvl > 0)
			item.addUnsafeEnchantment(e, lvl);
		else
			item.removeEnchantment(e);

		p.sendMessage("§aVous venez d'enchanter votre objet !");
	}

	public void load() {
		loaded = true;
		this.enchants.put("unbreaking", Enchantment.DURABILITY);
		this.enchants.put("knockback", Enchantment.KNOCKBACK);
		this.enchants.put("infinity", Enchantment.ARROW_INFINITE);
		this.enchants.put("flame", Enchantment.ARROW_FIRE);
		this.enchants.put("punch", Enchantment.ARROW_KNOCKBACK);
		this.enchants.put("power", Enchantment.ARROW_DAMAGE);
		this.enchants.put("protection", Enchantment.PROTECTION_ENVIRONMENTAL);
		this.enchants.put("sharpness", Enchantment.DAMAGE_ALL);
		this.enchants.put("fireaspect", Enchantment.FIRE_ASPECT);

		this.materials.put("bow", Material.BOW);
		this.materials.put("arrow", Material.ARROW);
		this.materials.put("diamond", Material.DIAMOND);
		this.materials.put("ironingot", Material.IRON_INGOT);
		this.materials.put("fishingrod", Material.FISHING_ROD);
		this.materials.put("goldenapple", Material.GOLDEN_APPLE);
		this.materials.put("steak", Material.COOKED_BEEF);

		this.materials.put("diamondsword", Material.DIAMOND_SWORD);
		this.materials.put("diamondhelm", Material.DIAMOND_HELMET);
		this.materials.put("diamondhelmet", Material.DIAMOND_HELMET);
		this.materials.put("diamondplate", Material.DIAMOND_CHESTPLATE);
		this.materials.put("diamondleggings", Material.DIAMOND_LEGGINGS);
		this.materials.put("diamondboots", Material.DIAMOND_BOOTS);

		this.materials.put("ironsword", Material.IRON_SWORD);
		this.materials.put("ironhelm", Material.IRON_HELMET);
		this.materials.put("ironhelmet", Material.IRON_HELMET);
		this.materials.put("ironchestplate", Material.IRON_CHESTPLATE);
		this.materials.put("ironleggings", Material.IRON_LEGGINGS);
		this.materials.put("ironboots", Material.IRON_BOOTS);

		this.materials.put("expbottle", Material.EXP_BOTTLE);

	}

	@EventHandler
	public void onSignClick(final PlayerInteractEvent e) {
		if (e.getClickedBlock() == null || e.getClickedBlock().getState() == null)
			return;
		if (!(e.getClickedBlock().getState() instanceof Sign))
			return;
		final M_Player p = PlayerManagers.get().getPlayer(e.getPlayer());
		final ItemStack item = e.getPlayer().getInventory().getItemInHand();
		final Sign s = (Sign) e.getClickedBlock().getState();

		if (s == null)
			return;

		if (!loaded)
			this.load();

		if (s.getLine(0).contains("Kit")) {
			if (e.getPlayer().getInventory().contains(Material.DIAMOND_SWORD)
					|| e.getPlayer().getInventory().contains(Material.IRON_SWORD)
					|| e.getPlayer().getInventory().contains(Material.GOLDEN_APPLE)
					|| e.getPlayer().getInventory().contains(Material.COOKED_BEEF)
					|| e.getPlayer().getInventory().contains(Material.ARROW))
				e.getPlayer().sendMessage("§cVous avez déjà les items nécéssaire !");
			else
				KitsManager.getInstance().getKits().get(0).giveKits(e.getPlayer());
		} else if (s.getLine(0).contains("Enchant")) {
			final String[] a = s.getLine(2).split(":");
			final Enchantment ec = this.enchants.get(a[0].toLowerCase());
			final int lvl = Integer.parseInt(a[1]);
			final int price = Integer.parseInt(s.getLine(3));
			if (s.getLine(1).contains("Tous"))
				this.ApplyEnchant(item, ec, lvl, price, p);
			else {
				final ItemStack it = new ItemStack(this.materials.get(s.getLine(1).toLowerCase()));

				if (item.getType().equals(it.getType()))
					this.ApplyEnchant(item, ec, lvl, price, p);
				else
					p.sendMessage("§cVous n'avez pas le bon item en main !");
			}
		} else if (s.getLine(0).contains("Buy"))
			this.ApplyBuy(new ItemStack(this.materials.get(s.getLine(2).toLowerCase())), Integer.parseInt(s.getLine(1)),
					Integer.parseInt(s.getLine(3)), p);
		else if (s.getLine(0).contains("Poubelle"))
			p.openInventory(Bukkit.createInventory(null, 4 * 9));
	}

}
