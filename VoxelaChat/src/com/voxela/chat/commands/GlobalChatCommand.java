package com.voxela.chat.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.voxela.chat.Main;
import com.voxela.chat.formatter.Format;

import net.md_5.bungee.api.ChatColor;

public class GlobalChatCommand implements CommandExecutor {

	Main plugin;

	public GlobalChatCommand(Main passedPlugin) {

		this.plugin = passedPlugin;

	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(Main.consolePrefix + ChatColor.RED + "Commands cannot be typed through console.");
			return true;
		}
		
		Player player = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("g")) {
			
			if (args.length > 0) {
				
				String message = "";
				
	            for(int i = 0; i != args.length; i++) message += args[i] + " ";
				
	            String format = Format.format(player, false) + message;
	            
				Bukkit.broadcastMessage(format);
				return true;
				
			}
			
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "Speak in global chat with " + ChatColor.GOLD + "/g [message].");
			return true;
		}
		return false;
	}

}
