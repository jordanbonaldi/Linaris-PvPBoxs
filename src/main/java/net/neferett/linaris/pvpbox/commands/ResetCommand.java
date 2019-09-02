package net.neferett.linaris.pvpbox.commands;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import net.neferett.linaris.BukkitAPI;
import net.neferett.linaris.PlayersHandler.Players;
import net.neferett.linaris.api.PlayerData;
import net.neferett.linaris.commands.CommandHandler;
import net.neferett.linaris.pvpbox.handlers.ConfigReader;

public class ResetCommand extends CommandHandler {

	static String	_death	= ConfigReader.getInstance().getGameName() + "_deaths";
	static String	_kills	= ConfigReader.getInstance().getGameName() + "_kills";
	static String	_level	= ConfigReader.getInstance().getGameName() + "_level";
	static String	_money	= ConfigReader.getInstance().getGameName() + "_money";
	static String	_score	= ConfigReader.getInstance().getGameName() + "_score";

	public ResetCommand() {
		super("reset", p -> p.getRank().getModerationLevel() >= 4);
	}

	@Override
	public void cmd(final Players arg0, final String arg1, final List<String> arg2) {
		final File folder = new File("plugins/PvPBox/data/");

		Arrays.asList(folder.listFiles()).forEach(p -> {
			final String player = p.getName().split(Pattern.quote("."))[0];
			System.out.println("Reseting " + player);
			final PlayerData pd = BukkitAPI.get().getPlayerDataManager().getPlayerData(player);
			pd.setInt(_death, 0);
			pd.setInt(_kills, 0);
			pd.setInt(_level, 0);
			pd.setInt(_money, 0);
			pd.setInt(_score, 0);
		});
	}

	@Override
	public void onError(final Players arg0) {
		arg0.sendMessage("§cIl faut être §cAdmin!");
	}

}