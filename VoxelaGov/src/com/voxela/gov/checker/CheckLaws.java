package com.voxela.gov.checker;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.voxela.gov.utils.FileManager;

import net.md_5.bungee.api.ChatColor;

public class CheckLaws {
	
	public static void returnLaws(Player player) { lawFormatter(player, getLawList()); }
	
	public static void returnFedLaws(Player player, String fed) { lawFormatter(player, getFedLawList(fed)); }
	
	private static String[] getFedLawList(String fed) {

		if ((FileManager.dataFileCfg.getList("federations." + fed + ".laws") == null)) return new String[] {"None!"};
			
		else {
			
			@SuppressWarnings("unchecked")
			ArrayList<String> lawList = (ArrayList<String>) FileManager.dataFileCfg.getList("federations." + fed + ".laws");
			String[] lawArray = new String[lawList.size()];
			lawArray = lawList.toArray(lawArray);
			
			return lawArray;	
			
		}
	}
	
	private static String[] getLawList() {

		if ((FileManager.dataFileCfg.getList("federations.Acadia.laws") == null)) return new String[] {"None!"};
			
		else {
			
			@SuppressWarnings("unchecked")
			ArrayList<String> lawList = (ArrayList<String>) FileManager.dataFileCfg.getList("federations.Acadia.laws");
			String[] lawArray = new String[lawList.size()];
			lawArray = lawList.toArray(lawArray);
			
			return lawArray;	
			
		}
	}
	
	private static void lawFormatter(Player player, String[] lawArray) {

        for(int i=0; i < lawArray.length; i++){
        	
        	int num = i+1;
			player.sendMessage("" + ChatColor.DARK_AQUA + num + ". " + ChatColor.GRAY + lawArray[i]);
			
        }	
	}
}
