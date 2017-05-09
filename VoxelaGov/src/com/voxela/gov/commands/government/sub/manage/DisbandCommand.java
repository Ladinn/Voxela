package com.voxela.gov.commands.government.sub.manage;

import org.bukkit.entity.Player;

import com.voxela.gov.Main;
import com.voxela.gov.checker.CheckFed;
import com.voxela.gov.utils.FileManager;

import net.md_5.bungee.api.ChatColor;

public class DisbandCommand {
	
	public static void disband(Player player, String[] args) {
		
		String fed = CheckFed.getPlayerFed(player);
		
		if (!(CheckFed.getChief(fed).equalsIgnoreCase(player.getUniqueId().toString()))) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "This can only be done by the federation chief.");
			return;
		}
		
		FileManager.dataFileCfg.set("federations." + fed, null);
		FileManager.saveDataFile();
		
		player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Deleted federation: " + ChatColor.GOLD + fed);
	}

}
