package com.voxela.chat.formatter.prefixHandler;

import org.bukkit.entity.Player;

import com.voxela.towns.townInfo.MayorCheck;

public class Town {
	
	public static String getPrefix(Player player) {
		
		String prefix = "";
		
		if (MayorCheck.isAMayor(player)) {
			prefix = "Mayor";			
		}
		return prefix;
	}

}
