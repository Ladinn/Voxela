package com.voxela.lockpick.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;

import com.voxela.lockpick.Main;
import com.voxela.lockpick.items.LockpickItem;
import com.voxela.lockpick.items.MasterKeyItem;

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
			
			if (args.length > 0) {
				
				if (args[0].equalsIgnoreCase("reload")) {
					
					if (!(player.isOp()) || !(player.hasPermission(new Permission("voxela.lockpick.admin")))) {
						player.sendMessage(Main.gamePrefix + ChatColor.RED + "You do not have permission to do this.");
						return true;
					}
					
					Main.getInstance().reloadConfig();
					player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Configuration reloaded.");
					return true;
				}
				
				if (args[0].equalsIgnoreCase("get")) {
					
					if (!(player.isOp()) || !(player.hasPermission(new Permission("voxela.lockpick.admin")))) {
						player.sendMessage(Main.gamePrefix + ChatColor.RED + "You do not have permission to do this.");
						return true;
					}
					
					if (args.length > 1) {
						
						if (args[1].equalsIgnoreCase("lockpick")) {
							
							int amount = 1;
							
							if (args.length > 2) amount = Integer.parseInt(args[2]);
							
							ItemStack item = LockpickItem.item;
							item.setAmount(amount);
							
							player.getInventory().addItem(item);
							player.updateInventory();
							player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Giving you " + ChatColor.GOLD + amount + ChatColor.GREEN + " lockpick.");
							return true;
						}
						
						if (args[1].equalsIgnoreCase("master")) {
							
							int amount = 1;
							
							if (args.length > 2) amount = Integer.parseInt(args[2]);
							
							ItemStack item = MasterKeyItem.item;
							item.setAmount(amount);
							
							player.getInventory().addItem(item);
							player.updateInventory();
							player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Giving you " + ChatColor.GOLD + amount + ChatColor.GREEN + " master key.");
							return true;
						}
						
					}
					
					player.sendMessage(Main.gamePrefix + ChatColor.DARK_AQUA + "Usage: " + ChatColor.GRAY + "/lockpick get [lockpick/master]");
					return true;
				}
				
				
			}
			
			player.sendMessage(Main.gamePrefix + ChatColor.GOLD + "Lockpick" + ChatColor.DARK_GRAY + " | " + ChatColor.GRAY + "Version " + Main.version);
			player.sendMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.GRAY + "Crafting recipe: " + ChatColor.DARK_AQUA + "http://voxe.la/lockpick");
			return true;
		}
		
		return false;
		
	}

}
