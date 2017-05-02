package com.voxela.chat.formatter;

import org.bukkit.entity.Player;

import com.voxela.chat.Main;
import com.voxela.chat.formatter.prefixHandler.Getter;

import net.md_5.bungee.api.ChatColor;

public class Format {
	
	public static String format(Player player, boolean local) {
		
		String channel = "";
		
		if (local == true) {
	        channel = ChatColor.DARK_GRAY + " [" + ChatColor.GOLD + "L" + ChatColor.DARK_GRAY + "] ";
		}
		if (local == false) {
			channel = ChatColor.DARK_GRAY + " [" + ChatColor.RED + "G" + ChatColor.DARK_GRAY + "] ";
		}
		
		String prefix = Main.getChat().getPlayerPrefix(player).replaceAll("&([0-9a-f])", "§$1");
        String voxPrefix = Getter.prefix(player);
        
        if (!(prefix.isEmpty())) {
        	prefix = prefix + " ";
        }
        
        if (!(voxPrefix.isEmpty())) {
        	voxPrefix = voxPrefix + " ";
        }
        
        String playerName = ChatColor.AQUA + voxPrefix + player.getName() + ": ";
                
        String format = channel + prefix + playerName + ChatColor.GRAY;
        
        return format;
		
	}

}
