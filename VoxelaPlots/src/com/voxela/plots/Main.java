package com.voxela.plots;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.voxela.plots.timeUtils.RentTime;
import com.voxela.plots.utils.FileManager;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin implements Listener {
	
	private static Main instance;
    private static Economy econ = null;


	public static String consolePrefix = "[Voxela] ";
	public static String gamePrefix = ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "Voxela" + ChatColor.DARK_GRAY + "] ";
	
	@Override
	public void onEnable() {
		System.out.print(consolePrefix + "Plots has been enabled.");
		instance = this;
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		FileManager.loadFiles();
		this.getCommand("plot").setExecutor(new PlotCommand(this));
		setupEconomy();
		RentTime.checkEveryTenMins();
	}

	public void onDisable() {
		System.out.print(consolePrefix + "Plots has been disabled.");
		FileManager.unloadFiles();
	}
	
	public static Main getInstance() {
		return instance;
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
	
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
    public static Economy getEconomy() {
        return econ;
    }
	
}
