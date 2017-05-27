package com.voxela.lockpick;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
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
	
	@Override
	public void onEnable() {
		
		instance = this;
		metrics = new Metrics(this);
		
		loadFiles();
		loadMsg();
		checkPlugins();
				
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
	
	public static WorldGuardPlugin getWorldGuard() {
		Plugin plugin = Bukkit.getServer().getPluginManager()
				.getPlugin("WorldGuard");

		if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
			return null;
		}
		return (WorldGuardPlugin) plugin;
	}

	public static WorldEditPlugin getWorldEdit() {
		Plugin plugin = Bukkit.getServer().getPluginManager()
				.getPlugin("WorldEdit");

		if (plugin == null || !(plugin instanceof WorldEditPlugin)) {
			return null;
		}
		return (WorldEditPlugin) plugin;
	}
	
	private void loadFiles() {
		
		getConfig().options().copyDefaults();
		saveDefaultConfig();
		
	}
	
	private void loadMsg() {
		String msg = HttpUtil.requestHttp("http://net.voxela.com/lockpick/msg.html");
		getLogger().info(msg);
	}
	
	private void checkPlugins() {
		
		if (getServer().getPluginManager().getPlugin("TitleAPI") != null) {
			getLogger().info("Found TitleAPI. Using it!");
			titleAPI = true;
		} else {
			getLogger().info("Couldn't find TitleAPI. Not using it.");
		}
		
	}

}
