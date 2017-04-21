package com.voxela.towns.townInfo;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.towns.Main;
import com.voxela.towns.utils.ChatUtils;
import com.voxela.towns.utils.FileManager;

public class MayorCheck {
	
	public static boolean isMayor(ProtectedRegion region, Player player) {
		
		String mayor = FileManager.dataFileCfg.getString("regions." + region.getId() + ".mayor");
		
		if (mayor.contains(player.getUniqueId().toString())) {
			return true;
		} else return false;	
	}
	
	public static boolean isMayorOrDeputy(ProtectedRegion region, Player player) {
		
		String mayor = FileManager.dataFileCfg.getString("regions." + region.getId() + ".mayor");
		String deputy = FileManager.dataFileCfg.getString("regions." + region.getId() + ".deputies");
		
		if (mayor.contains(player.getUniqueId().toString()) || deputy.contains(player.getUniqueId().toString())) {
			return true;
		} else return false;	
	}
	
	public static boolean isDeputy(ProtectedRegion region, String player) {
		
		String deputy = FileManager.dataFileCfg.getString("regions." + region.getId() + ".deputies");
		
		if (deputy.contains(player)) {
			return true;
		} else return false;	
	}
	
	public static String getMayor(ProtectedRegion region) {
		
		String mayor = ChatUtils.fromUUID(FileManager.dataFileCfg.getString("regions." + region.getId() + ".mayor"));
		return mayor;
	}
	
	public static String getMayorFromString(String town) {
		
		ProtectedRegion region = Main.getWorldGuard().getRegionManager(Bukkit.getWorld("world")).getRegion(town);
		
		return FileManager.dataFileCfg.getString("regions." + region.getId() + ".mayor");		
	}

}
