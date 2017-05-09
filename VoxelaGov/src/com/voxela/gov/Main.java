package com.voxela.gov;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.voxela.gov.commands.RunCommand;
import com.voxela.gov.commands.VoteCommand;
import com.voxela.gov.commands.government.GovernmentCommand;
import com.voxela.gov.commands.primeminister.PrimeMinisterCommand;
import com.voxela.gov.events.PlayerCommandEvent;
import com.voxela.gov.events.PlayerLogin;
import com.voxela.gov.events.ProvinceEnter;
import com.voxela.gov.events.war.WarEvents;
import com.voxela.gov.listeners.InventoryClickListener;
import com.voxela.gov.utils.FileManager;
import com.voxela.gov.utils.TimeManager.Taxation;
import com.voxela.gov.utils.TimeManager.WarTime;
import com.voxela.gov.utils.TimeManager.PrimeMinister.MainCheck;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin implements Listener {
	
	private static Main instance;
    private static Economy econ = null;

	public static String consolePrefix = "[VoxelaGov] ";
	public static String gamePrefix = ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "Voxela" + ChatColor.DARK_GRAY + "] ";
	
	@Override
	public void onEnable() {
		System.out.print(consolePrefix + "Gov has been enabled.");
		instance = this;
		
		FileManager.loadFiles();
		
		this.getCommand("gov").setExecutor(new GovernmentCommand(this));
		this.getCommand("pm").setExecutor(new PrimeMinisterCommand(this));
		this.getCommand("run").setExecutor(new RunCommand(this));
		this.getCommand("vote").setExecutor(new VoteCommand(this));

		this.getServer().getPluginManager().registerEvents(new ProvinceEnter(), getInstance());
		this.getServer().getPluginManager().registerEvents(new WarEvents(), getInstance());
		this.getServer().getPluginManager().registerEvents(new PlayerLogin(), getInstance());
        this.getServer().getPluginManager().registerEvents(new InventoryClickListener(), getInstance());
        this.getServer().getPluginManager().registerEvents(new PlayerCommandEvent(), getInstance());
		
		setupEconomy();
		
		MainCheck.check();
		WarTime.warCheck();
		Taxation.checkTaxes();
	}

	public void onDisable() {
		System.out.print(consolePrefix + "Gov has been disabled.");
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
