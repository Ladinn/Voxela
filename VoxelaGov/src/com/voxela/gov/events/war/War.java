package com.voxela.gov.events.war;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.gov.Main;
import com.voxela.gov.checker.CheckFed;
import com.voxela.gov.checker.CheckProvince;
import com.voxela.gov.players.CombatantPlayer;
import com.voxela.gov.utils.ChatUtils;

import net.md_5.bungee.api.ChatColor;

public class War {
	
	private static HashMap<String, Integer> warMap = new HashMap<String, Integer>();

	public static boolean isWarring() {
		
		if (warMap.get("war") == null) return false;
		if (warMap.get("war") == 0) return false;
		if (warMap.get("war") == 1) return true;
		
		return false;		
	}
		
	public static void beginWar() {
		
		warMap.put("war", 1);
				
		Map<String,ProtectedRegion> regionMap = Main.getWorldGuard().getRegionManager(Bukkit.getWorld("world")).getRegions();
		ArrayList<ProtectedRegion> regionArray = new ArrayList<ProtectedRegion>(regionMap.values());
		
		ArrayList<String> playersInFed = CheckFed.getAllPlayersInFed();
		
		for (String player : playersInFed) CombatantPlayer.setCombatant(player);
		
		for (ProtectedRegion region : regionArray) {
			
			String regionName = region.getId();
			
			if (regionName.startsWith("war_")) {
				
				String[] regionSplit = regionName.split("_");
				String province = CheckProvince.stringToDataFile(regionSplit[1]);
				String fed = CheckProvince.getFed(province);
				
				WarPoints.changeObjectiveControl(regionName, fed);
			}
		}
		
		Bukkit.broadcastMessage(ChatUtils.divider);
		ChatUtils.broadcastCenteredMessage(ChatColor.RED + "The world is now at war! Provinces are able to be seized.");
		ChatUtils.broadcastCenteredMessage(ChatColor.GRAY + "Remain neutral by not attacking other players.");
		Bukkit.broadcastMessage(ChatUtils.divider);
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			
			Location loc = player.getLocation();
			player.playSound(loc, Sound.ENTITY_ENDERDRAGON_DEATH, 0.25F, 2.5F);
			player.playSound(loc, Sound.ENTITY_WOLF_HOWL, 0.15F, 0.75F);
		}
		
	}
	
	public static void endWar() {
		
		warMap.put("war", 0);
		
		Bukkit.broadcastMessage(ChatUtils.divider);
		ChatUtils.broadcastCenteredMessage(ChatColor.GREEN + "A ceasefire has been called and the war has ended.");
		ChatUtils.broadcastCenteredMessage(ChatColor.GRAY + "Next war: " + ChatColor.DARK_GRAY + " 10:00 PM EST");
		Bukkit.broadcastMessage(ChatUtils.divider);
		
		CombatantPlayer.clearCombatants();
		WarPoints.objectiveMap.clear();		
	}
}
