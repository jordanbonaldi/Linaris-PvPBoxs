package net.neferett.linaris.pvpbox;

import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.sainttx.holograms.api.HologramManager;
import com.sainttx.holograms.api.HologramPlugin;

import net.neferett.linaris.BukkitAPI;
import net.neferett.linaris.api.API;
import net.neferett.linaris.pvpbox.commands.Back;
import net.neferett.linaris.pvpbox.commands.CraftCommand;
import net.neferett.linaris.pvpbox.commands.EnderChest;
import net.neferett.linaris.pvpbox.commands.Feed;
import net.neferett.linaris.pvpbox.commands.GiveKits;
import net.neferett.linaris.pvpbox.commands.KitsCommand;
import net.neferett.linaris.pvpbox.commands.LevelCommand;
import net.neferett.linaris.pvpbox.commands.MoneyManagement;
import net.neferett.linaris.pvpbox.commands.ResetCommand;
import net.neferett.linaris.pvpbox.commands.SpawnCommand;
import net.neferett.linaris.pvpbox.commands.TPA.TPA;
import net.neferett.linaris.pvpbox.commands.TPA.TPAccept;
import net.neferett.linaris.pvpbox.commands.TPA.TPAdeny;
import net.neferett.linaris.pvpbox.handlers.ConfigReader;
import net.neferett.linaris.pvpbox.handlers.ScoreBoard;
import net.neferett.linaris.pvpbox.handlers.kits.KitsManager;
import net.neferett.linaris.pvpbox.listeners.CancelledEvents;
import net.neferett.linaris.pvpbox.listeners.ChatHandling;
import net.neferett.linaris.pvpbox.listeners.ChestListener;
import net.neferett.linaris.pvpbox.listeners.events.AntiCrop;
import net.neferett.linaris.pvpbox.listeners.events.AnvilBreak;
import net.neferett.linaris.pvpbox.listeners.events.AutoLapis;
import net.neferett.linaris.pvpbox.listeners.events.DamageEvent;
import net.neferett.linaris.pvpbox.listeners.events.DeathEvents;
import net.neferett.linaris.pvpbox.listeners.events.InteractListener;
import net.neferett.linaris.pvpbox.listeners.events.JoinAndLeave;
import net.neferett.linaris.pvpbox.listeners.events.MoveListener;
import net.neferett.linaris.pvpbox.listeners.events.SignListener;
import net.neferett.linaris.pvpbox.players.PlayerManagers;
import net.neferett.linaris.utils.TimeUtils;

public class Main extends API {

	static Main instanceMain;

	public static Main getInstanceMain() {
		return instanceMain;
	}

	protected HologramManager	hologramManager;

	PlayerManagers				pm;

	public Main() {
		super(ConfigReader.getInstance().getGameName(), "Default", 100);
		instanceMain = this;
	}

	@Override
	public void addRanks() {}

	public HologramManager getHologramManager() {
		return this.hologramManager;
	}

	public PlayerManagers getPm() {
		return this.pm;
	}

	private void loadPredicateProcessors() {
		BukkitAPI.get().addProcessPredicate(e -> {
			final Player p = e.getPlayer();
			if (DamageEvent.time.containsKey(p) && TimeUtils.CreateTestCoolDown(15).test(DamageEvent.time.get(p))) {
				p.sendMessage("§cVous êtes en combat vous ne pouvez pas faire cela !");
				e.setMessage("");
				e.setCancelled(true);
				return false;
			}
			return true;
		});
	}

	@Override
	public void onClose() {
		this.closeServer();
	}

	@Override
	public void onLoading() {
		new KitsManager();
		ConfigReader.getInstance().getKits();
	}

	@Override
	public void onOpen() {
		this.pm = new PlayerManagers();
		this.hologramManager = JavaPlugin.getPlugin(HologramPlugin.class).getHologramManager();

		this.openServer();
		this.handleWorld();

		this.w.getEntities().forEach(e -> {
			if (!(e instanceof ItemFrame))
				e.remove();
		});

		this.loadPredicateProcessors();

		this.setScoreBoard(ScoreBoard.class);

		if (ConfigReader.getInstance().getChectClickable())
			this.RegisterAllEvents(new ChestListener());
		this.RegisterAllEvents(new JoinAndLeave(), new CancelledEvents(), new ChatHandling(), new InteractListener(),
				new DeathEvents(), new MoveListener(), new AutoLapis(), new DamageEvent(), new AnvilBreak(),
				new AntiCrop(), new SignListener());
		this.setAPIMode(true);
		// TaskManager.scheduleSyncRepeatingTask("Classement", () -> {
		// if (Bukkit.getOnlinePlayers().size() >= 1)
		// Classement.getInstance().SignClassement();
		// }, 0, 6 * 20);
		this.setAnnounce();
		this.addHealthNameTag();
	}

	@Override
	public void RegisterCommands() {
		new Feed();
		new TPA();
		new TPAccept();
		new TPAdeny();
		new SpawnCommand();
		if (!ConfigReader.getInstance().isDefault()) {
			new KitsCommand();
			this.getCommand("givekits").setExecutor(new GiveKits());
			new Back();
		}
		this.getCommand("money").setExecutor(new MoneyManagement());
		this.getCommand("level").setExecutor(new LevelCommand());
		new ResetCommand();
		new EnderChest();
		this.getCommand("craft").setExecutor(new CraftCommand());
		// this.getCommand("pay").setExecutor(new PayCommand());
		this.getCommand("classement").setExecutor(new net.neferett.linaris.pvpbox.commands.Classement());
	}

}
