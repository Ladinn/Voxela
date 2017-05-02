package com.voxela.chat.location;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.voxela.chat.Main;
import com.voxela.gov.checker.CheckProvince;

import net.md_5.bungee.api.ChatColor;

public class InProvince {

	public static void local(Player player, AsyncPlayerChatEvent e) {

		String province = CheckProvince.checkProvince(player)[1];
		
		e.getRecipients().clear();
				
		if (province.contains("none")) {

			noPlayers(player);
			
	        List<Entity> entities = player.getNearbyEntities(80, 80, 80);
	        List<Player> canSee = new ArrayList<Player>();
	       
	        for(Entity entity : entities){
	        	
	            if(entity instanceof Player){
	                Player p2 = (Player) entity;
	                canSee.add(p2);
	            }
	        }
	        
			e.getRecipients().addAll(canSee);
			return;
		}
		
		ArrayList<Player> playersInProvince = CheckProvince.getPlayersInProvince(province.toLowerCase());
		
		if (playersInProvince.size() == 1) noPlayers(player);
				
		e.getRecipients().addAll(playersInProvince);

	}
	
	private static void noPlayers(Player player) {
		
		player.sendMessage(Main.gamePrefix + ChatColor.GRAY + ChatColor.ITALIC
				+ "Nobody is in this province, so no one can hear you!");
		player.sendMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.GRAY + "Speak in global chat with " + ChatColor.DARK_AQUA + "/g [message].");
		
	}
}
