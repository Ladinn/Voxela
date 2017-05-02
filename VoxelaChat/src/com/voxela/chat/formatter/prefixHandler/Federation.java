package com.voxela.chat.formatter.prefixHandler;

import org.bukkit.entity.Player;

import com.voxela.gov.checker.CheckFed;

public class Federation {
	
	public static String getPrefix(Player player) {
		
		String fed = CheckFed.getPlayerFed(player);
		
		if (fed.equals("None!")) return "";
				
		String chief = CheckFed.getChief(fed);
		String playerUUID = player.getUniqueId().toString();
		
		if (!(chief.equals(playerUUID))) return "";
		
		String title = CheckFed.getTitle(fed);
		
		return title;		
		
	}

}
