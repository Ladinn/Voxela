package com.voxela.plots.plotManagement;

import java.util.Map;
import java.util.UUID;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.plots.Main;
import com.voxela.plots.timeUtils.TimeManager;
import com.voxela.plots.utils.ChatUtils;
import com.voxela.plots.utils.FileManager;

public class UserPlot {
	
	public static void plotInfo(Player player, ProtectedRegion region) {
		
		if (!(FileManager.dataFileCfg.isSet("regions." + region.getId()))) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "This property is not available.");
			return;
		}
		
		player.sendMessage(ChatUtils.formatTitle("Voxela Plots"));
		ChatUtils.sendCenteredMessage(player, ChatColor.GOLD + "Region Info");
				
		int plotPrice = FileManager.dataFileCfg.getInt("regions." + region.getId() + ".price");
		
		// If region is owned...
		if ((region.getOwners().getUniqueIds().toString().contains("-") )) {
		
			String ownerUUIDs1 = region.getOwners().getUniqueIds().toString();
			String ownerUUIDs2 = ownerUUIDs1.replace("[", "");
			String ownerUUIDs3 = ownerUUIDs2.replace("]", "");
			
			String ownerName = null;
			if (ownerUUIDs3.contains(",")) {
				ownerName = ChatColor.RED + "Error: Region has multiple owners. Contact admin.";
				System.out.print(Main.consolePrefix + "Player plot contains multiple owners: " + region.getId());
			} else {
				ownerName = ChatUtils.fromUUID(ownerUUIDs3);
			}
			
			String memberName = "";
			if ((region.getMembers().getUniqueIds().toString().contains("-") )) {
				
				String memberUUIDs1 = region.getMembers().getUniqueIds().toString();
				String memberUUIDs2 = memberUUIDs1.replace("[", "");
				String memberUUIDs3 = memberUUIDs2.replace("]", "");
				String[] strArray = memberUUIDs3.split(", ");
				
	            for(int i=0; i < strArray.length; i++){
	    			memberName += ChatUtils.fromUUID(strArray[i]) + ", ";
	            }
			} else {
				memberName = "None!";
			}
			
			String rentDue = TimeManager.timeFormatter(FileManager.dataFileCfg.getString("regions." + region.getId() + ".rentuntil"));
			
			player.sendMessage(ChatColor.DARK_AQUA + "Region: " + ChatColor.GRAY + region.getId());
			player.sendMessage(ChatColor.DARK_AQUA + "Price: " + ChatColor.GRAY + "$" + plotPrice + "/week");
			player.sendMessage(ChatColor.DARK_AQUA + "Rent Due: " + ChatColor.GRAY + rentDue);
			player.sendMessage(ChatColor.DARK_AQUA + "Owner: " + ChatColor.GRAY + ownerName);
			player.sendMessage(ChatColor.DARK_AQUA + "Members: " + ChatColor.GRAY + memberName);
			return;
			
		} else {
			
			player.sendMessage(ChatColor.DARK_AQUA + "Region: " + ChatColor.GRAY + region.getId());
			player.sendMessage(ChatColor.DARK_AQUA + "Price: " + ChatColor.GRAY + "$" + plotPrice + "/week");
			player.sendMessage("");
			ChatUtils.sendCenteredMessage(player, ChatColor.GREEN + "This plot is for sale! Type" + ChatColor.GOLD + " /plot rent " + region.getId() + ChatColor.GREEN + " to rent it.");
			return;
		}
		
	}
	
	public static void add(Player player, String playerToAdd, ProtectedRegion region) {
		
		UUID playerToAddUUID = ChatUtils.toUUID(playerToAdd);
		
		DefaultDomain domain = region.getMembers();
		domain.addPlayer(playerToAddUUID);
		
		region.setMembers(domain);
		player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Added " + ChatColor.GOLD + playerToAdd + ChatColor.GREEN + " to your plot: " + ChatColor.GOLD + region.getId());
		
	}
	
	public static void delete(Player player, String playerToDelete, ProtectedRegion region) {
		
		UUID playerToAddUUID = ChatUtils.toUUID(playerToDelete);
		
		DefaultDomain domain = region.getMembers();
		domain.removePlayer(playerToAddUUID);
		
		region.setMembers(domain);
		player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Deleted " + ChatColor.GOLD + playerToDelete + ChatColor.GREEN + " from your plot: " + ChatColor.GOLD + region.getId());
		
	}
	
	public static void listPlots(Player player) {
		
        Map<String, ProtectedRegion> regionMap = Main.getWorldGuard().getRegionManager(player.getWorld()).getRegions();
        
		player.sendMessage(ChatUtils.formatTitle("Voxela Plots"));
		
		ChatUtils.sendCenteredMessage(player, ChatColor.GOLD + "Plots you have access to:");
        for (ProtectedRegion region : regionMap.values()) {
        	
            if(region.isOwner(Main.getWorldGuard().wrapPlayer(player))) {
            	            	
                player.sendMessage(ChatColor.DARK_AQUA + region.getId() + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Owner");
                continue;
                
            } else if(region.isMember(Main.getWorldGuard().wrapPlayer(player))) {
            	
                player.sendMessage(ChatColor.DARK_AQUA + region.getId() + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Member");
                
            }
        }
        
		player.sendMessage(ChatUtils.divider);
		
	}

}
