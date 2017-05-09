package com.voxela.gov.commands.government;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.voxela.gov.Main;
import com.voxela.gov.checker.CheckFed;
import com.voxela.gov.commands.government.sub.InfoCommand;
import com.voxela.gov.commands.government.sub.WarCommand;
import com.voxela.gov.commands.government.sub.manage.InviteCommand;
import com.voxela.gov.commands.government.sub.manage.ManageCommand;
import com.voxela.gov.events.war.War;
import com.voxela.gov.events.war.WarPoints;
import com.voxela.gov.utils.ChatUtils;
import com.voxela.gov.utils.TimeManager.WarTime;

import net.md_5.bungee.api.ChatColor;

public class GovernmentCommand implements CommandExecutor {
	
	Main plugin;

	public GovernmentCommand(Main passedPlugin) {

		this.plugin = passedPlugin;

	}
	
    String noPerm = Main.gamePrefix + ChatColor.RED + "You do not have permission to use this command!";
	String syntaxError = Main.gamePrefix + ChatColor.RED + "Incorrect command usage. Type " + ChatColor.GOLD + "/gov" + ChatColor.RED + " for help.";
	
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(Main.consolePrefix + ChatColor.RED
					+ "Commands cannot be typed through console.");
			return true;
		}
		
		Player player = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("gov")) {
			
			if (args.length == 0) {
				
				String warCountdown = WarTime.warCountdown();
				
				player.sendMessage(ChatUtils.formatTitle("Voxela Government"));
				player.sendMessage("");
				player.sendMessage(ChatColor.GOLD + "/gov info" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Get information on current governments.");
				//player.sendMessage(ChatColor.GOLD + "/gov me" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Shows your alliances and federations.");
				player.sendMessage(ChatColor.GOLD + "/gov manage" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Create, manage, or leave a federation.");
				player.sendMessage(ChatColor.GOLD + "/gov war" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Get information on the state of the war.");
				player.sendMessage(ChatColor.GOLD + "/gov help" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Read about how the government works.");
				player.sendMessage("");
				ChatUtils.sendCenteredMessage(player, warCountdown);
				player.sendMessage(ChatUtils.divider);				
				
			}
			
			if (args.length > 0) {

				if (args[0].equalsIgnoreCase("info")) {
					
					InfoCommand.info(player, args);
					return true;
					
				}
				
				// DEBUG, remove.
				if (args[0].equalsIgnoreCase("startwar")) {
					
					War.beginWar();
					return true;
					
				}

				if (args[0].equalsIgnoreCase("war")) {
					
					WarCommand.war(player, args);
					return true;
					
				}
				
//				if (args[0].equalsIgnoreCase("me")) {
//					
//					return true;
//					
//				}
				
				if (args[0].equalsIgnoreCase("manage")) {
					
					ManageCommand.manage(player, args);
					return true;
					
				}
				
				if (args[0].equalsIgnoreCase("help")) {
					
					
					return true;
					
				}
				
				if (args[0].equalsIgnoreCase("join")) {
					
					if ( InviteCommand.inviteMap.get(player) == null ) {
						player.sendMessage(Main.gamePrefix + ChatColor.RED + "You have not been invited to any federations!");
						return true;
					}
					
					if ( !( CheckFed.getPlayerFed(player).equals("None!") )) {
						player.sendMessage(Main.gamePrefix + ChatColor.RED + "You are already in a federation!");
						return true;
					}
					
					String fed = InviteCommand.inviteMap.get(player);
					CheckFed.addMember(player, fed);
					InviteCommand.inviteMap.remove(player);
					
					ArrayList<Player> online = CheckFed.getOnlinePlayersInFed(fed);
					
					for (Player fedPlayer : online) fedPlayer.sendMessage(Main.gamePrefix + ChatColor.GOLD + player.getName() + ChatColor.GREEN + " has joined the federation!");
					return true;	
				}
				
				if (args[0].equalsIgnoreCase("objective")) {
					
					if (!(player.isOp())) return false;
					
					String region = args[1];
					WarPoints.getObjectiveController(region);
					return true;
				}
				
				player.sendMessage(syntaxError);
				
			}			
		} return true;
    }
}
