package com.voxela.lockpick;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.voxela.lockpick.commands.LockpickCommand;
import com.voxela.lockpick.events.RightClickEvent;
import com.voxela.lockpick.items.LockpickItem;
import com.voxela.lockpick.metrics.Metrics;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {
	
	private static Main instance;
	private static Metrics metrics;
	public static String consolePrefix = "[Voxela] ";
	public static String gamePrefix = ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "Voxela" + ChatColor.DARK_GRAY + "] ";
	
	@Override
	public void onEnable() {
		
		instance = this;
		metrics = new Metrics(this);
		
		System.out.print(consolePrefix + "Lockpick has been enabled.");
		
		this.getCommand("lockpick").setExecutor(new LockpickCommand(this));
        this.getServer().getPluginManager().registerEvents(new RightClickEvent(), getInstance());
		LockpickItem.init();
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

}
