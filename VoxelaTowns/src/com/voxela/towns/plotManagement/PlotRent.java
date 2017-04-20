package com.voxela.towns.plotManagement;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.towns.Main;
import com.voxela.towns.timeUtils.TimeManager;
import com.voxela.towns.utils.ChatUtils;
import com.voxela.towns.utils.FileManager;
import com.voxela.towns.utils.TownRegionConvert;

import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("deprecation")
public class PlotRent {
	
	public static void rentMethod(Player player, String town, ProtectedRegion townRegion, ProtectedRegion plotRegion) {
				
		if (!(FileManager.dataFileCfg.isSet("regions." + townRegion.getId() + ".plots." + plotRegion.getId()))) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "This property is not for sale.");
			return;
		}
		
		// If region is owned...
		if ((plotRegion.getOwners().getUniqueIds().toString().contains("-") )) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "This plot is already owned!");
			return;
		}
		
		int plotPrice = FileManager.dataFileCfg.getInt("regions." + townRegion.getId() + ".plots." + plotRegion.getId() + ".price");
		
		if (Main.getEconomy().getBalance(player.getName()) >= plotPrice) {
			
			OfflinePlayer account = Bukkit.getOfflinePlayer("town-" + town);
			
			Main.getEconomy().withdrawPlayer(player, plotPrice);
			Main.getEconomy().depositPlayer(account, plotPrice);
			
			FileManager.dataFileCfg.set("regions." + townRegion.getId() + ".plots." + plotRegion.getId() + ".renter", player.getName());
			FileManager.dataFileCfg.set("regions." + townRegion.getId() + ".plots." + plotRegion.getId() + ".rentuntil", TimeManager.timePlusWeek());
			FileManager.saveDataFile();
			
			UUID playerUUID = ChatUtils.toUUID(player.getName());
			
			DefaultDomain domain = plotRegion.getOwners();
			domain.addPlayer(playerUUID);
			plotRegion.setOwners(domain);
			
			plotRegion.setFlag(DefaultFlag.GREET_MESSAGE, Main.gamePrefix + ChatColor.GOLD + town + ":" + ChatColor.GREEN + " Entering " + 
					ChatColor.GOLD + plotRegion.getId() + ChatColor.GREEN + " owned by " + ChatColor.GOLD + player.getName() + ".");
			plotRegion.setFlag(DefaultFlag.FAREWELL_MESSAGE, Main.gamePrefix + ChatColor.GOLD + town + ":" + ChatColor.GREEN + " Leaving " + 
							ChatColor.GOLD + plotRegion.getId() + ChatColor.GREEN + " owned by " + ChatColor.GOLD + player.getName() + ".");	
			
			player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "You are now renting " + ChatColor.GOLD + plotRegion.getId() + ChatColor.GREEN + " for " + ChatColor.GOLD + "$" + plotPrice + ChatColor.GREEN + " per week!");
			
		} else {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "You can't afford this plot!");
		}
		
	}
	
	public static void playerUnrentMethod(ProtectedRegion townRegion, ProtectedRegion plotRegion) {
		
		FileManager.dataFileCfg.set("regions." + townRegion.getId() + ".plots." + plotRegion.getId() + ".renter", "unowned");
		FileManager.dataFileCfg.set("regions." + townRegion.getId() + ".plots." + plotRegion.getId() + ".rentuntil", null);
		FileManager.saveDataFile();
		
		String town = TownRegionConvert.regionToTown(townRegion);
		
		plotRegion.setFlag(DefaultFlag.GREET_MESSAGE, Main.gamePrefix + ChatColor.GOLD + town + ":" + ChatColor.GREEN + " Entering unowned plot: " + 
		ChatColor.GOLD + plotRegion.getId() + ".");
		plotRegion.setFlag(DefaultFlag.FAREWELL_MESSAGE, Main.gamePrefix + ChatColor.GOLD + town + ":" + ChatColor.GREEN + " Leaving unowned plot: " + 
				ChatColor.GOLD + plotRegion.getId() + ".");

		plotRegion.getOwners().clear();
		plotRegion.getMembers().clear();
		
	}
}