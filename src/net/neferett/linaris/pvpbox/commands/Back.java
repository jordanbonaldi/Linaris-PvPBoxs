package net.neferett.linaris.pvpbox.commands;

import java.util.List;

import net.neferett.linaris.PlayersHandler.Players;
import net.neferett.linaris.commands.CommandHandler;
import net.neferett.linaris.pvpbox.handlers.ConfigReader;
import net.neferett.linaris.pvpbox.listeners.CancelledEvents;
import net.neferett.linaris.pvpbox.players.M_Player;
import net.neferett.linaris.pvpbox.players.PlayerManagers;

public class Back extends CommandHandler {

	public Back() {
		super("back", p -> p.getRank().getVipLevel() > 3);
	}

	@Override
	public void cmd(final Players arg0, final String arg1, final List<String> arg2) {
		final M_Player p = PlayerManagers.get().getPlayer(arg0.getPlayer());
		if (!ConfigReader.getInstance().getKitOutside() && CancelledEvents.cb.isInside(arg0.getLocation())
				|| ConfigReader.getInstance().getKitOutside())
			arg0.createTPWithDelay(5, () -> p.getBackPos());
		else
			arg0.sendMessage("§cIl faut etre au spawn !");

	}

	@Override
	public void onError(final Players arg0) {
		arg0.sendMessage("§cVous ne pouvez pas executer cette commande !");
	}

}
