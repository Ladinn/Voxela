package com.voxela.towns.mayor;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.towns.Main;
import com.voxela.towns.timeUtils.TimeManager;
import com.voxela.towns.utils.FileManager;

import net.md_5.bungee.api.ChatColor;

public class NewTown {
		
	@SuppressWarnings("deprecation")
	public static void rentMethod(Player player, ProtectedRegion region, String town) {

		int townPrice = FileManager.dataFileCfg.getInt("regions." + region.getId() + ".price");
		
		if (Main.getEconomy().getBalance(player.getName()) >= townPrice) {
			
			Main.getEconomy().withdrawPlayer(player, townPrice);
			
			FileManager.dataFileCfg.set("regions." + region.getId() + ".name", town);
			FileManager.dataFileCfg.set("regions." + region.getId() + ".mayor", player.getUniqueId().toString());
			FileManager.dataFileCfg.set("regions." + region.getId() + ".rentuntil", TimeManager.timePlusWeek());
			FileManager.saveDataFile();
			
			Main.getEconomy().createPlayerAccount("town-" + town);
			
			UUID playerUUID = player.getUniqueId();
			
			DefaultDomain domain = region.getOwners();
			domain.addPlayer(playerUUID);
			region.setOwners(domain);
			
			region.setFlag(DefaultFlag.GREET_MESSAGE, Main.gamePrefix + ChatColor.AQUA + "Entering town: " + ChatColor.LIGHT_PURPLE + town + ChatColor.DARK_GRAY + " | " +  ChatColor.AQUA + "Mayor: " + ChatColor.LIGHT_PURPLE + player.getName());
			region.setFlag(DefaultFlag.FAREWELL_MESSAGE, Main.gamePrefix + ChatColor.AQUA + "Leaving town: " + ChatColor.LIGHT_PURPLE + town + ChatColor.DARK_GRAY + " | " +  ChatColor.AQUA + "Mayor: " + ChatColor.LIGHT_PURPLE + player.getName());
			
			Bukkit.broadcastMessage(Main.gamePrefix + ChatColor.GOLD + player.getName() + ChatColor.DARK_AQUA + " founded the town " + ChatColor.GOLD + town + "!");
			player.sendMessage(Main.gamePrefix + ChatColor.GRAY + "Make sure you have enough money in your" + ChatColor.DARK_AQUA + " town balance " + ChatColor.GRAY + "to afford the rent on " + ChatColor.DARK_AQUA + 
					TimeManager.timeFormatter(TimeManager.timePlusWeek()) + ChatColor.GRAY + " or it will be reset! Weekly price is " + ChatColor.DARK_AQUA + "$" + townPrice + ".");
		} else {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "You can't afford this town!");
		}
		
	}

}
