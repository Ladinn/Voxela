package com.voxela.towns.townInfo;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.towns.timeUtils.TimeManager;
import com.voxela.towns.Main;
import com.voxela.towns.utils.FileManager;

public class TownInfo {
	
	public static int getPrice(ProtectedRegion region, String town) {
		
		int price = FileManager.dataFileCfg.getInt("regions." + region.getId() + ".price");
		return price;		
	}
	
	@SuppressWarnings("deprecation")
	public static double getTownBal(ProtectedRegion region, String town) {
		
		double bal = Main.getEconomy().getBalance("town-" + town);
		return bal;
	}
	
	public static String getTownRentDue(ProtectedRegion region, String town) {
		
		String rentDue = TimeManager.timeFormatter(FileManager.dataFileCfg.getString("regions." + region.getId() + ".rentuntil"));
		return rentDue;
		
	}
	
	public static boolean townExists(String town) {
		
		for (String key : FileManager.dataFileCfg.getConfigurationSection("regions").getKeys(false)) {

			if (FileManager.dataFileCfg.getString("regions." + key + ".name").equals(town)) {
        		return true;
        	}
        }
		return false;
	}
	
	public static boolean regionIsTaken(ProtectedRegion region) {
		
		if (region.hasMembersOrOwners()) {
			return true;			
		}
		
		return false;
		
	}
	
	public static String getAvailableTowns() {
		
		String availTowns = "";
		
		for (String key : FileManager.dataFileCfg.getConfigurationSection("regions").getKeys(false)) {
        	
        	if (!(FileManager.dataFileCfg.getString("regions." + key + ".name").equals("unowned"))) {
        		continue;
        	} else {
    			availTowns += key + ", ";
        	}
        }
		
		if (availTowns == "") {
				return "None!";
		}
		
    	return availTowns;
    	
	}

}
