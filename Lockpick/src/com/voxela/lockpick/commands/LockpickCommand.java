package com.voxela.lockpick.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.voxela.lockpick.Main;

import net.md_5.bungee.api.ChatColor;

public class LockpickCommand implements CommandExecutor {
	
	Main plugin;

	public LockpickCommand(Main passedPlugin) {

		this.plugin = passedPlugin;

	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
				
		if (!(sender instanceof Player)) {
			sender.sendMessage(Main.consolePrefix + "You must be a player to run this command.");
			return true;
		}
		
		Player player = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("lockpick")) {
			
			player.sendMessage(Main.gamePrefix + ChatColor.GOLD + "Lockpick" + ChatColor.DARK_GRAY + " | " + ChatColor.GRAY + "Version 1.0");
			player.sendMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.GRAY + "Crafting recipe: " + ChatColor.DARK_AQUA + "http://voxe.la/lockpick");
			return true;
		}
		
		return false;
		
	}

}
