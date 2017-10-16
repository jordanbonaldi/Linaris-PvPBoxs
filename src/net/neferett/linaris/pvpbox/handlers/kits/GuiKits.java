package net.neferett.linaris.pvpbox.handlers.kits;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.neferett.linaris.api.Rank;
import net.neferett.linaris.pvpbox.events.players.PlayerActions;
import net.neferett.linaris.pvpbox.events.players.PlayerManagers;
import net.neferett.linaris.pvpbox.handlers.ConfigReader;
import net.neferett.linaris.pvpbox.utils.GuiManager;
import net.neferett.linaris.pvpbox.utils.GuiScreen;
import net.neferett.linaris.utils.TimeUtils;

public class GuiKits extends GuiScreen {

	public class CoolDown {
		private final String	kitname;
		private final long		time;

		public CoolDown(final String kitname, final long time) {
			this.time = time;
			this.kitname = kitname;
		}

		public String getKitname() {
			return this.kitname;
		}

		public long getTime() {
			return this.time;
		}
	}

	public static HashMap<String, CoolDown> cool = new HashMap<>();

	public GuiKits(final Player p) {
		super("Choisi un Kit", ConfigReader.getInstance().getKitSlots(), p, false);
		this.build();
	}

	@Override
	public void drawScreen() {
		KitsManager.getInstance().getKits().forEach((kit) -> {
			this.setItem(kit.getKitdisp(), kit.getSlot());
		});
	}

	@Override
	public void onClick(final ItemStack item, final InventoryClickEvent e) {
		e.setCancelled(true);
		if (item.getType() != null && e.isRightClick())
			GuiManager.openGui(new GuiShowKits(item.getItemMeta().getDisplayName(), (Player) e.getWhoClicked(), this));
		else if (item.getType() != null && e.isLeftClick())
			new PlayerManagers().ActionOnPlayer(new PlayerActions((Player) e.getWhoClicked()) {

				@Override
				public void Actions() {
					final Kits kit = KitsManager.getInstance().getKitByName(item.getItemMeta().getDisplayName());

					if (cool.containsKey(this.p.getName().toLowerCase() + kit.getName())
							&& TimeUtils.CreateTestCoolDown(kit.getCooldown())
									.test(cool.get(this.p.getName().toLowerCase() + kit.getName()).getTime())) {
						final int minutes = (int) (TimeUtils.getTimeLeft(
								cool.get(this.p.getName().toLowerCase() + kit.getName()).getTime(), kit.getCooldown())
								/ 60);
						final int secondes = (int) (TimeUtils.getTimeLeft(
								cool.get(this.p.getName().toLowerCase() + kit.getName()).getTime(), kit.getCooldown())
								% 60);
						this.getPlayer()
								.sendMessage("§7Tu dois attente encore §e"
										+ (minutes != 0 ? minutes + " minute" + (minutes > 1 ? "s " : " ") : " ")
										+ secondes + " seconde" + (secondes > 1 ? "s" : ""));
						this.getPlayer().closeInventory();
						return;
					} else if (!kit.getRank().equals("none")
							&& Rank.get(kit.getRank()).getVipLevel() > this.getRank().getVipLevel()) {
						this.getPlayer().sendMessage(
								"§cVous devez être §a" + kit.getRank() + "§c pour pouvoir acceder a ce kit !");
						this.getPlayer().closeInventory();
						return;
					} else if (this.getMoney() < kit.getPrice()) {
						this.getPlayer().sendMessage("§cIl vous manque §e" + (kit.getPrice() - this.getMoney())
								+ "$ §cpour acheter ce kit !");
						this.getPlayer().closeInventory();
						return;
					} else if (this.getMoney() >= kit.getPrice()) {
						this.delMoney(kit.getPrice());
						cool.put(this.p.getName().toLowerCase() + kit.getName(),
								new CoolDown(kit.getName(), System.currentTimeMillis()));
						this.getPlayer().sendMessage("§aVous venez d'acheter le kit §b" + kit.getName());
						kit.giveKits(this.getPlayer());
						this.getPlayer().closeInventory();
					}

				}
			});
	}

	@Override
	public void onClose() {}

	@Override
	public void onOpen() {}

}
