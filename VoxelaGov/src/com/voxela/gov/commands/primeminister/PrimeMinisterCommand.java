package com.voxela.gov.commands.primeminister;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.voxela.gov.Main;
import com.voxela.gov.utils.ChatUtils;

import net.md_5.bungee.api.ChatColor;

public class PrimeMinisterCommand implements CommandExecutor {
	
	Main plugin;

	public PrimeMinisterCommand(Main passedPlugin) {

		this.plugin = passedPlugin;

	}
	
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(Main.consolePrefix + ChatColor.RED
					+ "Commands cannot be typed through console.");
			return true;
		}
		
		Player player = (Player) sender;
		
		String syntaxError = Main.gamePrefix + ChatColor.RED + "Incorrect command usage. Type " + ChatColor.GOLD + "/pm" + ChatColor.RED + " for help.";
		
		if (cmd.getName().equalsIgnoreCase("pm")) {
			
//			if (player is not PM) {
//				
//				player.sendMessage(Main.gamePrefix + ChatColor.RED + "You are not the Prime Minister.");
//				player.sendMessage(Main.gamePrefix + ChatColor.DARK_GRAY + "During an election, run for office using " + ChatColor.GRAY + "/gov run.");
//				return true;				
//			}
			
			if (args.length == 0) {
				
				player.sendMessage(ChatUtils.formatTitle("Voxela Government"));
				ChatUtils.sendCenteredMessage(player, ChatColor.GOLD + "Prime Minister Console");
				player.sendMessage("");
				player.sendMessage(ChatColor.GOLD + "/pm laws" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Manage or create laws.");
				player.sendMessage(ChatColor.GOLD + "/pm police" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Manage your police force.");
				player.sendMessage(ChatColor.GOLD + "/pm mandates" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Manage your executive orders.");
				player.sendMessage(ChatColor.GOLD + "/pm resign" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "End your term.");
				player.sendMessage("");
				ChatUtils.sendCenteredMessage(player, ChatColor.GREEN + "Term ends: ");
				player.sendMessage(ChatUtils.divider);
				
				
			}
			
			if (args.length > 0) {

				if (args[0].equalsIgnoreCase("laws")) {
					
					return true;
					
				}

				if (args[0].equalsIgnoreCase("police")) {
					
					return true;
					
				}
				
				if (args[0].equalsIgnoreCase("order")) {
					
					return true;
					
				}
				
				if (args[0].equalsIgnoreCase("resign")) {
					
					return true;
					
				}
				
				player.sendMessage(syntaxError);
				
			}			
		} return true;
    }
}
