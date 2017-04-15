package com.voxela.plots.rent;

import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.plots.Main;
import net.md_5.bungee.api.ChatColor;

public class rentCommands {
	
	@SuppressWarnings("deprecation")
	public static void rent(Player player, ProtectedRegion region) {
		
		player.sendMessage("" + Main.economy.format(Main.economy.getBalance(player.getName())));
		if (Main.economy.getBalance(player.getName()) > 1) {
			
			player.sendMessage("test");
			
		} else {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "You cannot afford this plot!");
		}
		
	}

}
