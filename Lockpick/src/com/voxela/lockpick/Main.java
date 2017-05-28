package com.voxela.lockpick;

import org.bukkit.plugin.java.JavaPlugin;

import com.voxela.lockpick.commands.LockpickCommand;
import com.voxela.lockpick.events.RightClickEvent;
import com.voxela.lockpick.items.LockpickItem;
import com.voxela.lockpick.items.MasterKeyItem;
import com.voxela.lockpick.metrics.Metrics;
import com.voxela.lockpick.utils.HttpUtil;
import com.voxela.lockpick.utils.UpdateCheck;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {
	
	private static Main instance;
	private static Metrics metrics;
	
	public static String consolePrefix = "[Lockpick] ";
	public static String gamePrefix = ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "Voxela" + ChatColor.DARK_GRAY + "] ";
	
	public static boolean titleAPI;
	
	public static double version;
	
	@Override
	public void onEnable() {
		
		instance = this;
		metrics = new Metrics(this);
		
		getVersion();
		
		loadFiles();
		loadMsg();				
		this.getCommand("lockpick").setExecutor(new LockpickCommand(this));
        this.getServer().getPluginManager().registerEvents(new RightClickEvent(), getInstance());
        
		LockpickItem.init();
		MasterKeyItem.init();

		System.out.print(consolePrefix + "Lockpick has been enabled.");
		
		UpdateCheck.check();

	}

	public void onDisable() {
		System.out.print(consolePrefix + "Lockpick has been disabled.");
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	public static Metrics getMetrics() {
		return metrics;
	}
	
	private void loadFiles() {
		getConfig().options().copyDefaults();
		saveDefaultConfig();
	}
	
	private void loadMsg() {
		String msg = HttpUtil.requestHttp("http://net.voxela.com/lockpick/msg.html");
		getLogger().info(msg);
	}
	
	private void getVersion() {
		String currentString = getDescription().getVersion();
		double current = Double.parseDouble(currentString.replace("version: ", ""));
		version = current;
	}

}
