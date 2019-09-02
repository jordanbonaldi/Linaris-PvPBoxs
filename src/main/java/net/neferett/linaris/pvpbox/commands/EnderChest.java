package net.neferett.linaris.pvpbox.commands;

import java.util.List;

import net.neferett.linaris.PlayersHandler.Players;
import net.neferett.linaris.commands.CommandHandler;
import net.neferett.linaris.pvpbox.handlers.ConfigReader;
import net.neferett.linaris.pvpbox.listeners.CancelledEvents;

public class EnderChest extends CommandHandler {

	public EnderChest() {
		super("ec", p -> p.getRank().getVipLevel() > 3, "enderchest");
	}

	@Override
	public void cmd(final Players arg0, final String arg1, final List<String> arg2) {
		if (!ConfigReader.getInstance().getKitOutside() && CancelledEvents.cb.isInside(arg0.getPlayer().getLocation())
				|| ConfigReader.getInstance().getKitOutside())
			arg0.getPlayer().openInventory(arg0.getPlayer().getEnderChest());
		else
			arg0.getPlayer().sendMessage("§cIl faut etre au spawn !");
	}

	@Override
	public void onError(final Players arg0) {
		arg0.sendMessage("§cVous ne pouvez pas faire cela !");
	}

}
