package com.voxela.gov.players.primeminister;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.voxela.gov.Main;
import com.voxela.gov.utils.ChatUtils;

import net.md_5.bungee.api.ChatColor;

public class PrimeMinisterPlayer {
	
	private static HashMap<String, Player> pmMap = new HashMap<String, Player>();
	
	public static boolean electionsEnabled() { 
		if (Main.getInstance().getConfig().getBoolean("elections")) return true;
		return false;
	}
	
	public static void setPrimeMinister(Player player) {
		
		Bukkit.broadcastMessage(ChatUtils.divider);
		ChatUtils.broadcastCenteredMessage("" + ChatColor.GOLD + player + ChatColor.GREEN + " has been elected");
		ChatUtils.broadcastCenteredMessage(ChatColor.GREEN + "as the new Prime Minister!");
		Bukkit.broadcastMessage(ChatUtils.divider);
		
		World world = Bukkit.getWorld("world");
		Location loc = new Location(world, 1507, 61, -1815);
		player.teleport(loc);
				
		pmMap.put("PM", player);
	}
	
	public static void removePrimeMinister() {
		pmMap.clear();
	}
	
	public static Player getPrimeMinister() { return pmMap.get("PM"); }
	
	public static boolean isPrimeMinister() {
		
		if (pmMap.isEmpty()) return false;
		return true;
		
	}
	
	public static boolean playerIsPrimeMinister(Player player) {
		
		if (pmMap.containsValue(player)) return true;
		return false;
		
	}
}
