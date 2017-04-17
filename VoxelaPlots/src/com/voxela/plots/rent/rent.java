package com.voxela.plots.rent;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.plots.Main;
import com.voxela.plots.timeUtils.TimeManager;
import com.voxela.plots.utils.ChatUtils;
import com.voxela.plots.utils.FileManager;

import net.md_5.bungee.api.ChatColor;

public class rent {
		
	@SuppressWarnings("deprecation")
	public static void rentMethod(Player player, ProtectedRegion region) {
		
		if (!(FileManager.dataFileCfg.isSet("regions." + region.getId()))) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "This property is not for sale.");
			return;
		}
		
		// If region is owned...
		if ((region.getOwners().getUniqueIds().toString().contains("-") )) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "This plot is already owned!");
			return;
		}
		
		int plotPrice = FileManager.dataFileCfg.getInt("regions." + region.getId() + ".price");
		
		if (Main.getEconomy().getBalance(player.getName()) >= plotPrice) {
			
			Main.getEconomy().withdrawPlayer(player, plotPrice);
			
			FileManager.dataFileCfg.set("regions." + region.getId() + ".renter", player.getName());
			FileManager.dataFileCfg.set("regions." + region.getId() + ".rentuntil", TimeManager.timePlusWeek());
			FileManager.saveDataFile();
			
			UUID playerUUID = ChatUtils.toUUID(player.getName());
			
			DefaultDomain domain = region.getOwners();
			domain.addPlayer(playerUUID);
			region.setOwners(domain);
			
			region.setFlag(DefaultFlag.GREET_MESSAGE, Main.gamePrefix + ChatColor.GREEN + "Entering " + ChatColor.GOLD + region.getId() + "." + ChatColor.GREEN + " Owned by " + ChatColor.GOLD + player.getName() + ".");
			region.setFlag(DefaultFlag.FAREWELL_MESSAGE, Main.gamePrefix + ChatColor.GREEN + "Leaving " + ChatColor.GOLD + region.getId() + "." + ChatColor.GREEN + " Owned by " + ChatColor.GOLD + player.getName() + ".");			
			
			player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "You are now renting " + ChatColor.GOLD + region.getId() + ChatColor.GREEN + " for " + ChatColor.GOLD + "$" + plotPrice + ChatColor.GREEN + " per week!");
			player.sendMessage(Main.gamePrefix + ChatColor.GRAY + "Make sure you have enough money in your balance to afford the rent on " + ChatColor.DARK_AQUA + 
					TimeManager.timeFormatter(TimeManager.timePlusWeek()) + ChatColor.GRAY + " or it will be reset! Current balance is " + ChatColor.DARK_AQUA + "$" + 
					Main.getEconomy().getBalance(player.getName()) + ".");
		} else {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "You can't afford this plot!");
		}
		
	}

}
