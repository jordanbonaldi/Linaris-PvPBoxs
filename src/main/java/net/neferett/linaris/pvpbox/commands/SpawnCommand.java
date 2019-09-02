package net.neferett.linaris.pvpbox.commands;

import java.util.List;

import net.neferett.linaris.PlayersHandler.Players;
import net.neferett.linaris.commands.CommandHandler;
import net.neferett.linaris.pvpbox.handlers.ConfigReader;

public class SpawnCommand extends CommandHandler {

	public SpawnCommand() {
		super("spawn", p -> p != null);
	}

	@Override
	public void cmd(final Players arg0, final String arg1, final List<String> arg2) {
		arg0.sendMessage("§cTéléportation dans environ 5 secondes, ne bougez pas !");
		arg0.createTPWithDelay(5, () -> ConfigReader.getInstance().getSpawn());
	}

	@Override
	public void onError(final Players arg0) {
		// TODO Auto-generated method stub

	}

}
