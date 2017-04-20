package com.voxela.towns.townInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.towns.Main;

public class GetChildren {

	public static List<ProtectedRegion> getChildren(ProtectedRegion region) {
		
        Map<String, ProtectedRegion> regionMap = Main.getWorldGuard().getRegionManager(Bukkit.getWorld("world")).getRegions();
		
		List<ProtectedRegion> regionArray = new ArrayList<ProtectedRegion>();
		
        for (ProtectedRegion regionToCheck : regionMap.values()) {
        	
        	if ( regionToCheck.getParent() == region ) {
            	regionArray.add(regionToCheck);
        		continue;
        	}
        }
        return regionArray;
	}
	
}
