package com.voxela.gov.commands.government.sub.manage;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.voxela.gov.Main;
import com.voxela.gov.checker.CheckFed;
import com.voxela.gov.checker.CheckMandate;
import com.voxela.gov.utils.FileManager;

import net.md_5.bungee.api.ChatColor;

public class CreateCommand {
	
	public static int price = Main.getInstance().getConfig().getInt("fedprice");
	
	public static void create(Player player, String[] args) {
		
		if (!(args.length == 3)) {
			player.sendMessage(ManageCommand.syntaxError);
			return;
		}
		
		String name = args[2];
		
		if (!(CheckFed.getPlayerFed(player).equals("None!"))) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "You are already in a federation!");
			player.sendMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.GRAY + "Leave your federation with " + ChatColor.DARK_AQUA + "/gov manage leave.");
			return;
		}
		
		if (name.length() > 30) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "Your federation name is too long!");
			return;
		}
		
		if (CheckFed.fedExists(name)) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "This name is taken!");
			return;
		}
		
		if (Main.getEconomy().getBalance(player) < price) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "Insufficient funds! Cost: " + ChatColor.GOLD + "$" + price + ".");
			return;
		}
		
		Main.getEconomy().withdrawPlayer(player, price);
		
		String dataFile = "federations." + name;
		
		FileManager.dataFileCfg.set(dataFile + ".chief", player.getUniqueId().toString());
		FileManager.dataFileCfg.set(dataFile + ".title", "Chief");
		FileManager.dataFileCfg.set(dataFile + ".ideology", "Republic");
		FileManager.dataFileCfg.set(dataFile + ".taxrate", "0");
		
		FileManager.dataFileCfg.createSection(dataFile + ".deputies");
		FileManager.dataFileCfg.createSection(dataFile + ".members");
		
		FileManager.dataFileCfg.set(dataFile + ".laws", Arrays.asList(new String[] {"None!"}));
		
		FileManager.dataFileCfg.createSection(dataFile + ".mandates");
		
		ArrayList<String> mandates = CheckMandate.getMandates();
		
		for (String mandate : mandates) {
			FileManager.dataFileCfg.set(dataFile + ".mandates." + mandate, false);
		}	
		
	    FileManager.saveDataFile();
		
		Bukkit.broadcastMessage(Main.gamePrefix + ChatColor.GOLD + player.getName() + ChatColor.GREEN + " has founded " + ChatColor.GOLD + name + ".");
		player.sendMessage(Main.gamePrefix + ChatColor.GRAY + "Manage your federation with " + ChatColor.DARK_AQUA + "/gov manage chief.");
		return;		
	}

}
