package com.voxela.towns.townInfo;

import java.util.ArrayList;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.towns.utils.ChatUtils;
import com.voxela.towns.utils.FileManager;

public class GetMembers {
	
	public static String getResidents(String town, ProtectedRegion region) {
		
		String memberName = "";		
		if (!(FileManager.dataFileCfg.getList("regions." + region.getId() + ".residents") == null)) {
			
			@SuppressWarnings("unchecked")
			ArrayList<String> membersList = (ArrayList<String>) FileManager.dataFileCfg.getList("regions." + region.getId() + ".residents");
			String[] membersArray = new String[membersList.size()];
			membersArray = membersList.toArray(membersArray);
			
            for(int i=0; i < membersArray.length; i++){
    			memberName += ChatUtils.fromUUID(membersArray[i]) + ", ";
            }
            return memberName;
		}
            
		memberName = "None!";
		return memberName;
		
	}
	
	public static String getDeputies(String town, ProtectedRegion region) {
		
		String deputyName = "";		
		if (!(FileManager.dataFileCfg.getList("regions." + region.getId() + ".deputies") == null)) {
			
			@SuppressWarnings("unchecked")
			ArrayList<String> deputyList = (ArrayList<String>) FileManager.dataFileCfg.getList("regions." + region.getId() + ".deputies");
			String[] deputyArray = new String[deputyList.size()];
			deputyArray = deputyList.toArray(deputyArray);
			
            for(int i=0; i < deputyArray.length; i++){
            	deputyName += ChatUtils.fromUUID(deputyArray[i]) + ", ";
            }
            return deputyName;
		}
            
		deputyName = "None!";
		return deputyName;
		
	}
}
