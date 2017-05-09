package com.voxela.gov.commands.government.sub.manage;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.voxela.gov.Main;
import com.voxela.gov.checker.CheckFed;

import net.md_5.bungee.api.ChatColor;

public class LeaveCommand {
	
	public static void leave(Player player, String[] args) {
		
		if (CheckFed.getPlayerFed(player) == null) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "You are not in a federation!");
			return;
		}
		
		String fed = CheckFed.getPlayerFed(player);
				
		if ( CheckFed.getChief(fed).equals(player.getUniqueId().toString()) ) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "You can't abandon your federation!");
			return;
		}
		
		CheckFed.removeMember(player.getName(), fed);
	    
	    player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "You have left " + ChatColor.GOLD + CheckFed.getNiceName(fed) + ".");
	    
		ArrayList<Player> online = CheckFed.getOnlinePlayersInFed(fed);
		
		for (Player fedPlayer : online) fedPlayer.sendMessage(Main.gamePrefix + ChatColor.DARK_AQUA + player.getName() + ChatColor.GRAY + " has left the federation.");
		
	    return;	
		
	}
}
