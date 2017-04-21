package com.voxela.towns.townManagement;

import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.towns.Main;
import com.voxela.towns.townInfo.GetMembers;
import com.voxela.towns.townInfo.MayorCheck;
import com.voxela.towns.townInfo.TownInfo;
import com.voxela.towns.utils.ChatUtils;
import com.voxela.towns.utils.FileManager;

import net.md_5.bungee.api.ChatColor;

public class InfoTown {
	
	public static void infoTown(Player player, ProtectedRegion region, String town) {
		
		String members = GetMembers.getResidents(town, region);
		String deputies = GetMembers.getDeputies(town, region);
		String mayor = MayorCheck.getMayor(region);
		double weeklyPriceCut = Main.getInstance().getConfig().getDouble("weeklypricecut");
		int price = (int) (FileManager.dataFileCfg.getInt("regions." + region.getId() + ".price") * weeklyPriceCut);
		int bal = (int) TownInfo.getTownBal(region, town);
		String rentDue = TownInfo.getTownRentDue(region, town);

		String rentResponse = ChatColor.GREEN + "[$]";
		
		if (bal < price) {
			
			rentResponse = ChatColor.RED + "[$]";
			
		}
		
		player.sendMessage(ChatUtils.formatTitle("Voxela Towns"));
		ChatUtils.sendCenteredMessage(player, ChatColor.GOLD + town + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Town Information");
		player.sendMessage("");
		player.sendMessage(ChatColor.DARK_AQUA + "Bank: " + ChatColor.GRAY + "$" + bal + ChatColor.DARK_GRAY + " | " + ChatColor.DARK_AQUA + 
				"Weekly Rent: " + ChatColor.GRAY + "$" + price);
		player.sendMessage(ChatColor.DARK_AQUA + "Rent Due: " + ChatColor.GRAY + rentDue + " " + rentResponse);
		player.sendMessage(ChatColor.DARK_AQUA + "Mayor: " + ChatColor.GRAY + mayor);
		player.sendMessage(ChatColor.DARK_AQUA + "Deputies: " + ChatColor.GRAY + deputies);
		player.sendMessage(ChatColor.DARK_AQUA + "Residents: " + ChatColor.GRAY + members);
		player.sendMessage(ChatUtils.divider);

	}

}
