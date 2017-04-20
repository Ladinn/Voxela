package com.voxela.towns.utils;

import org.bukkit.Bukkit;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.towns.Main;

public class TownRegionConvert {
	
	public static ProtectedRegion townToRegion(String town) {
		
		for (String key : FileManager.dataFileCfg.getConfigurationSection("regions").getKeys(false)) {
			
			String regionString1 = FileManager.dataFileCfg.getString("regions." + key);
			String regionString2 = regionString1.replace("MemorySection[path='regions.", "");
			String regionString = regionString2.replace("', root='YamlConfiguration']", "");		

        	if (FileManager.dataFileCfg.getString("regions." + key + ".name").contains(town)) {

        		ProtectedRegion region = Main.getWorldGuard().getRegionManager(Bukkit.getWorld("world")).getRegion(regionString);
        		return region;
        		
        	}			
        } return null;
	}
	
	public static String regionToTown(ProtectedRegion region) {
		
		return FileManager.dataFileCfg.getString("regions." + region.getId() + ".name");
	}
	
}
