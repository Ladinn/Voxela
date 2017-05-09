package com.voxela.gov.events.war;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.gov.Main;
import com.voxela.gov.checker.CheckFed;
import com.voxela.gov.checker.CheckProvince;
import com.voxela.gov.utils.ChatUtils;
import com.voxela.gov.utils.FileManager;

import net.md_5.bungee.api.ChatColor;

public class WarPoints {
	
	public static HashMap<String, String> objectiveMap = new HashMap<String, String>();
	
	public static void changeObjectiveControl(String region, String fed) {
		objectiveMap.remove(region);
		objectiveMap.put(region, fed);
		System.out.print(Main.consolePrefix + "Set " + fed + " as controller of " + region);
	}
	
	public static String getObjectiveController(String region) { return objectiveMap.get(region); }
	
	public static void changeProvinceControl(String province, String fed) {
		FileManager.dataFileCfg.set("provinces." + province + ".federation", fed);
		FileManager.saveDataFile();
	}
	
	public static boolean checkCamps(String fed, String province) {
				
		Map<String, ProtectedRegion> regionMap = Main.getWorldGuard().getRegionManager(Bukkit.getWorld("world")).getRegions();
		ArrayList<String> regionArray = new ArrayList<String>(regionMap.keySet());
		
		int camps = 0;
		for (String region : regionArray) {
						
			if (region.startsWith("war_")) {
								
				String[] regionSplit = region.split("_");
				String regionProvince = CheckProvince.stringToProvince(regionSplit[1]);
				String objectiveType = regionSplit[2];
								
				if (!(regionProvince.contains(province))) continue;
				
				if (objectiveType.equalsIgnoreCase("camp") && (getObjectiveController(region) == fed)) camps++;
				
				if (camps == 5) return true;

			}		
		}
		return false;		
	}	
	
	public static boolean checkOutposts(String fed, String province) {
				
		Map<String, ProtectedRegion> regionMap = Main.getWorldGuard().getRegionManager(Bukkit.getWorld("world")).getRegions();
		ArrayList<String> regionArray = new ArrayList<String>(regionMap.keySet());
		
		int outposts = 0;
		for (String region : regionArray) {
			
			if (region.startsWith("war_")) {
								
				String[] regionSplit = region.split("_");
				String regionProvince = CheckProvince.stringToProvince(regionSplit[1]);
				String objectiveType = regionSplit[2];
				
				if (!(regionProvince.contains(province))) continue;
				
				if (objectiveType.equalsIgnoreCase("outpost") && (getObjectiveController(region) == fed)) outposts++;
				
				if (outposts == 3) return true;

			}		
		}
		return false;		
	}	
	
	public static void checkProvinces(String fed) {
		
		ArrayList<String> provinces = CheckProvince.getProvinces();
		
		Map<String, ProtectedRegion> regionMap = Main.getWorldGuard().getRegionManager(Bukkit.getWorld("world")).getRegions();
		ArrayList<String> regionArray = new ArrayList<String>(regionMap.keySet());
		
		int points = 0;
		for (String region : regionArray) {
			
			if (region.startsWith("war_")) {
				
				String[] regionSplit = region.split("_");
				String regionProvince = CheckProvince.stringToDataFile(regionSplit[1]);
				
				for (String province : provinces) {
					
					if (regionProvince == province && (getObjectiveController(fed) == fed)) points++;
					
					if (points == 11) { changeProvinceControl(province, fed); }
					
					Bukkit.broadcastMessage(ChatUtils.divider);
					ChatUtils.broadcastCenteredMessage(ChatColor.GOLD + CheckFed.getNiceName(fed) + ChatColor.GREEN + " has captured " + ChatColor.GOLD + CheckProvince.stringToProvince(province) + "!");
					Bukkit.broadcastMessage(ChatUtils.divider);
				}				
			}		
		}		
	}	
}
