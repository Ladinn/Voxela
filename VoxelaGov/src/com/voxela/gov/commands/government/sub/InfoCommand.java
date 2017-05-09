package com.voxela.gov.commands.government.sub;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.voxela.gov.Main;
import com.voxela.gov.checker.CheckFed;
import com.voxela.gov.checker.CheckGov;
import com.voxela.gov.checker.CheckLaws;
import com.voxela.gov.checker.CheckProvince;
import com.voxela.gov.utils.ChatUtils;

import net.md_5.bungee.api.ChatColor;

public class InfoCommand {

	public static void info(Player player, String[] args) {

		String syntaxError = Main.gamePrefix + ChatColor.RED + "Incorrect command usage. Type " + ChatColor.GOLD
				+ "/gov info" + ChatColor.RED + " for help.";

		if (args.length == 1) {

			player.sendMessage(ChatUtils.formatTitle("Voxela Government"));
			ChatUtils.sendCenteredMessage(player, ChatColor.GOLD + "  " + ChatColor.UNDERLINE + "Information Commands");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "/gov info me" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Your player information.");
			player.sendMessage(ChatColor.GOLD + "/gov info here" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Info on the province you are in.");
			player.sendMessage(ChatColor.GOLD + "/gov info" + ChatColor.GRAY + " [province]" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Info on a specified province.");
			player.sendMessage(ChatColor.GOLD + "/gov info" + ChatColor.GRAY + " [federation]" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Info on a specified federation.");
			player.sendMessage(ChatUtils.divider);
			return;
		}
		
		if (args.length == 2) {
			
			if (args[1].equalsIgnoreCase("me")) {
				infoMe(player);
				return;
			}
			
			if (args[1].equalsIgnoreCase("here")) {
				infoHere(player);
				return;
			}

			String arg = args[1];
			ArrayList<String> provinces = CheckProvince.getProvinces();
			
			if (arg.equalsIgnoreCase("Acadia")) {
				infoAcadia(player);
				return;
			}
			if (provinces.toString().toLowerCase().contains(arg.toLowerCase())) {
				infoProvince(player, arg);
				return;
			}
			
			infoFed(player, arg);
			return;
		}

		if (args.length > 3) {
			player.sendMessage(syntaxError);
			return;
		}

	}
	
	private static void infoMe(Player player) {
		
		String fedNiceName = "None!";
				
		if (!(CheckFed.getPlayerFed(player).equals("None!"))) {
			fedNiceName = CheckFed.getNiceName(CheckFed.getPlayerFed(player));
		}
		
		String province = CheckProvince.checkProvince(player)[0];
		
		player.sendMessage(ChatUtils.formatTitle("Voxela Government"));
		ChatUtils.sendCenteredMessage(player, ChatColor.GOLD + "  " + ChatColor.UNDERLINE + "Information: " + player.getName());
		player.sendMessage("");
		player.sendMessage(ChatColor.DARK_AQUA + "Federation: " + ChatColor.GRAY + fedNiceName);
		player.sendMessage(ChatColor.DARK_AQUA + "Province: " + ChatColor.GRAY + province);
		player.sendMessage("");
		player.sendMessage(ChatUtils.divider);
		
	}
	
	private static void infoAcadia(Player player) {
		
		String primeMinister = "None!";
		if (!(CheckGov.getPrimeMinister() == null)) {
			primeMinister = CheckGov.getPrimeMinister().getName();
		}
		
		String police = CheckGov.policeList();
		
		player.sendMessage(ChatUtils.formatTitle("Voxela Government"));
		ChatUtils.sendCenteredMessage(player, ChatColor.GOLD + "  " + ChatColor.UNDERLINE + "Information: The Kingdom of Acadia");
		player.sendMessage("");
		player.sendMessage(ChatColor.DARK_AQUA + "Prime Minister: " + ChatColor.GRAY + primeMinister);
		player.sendMessage("");
		player.sendMessage(ChatUtils.divider);
		player.sendMessage("");
		player.sendMessage(ChatColor.DARK_AQUA + "Police Officers: " + ChatColor.GRAY + police);
		player.sendMessage("");
		player.sendMessage(ChatUtils.divider);
		
		
	}
	
	private static void infoFed(Player player, String fed) {
		
		if (!(CheckFed.fedExists(fed))) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "No such federation or province!");
			return;
		}
		
		String fedNiceName = CheckFed.getNiceName(fed);
		String chief = CheckFed.getFullChief(fed);
		String ideology = CheckFed.getIdeology(fed);
		int balance = (int) CheckFed.getBal(fed);
		
		player.sendMessage(ChatUtils.formatTitle("Voxela Government"));
		ChatUtils.sendCenteredMessage(player, ChatColor.GOLD + "  " + ChatColor.UNDERLINE + "Information: " + fedNiceName);
		player.sendMessage("");
		player.sendMessage(ChatColor.DARK_AQUA + "Chief: " + ChatColor.GRAY + chief);
		player.sendMessage(ChatColor.DARK_AQUA + "Ideology: " + ChatColor.GRAY + ideology);
		player.sendMessage(ChatColor.DARK_AQUA + "Balance: " + ChatColor.GRAY + "$" + balance);
		player.sendMessage("");
		player.sendMessage(ChatUtils.divider);
		player.sendMessage("");
		player.sendMessage(ChatColor.DARK_AQUA + "Deputies: " + ChatColor.GRAY + CheckFed.getDeputies(fed));
		player.sendMessage(ChatColor.DARK_AQUA + "Members: " + ChatColor.GRAY + CheckFed.getMembers(fed));
		player.sendMessage("");
		player.sendMessage(ChatUtils.divider);
		
	}

	private static void infoProvince(Player player, String provinceArg) {

		if (CheckProvince.stringToProvince(provinceArg) == null) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "No such province!");
			return;
		}

		String province = CheckProvince.stringToProvince(provinceArg);
		String provinceDataFile = CheckProvince.stringToDataFile(provinceArg);
		String fed = CheckProvince.getFed(provinceDataFile);
		String fedNiceName = CheckFed.getNiceName(fed);
		int taxRate = CheckFed.getTotalTaxRate(fed);
		//int execOrders = 4;

		player.sendMessage(ChatUtils.formatTitle("Voxela Government"));
		ChatUtils.sendCenteredMessage(player, ChatColor.GOLD + "  " + ChatColor.UNDERLINE + "Information: The Province of " + province);
		player.sendMessage("");
		ChatUtils.sendCenteredMessage(player, ChatColor.DARK_AQUA + "Governed by: " + ChatColor.GRAY + fedNiceName);
		player.sendMessage("");
		player.sendMessage(ChatUtils.divider);
		ChatUtils.sendCenteredMessage(player, ChatColor.GOLD + "Laws:");
		CheckLaws.returnFedLaws(player, fed);
		player.sendMessage(ChatUtils.divider);
		ChatUtils.sendCenteredMessage(player, ChatColor.DARK_AQUA + "Tax Rate: " + ChatColor.GRAY + taxRate + "%");
		player.sendMessage(ChatUtils.divider);
		ChatUtils.sendCenteredMessage(player, ChatColor.DARK_AQUA + "Type " + ChatColor.GOLD + "/gov info " + fed
				+ ChatColor.DARK_AQUA + " for info on");
		ChatUtils.sendCenteredMessage(player, ChatColor.DARK_AQUA + "the federation that controls this province.");
		player.sendMessage(ChatUtils.divider);
		return;

	}

	private static void infoHere(Player player) {

		String[] checkProvince = CheckProvince.checkProvince(player);
		String provinceInfo = checkProvince[1];

		ChatColor provColor1 = ChatColor.GRAY;
		ChatColor provColor2 = ChatColor.GRAY;
		ChatColor provColor3 = ChatColor.GRAY;
		ChatColor provColor4 = ChatColor.GRAY;
		ChatColor provColor5 = ChatColor.GRAY;
		ChatColor provColor6 = ChatColor.GRAY;
		ChatColor provColor7 = ChatColor.GRAY;
		if (provinceInfo.equalsIgnoreCase("Penobscot")) {
			provColor1 = ChatColor.GREEN;
		}
		if (provinceInfo.equalsIgnoreCase("Persolum")) {
			provColor2 = ChatColor.GREEN;
		}
		if (provinceInfo.equalsIgnoreCase("Schoodic")) {
			provColor3 = ChatColor.GREEN;
		}
		if (provinceInfo.equalsIgnoreCase("Torrhen")) {
			provColor4 = ChatColor.GREEN;
		}
		if (provinceInfo.equalsIgnoreCase("Thurmont")) {
			provColor5 = ChatColor.GREEN;
		}
		if (provinceInfo.equalsIgnoreCase("Rhinefield")) {
			provColor6 = ChatColor.GREEN;
		}
		if (provinceInfo.equalsIgnoreCase("Atacama")) {
			provColor7 = ChatColor.GREEN;
		}

		player.sendMessage(ChatUtils.formatTitle("Voxela Government"));
		ChatUtils.sendCenteredMessage(player,
				provColor1 + " Penobscot " + ChatColor.DARK_GRAY + "|" + provColor2 + " Persolum Crater "
						+ ChatColor.DARK_GRAY + "|" + provColor3 + " Schoodic Glacier " + ChatColor.DARK_GRAY + "|"
						+ provColor4 + " Torrhen Vale");
		ChatUtils.sendCenteredMessage(player, provColor5 + "Thurmont Plains " + ChatColor.DARK_GRAY + "|" + provColor6
				+ " Rhinefield " + ChatColor.DARK_GRAY + "|" + provColor7 + " Atacama Outlands");
		player.sendMessage(ChatUtils.divider);
		if (provinceInfo == "none") {
			ChatUtils.sendCenteredMessage(player, ChatColor.DARK_AQUA + "You are not currently in a province.");
			ChatUtils.sendCenteredMessage(player,
					ChatColor.DARK_AQUA + "View a map of the provinces with " + ChatColor.GOLD + "/map.");
		} else {
			
			String fed = CheckProvince.getFed(CheckProvince.stringToDataFile(provinceInfo));
			
			ChatUtils.sendCenteredMessage(player, ChatColor.DARK_AQUA + CheckFed.getFullChief(fed) + ChatColor.GRAY + " of " + ChatColor.DARK_AQUA + CheckFed.getNiceName(fed));			
			player.sendMessage(ChatUtils.divider);
			
			ChatUtils.sendCenteredMessage(player, ChatColor.DARK_AQUA + "Type " + ChatColor.GOLD + "/gov info "
					+ provinceInfo + ChatColor.DARK_AQUA + " for more");
			ChatUtils.sendCenteredMessage(player, ChatColor.DARK_AQUA + "information on the province you are in.");
		}
		player.sendMessage(ChatUtils.divider);
	}

}
