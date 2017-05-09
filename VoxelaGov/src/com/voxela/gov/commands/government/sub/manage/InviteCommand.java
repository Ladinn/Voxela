package com.voxela.gov.commands.government.sub.manage;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.voxela.gov.Main;
import com.voxela.gov.checker.CheckFed;

import net.md_5.bungee.api.ChatColor;

public class InviteCommand {
	
	public static HashMap<Player, String> inviteMap = new HashMap<Player,String>();
	
	public static void invite(Player player, String[] args) {
		
		String fed = CheckFed.getPlayerFed(player);
		
		if (! (Bukkit.getOnlinePlayers().toString().contains(args[2]))) {
			
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "The player " + ChatColor.GOLD + args[2] + ChatColor.RED + " is not online.");
			return;
		}
		
		Player playerInvited = Bukkit.getPlayer(args[2]);
		
		if (!( CheckFed.getPlayerFed(playerInvited).equals("None!") )) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "This player is already in a federation!");
			return;
		}
		
		inviteMap.put(playerInvited, fed);
		player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Successfully invited " + ChatColor.GOLD + playerInvited.getName() + ".");
		
		playerInvited.sendMessage(Main.gamePrefix + ChatColor.GREEN + "You have been invited to join " + ChatColor.GOLD + CheckFed.getNiceName(fed) + ".");
		playerInvited.sendMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.GRAY + "Type " + ChatColor.DARK_AQUA + "/gov join" + ChatColor.GRAY + " to accept.");
		return;
	}
}
