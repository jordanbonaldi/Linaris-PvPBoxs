package net.neferett.linaris.pvpbox.commands;

import java.util.List;

import net.neferett.linaris.PlayersHandler.Players;
import net.neferett.linaris.commands.CommandHandler;
import net.neferett.linaris.pvpbox.handlers.ConfigReader;
import net.neferett.linaris.pvpbox.handlers.kits.GuiKits;
import net.neferett.linaris.pvpbox.listeners.CancelledEvents;
import net.neferett.linaris.pvpbox.utils.GuiManager;

public class KitsCommand extends CommandHandler {

	public KitsCommand() {
		super("kit", p -> p != null);
	}

	@Override
	public void cmd(final Players arg0, final String arg1, final List<String> arg2) {

		if (!ConfigReader.getInstance().getKitOutside() && CancelledEvents.cb.isInside(arg0.getLocation())
				|| ConfigReader.getInstance().getKitOutside())
			GuiManager.openGui(new GuiKits(arg0.getPlayer()));
		else
			arg0.sendMessage("§cIl faut etre au spawn !");
	}

	@Override
	public void onError(final Players arg0) {
		// TODO Auto-generated method stub

	}

}
