package com.voxela.towns.plotManagement;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.towns.Main;
import com.voxela.towns.timeUtils.TimeManager;
import com.voxela.towns.utils.FileManager;
import com.voxela.towns.utils.ChatUtils;

import net.md_5.bungee.api.ChatColor;

public class PlotCommand {
	
	public static void plotHelp(Player player) {
		
		player.sendMessage(ChatUtils.formatTitle("Voxela Towns"));
		player.sendMessage("");
		player.sendMessage(ChatColor.GOLD + "/town plot list" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "List the plots that you're renting.");
		player.sendMessage(ChatColor.GOLD + "/town plot info" + ChatColor.GRAY + " [town] [plot name]"+ ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Get info on a plot.");
		player.sendMessage(ChatColor.GOLD + "/town plot rent" + ChatColor.GRAY + " [plot name]"+ ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Rent a new plot.");
		player.sendMessage(ChatColor.GOLD + "/town plot unrent" + ChatColor.GRAY + " [plot name]"+ ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Stop renting a plot.");
		player.sendMessage(ChatColor.GOLD + "/town plot add" + ChatColor.GRAY + " [plot name] [player]" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Add a player to your plot.");
		player.sendMessage(ChatColor.GOLD + "/town plot del" + ChatColor.GRAY + " [plot name] [player]" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Remove a player.");
		player.sendMessage("");
		player.sendMessage(ChatUtils.divider);
	}

	public static void plotInfo(Player player, String town, ProtectedRegion townRegion, ProtectedRegion region) {
		
		if (!(FileManager.dataFileCfg.isSet("regions." + townRegion.getId() + ".plots." + region.getId()))) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "This property is not available.");
			return;
		}
		
		player.sendMessage(ChatUtils.formatTitle("Voxela Plots"));
		ChatUtils.sendCenteredMessage(player, ChatColor.GOLD + town + ChatColor.GRAY + " Plot Info");
				
		int plotPrice = FileManager.dataFileCfg.getInt("regions." + townRegion.getId() + ".plots." + region.getId() + ".price");
		
		// If region is owned...
		double weeklyPriceCut = Main.getInstance().getConfig().getDouble("weeklypricecut");
		int plotPriceWeekly = (int) (plotPrice * weeklyPriceCut);
		
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
			
			String rentDue = TimeManager.timeFormatter(FileManager.dataFileCfg.getString("regions." + townRegion.getId() + ".plots." + region.getId() + ".rentuntil"));
			
			player.sendMessage(ChatColor.DARK_AQUA + "Town: " + ChatColor.GRAY + town);
			player.sendMessage(ChatColor.DARK_AQUA + "Region: " + ChatColor.GRAY + region.getId());
			player.sendMessage(ChatColor.DARK_AQUA + "Price: " + ChatColor.GRAY + "$" + plotPriceWeekly + "/week");
			player.sendMessage(ChatColor.DARK_AQUA + "Rent Due: " + ChatColor.GRAY + rentDue);
			player.sendMessage(ChatColor.DARK_AQUA + "Owner: " + ChatColor.GRAY + ownerName);
			player.sendMessage(ChatColor.DARK_AQUA + "Members: " + ChatColor.GRAY + memberName);
			return;
			
		} else {
						
			player.sendMessage(ChatColor.DARK_AQUA + "Town: " + ChatColor.GRAY + town);
			player.sendMessage(ChatColor.DARK_AQUA + "Region: " + ChatColor.GRAY + region.getId());
			player.sendMessage(ChatColor.DARK_AQUA + "Price: " + ChatColor.GRAY + "$" + plotPrice);
			player.sendMessage("");
			ChatUtils.sendCenteredMessage(player, ChatColor.GREEN + "This plot is for sale! Type" + ChatColor.GOLD + " /plot rent " + region.getId() + ChatColor.GREEN + " to rent it.");
			player.sendMessage(ChatUtils.divider);
			return;
		}
		
	}
	
	public static void add(Player player, String playerToAdd, ProtectedRegion region) {
		
		UUID playerToAddUUID = ChatUtils.toUUID(playerToAdd);
		
		DefaultDomain domain = region.getMembers();
		domain.addPlayer(playerToAddUUID);
		
		region.setMembers(domain);
		player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Added " + ChatColor.GOLD + playerToAdd + ChatColor.GREEN + " to your town plot: " + ChatColor.GOLD + region.getId());
		
	}
	
	public static void delete(Player player, String playerToDelete, ProtectedRegion region) {
		
		UUID playerToAddUUID = ChatUtils.toUUID(playerToDelete);
		
		DefaultDomain domain = region.getMembers();
		domain.removePlayer(playerToAddUUID);
		
		region.setMembers(domain);
		player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Deleted " + ChatColor.GOLD + playerToDelete + ChatColor.GREEN + " from your town plot: " + ChatColor.GOLD + region.getId());
		
	}
	
}
