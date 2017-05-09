package com.voxela.gov.players;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PolicePlayer {
	
	private static HashMap<Player, Integer> policeMap = new HashMap<Player, Integer>();
	
	public static void addPlayer(Player player) { policeMap.put(player, 1);	}
	
	public static void removePlayer(Player player) { policeMap.put(player, 0); }
	
	public static ArrayList<Player> getPolice() { 
		
		ArrayList<Player> police = new ArrayList<Player>();
		
		if (policeMap.isEmpty()) return police;
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (policeMap.get(player).equals(1)) police.add(player);
		}
		
		return police;
		
	}	
	
	public static boolean isPoliceEmpty() {
		
		if (policeMap.isEmpty()) return true;
		return false;
		
	}
	
	public static boolean playerIsPolice(Player player) {
		
		if (policeMap.containsValue(player)) {
			if (policeMap.get(player).equals("1")) return true;
		}
		return false;
		
	}

}
