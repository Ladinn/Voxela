package com.voxela.chat.formatter.prefixHandler;

import org.bukkit.entity.Player;

public class Getter {
	
	public static String prefix(Player player) {
		
        String fedTitle = Federation.getPrefix(player);
        String townTitle = Town.getPrefix(player);
        String prefix = " ";
        
        if (townTitle.equals("")) townTitle = "";
        else {
            prefix = townTitle;
        }
        
        if (fedTitle.equals("")) fedTitle = "";
        else {
        	prefix = fedTitle;
        }
        
		return prefix;
	}

}
