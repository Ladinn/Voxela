package com.voxela.gov.commands.government.sub.manage;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.voxela.gov.Main;
import com.voxela.gov.checker.CheckFed;
import com.voxela.gov.utils.ChatUtils;
import net.md_5.bungee.api.ChatColor;

public class ManageCommand {
	
	public static String syntaxError = Main.gamePrefix + ChatColor.RED + "Incorrect command usage. Type " + ChatColor.GOLD + "/gov" + ChatColor.RED + " for help.";
		
	public static void manage(Player player, String[] args) {
		
		if (args.length == 1) {
			
			player.sendMessage(ChatUtils.formatTitle("Voxela Government"));
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "/gov manage create" + ChatColor.GRAY + " [name]" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Found a new federation." + ChatColor.GRAY + " ($" + CreateCommand.price + ")");
			player.sendMessage(ChatColor.GOLD + "/gov manage leave" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Leave your federation.");
			player.sendMessage(ChatColor.GOLD + "/gov manage chief" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Manage your existing federation.");
			player.sendMessage("");
			player.sendMessage(ChatUtils.divider);
			return;
			
		}
		
		if (args[1].equalsIgnoreCase("chief")) {
			
			player.sendMessage(ChatUtils.formatTitle("Voxela Government"));
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "/gov manage"  + ChatColor.GOLD + " laws");
			player.sendMessage(" " + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Change the laws of your federation.");
			player.sendMessage(ChatColor.GOLD + "/gov manage" + ChatColor.GOLD + " orders");
			player.sendMessage(" " + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Change the executive orders in your federation.");
			player.sendMessage(ChatColor.GOLD + "/gov manage" + ChatColor.GOLD + " ideology");
			player.sendMessage(" " + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Change your federation's ideology.");
			player.sendMessage(ChatColor.GOLD + "/gov manage" + ChatColor.GOLD + " invite" + ChatColor.GRAY + " [player]");
			player.sendMessage(" " + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Add a new member of your federation.");
			player.sendMessage(ChatColor.GOLD + "/gov manage" + ChatColor.GOLD + " kick" + ChatColor.GRAY + " [player]");
			player.sendMessage(" " + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Remove a member of your federation.");
			player.sendMessage(ChatColor.GOLD + "/gov manage" + ChatColor.GOLD + " ally/neutral" + ChatColor.GRAY + " [federation]");
			player.sendMessage(" " + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Set another federation as an ally.");
			player.sendMessage(ChatColor.GOLD + "/gov manage" + ChatColor.GOLD + " disband");
			player.sendMessage(" " + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Disband your federation.");
			player.sendMessage("");
			player.sendMessage(ChatUtils.divider);
			return;
		}
		
		if (args[1].equalsIgnoreCase("create")) {
			CreateCommand.create(player, args);
			return;
		}
		
		if (args[1].equalsIgnoreCase("leave")) {
			LeaveCommand.leave(player, args);
			return;
		}
		
		if (CheckFed.getPlayerFed(player).equals("None!")) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "You are not in a federation!");
			return;
		}
		
		String fed = CheckFed.getPlayerFed(player);
		
		if (!CheckFed.getChief(fed).contains(player.getUniqueId().toString()) ) {
			
			if (CheckFed.getDeputies(fed) == null) {
				player.sendMessage(Main.gamePrefix + ChatColor.RED + "You must be a deputy to do this.");
				return;
			}
			
			if (CheckFed.getDeputies(fed).contains(player.getUniqueId().toString())) {
				player.sendMessage(Main.gamePrefix + ChatColor.RED + "You must be a deputy to do this.");
				return;				
			}
			
		}
		
		if (args[1].equalsIgnoreCase("invite")) {
			InviteCommand.invite(player, args);
			return;
		}
		
		if (args[1].equalsIgnoreCase("kick")) {
			kick(player, args);
			return;
		}
				
		if (args[1].equalsIgnoreCase("ideology")) {
			IdeologyCommand.ideology(player, args);
			return;
		}
		
		if (args[1].equalsIgnoreCase("mandates")) {
			MandateCommand.mandateGui(player);
			return;
		}
		
		if (args[1].equalsIgnoreCase("disband")) {
			DisbandCommand.disband(player, args);
			return;
		}
		
		player.sendMessage(syntaxError);
		
	}
	
	public static void kick(Player player, String[] args) {
		
		String fed = CheckFed.getPlayerFed(player);
		String playerToKick = args[2];
		
		if ( !(CheckFed.getAllPlayersInFed().contains(playerToKick))) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "This player is not in your federation.");
			return;
		}
		
		CheckFed.removeMember(playerToKick, fed);
		
		ArrayList<Player> online = CheckFed.getOnlinePlayersInFed(fed);
		
		for (Player fedPlayer : online) fedPlayer.sendMessage(Main.gamePrefix + ChatColor.DARK_AQUA + playerToKick + ChatColor.GRAY + " has left the federation.");
		return;
	}
}
