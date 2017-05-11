package com.voxela.password;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.voxela.password.utils.Checker;
import com.voxela.password.utils.Generator;
import com.voxela.password.utils.Message;

import net.md_5.bungee.api.ChatColor;

public class PasswordCommand implements CommandExecutor {

	Main plugin;

	public PasswordCommand(Main passedPlugin) {

		this.plugin = passedPlugin;

	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		String syntaxError = Main.gamePrefix + ChatColor.RED + "Incorrect command usage. Type " + ChatColor.GOLD + "/password.";
		String noPerm = Main.gamePrefix + ChatColor.RED + "You do not have permission to use this command!";
		
		if (!(sender instanceof Player)) {
			
			if (args.length > 1) {
				sender.sendMessage(syntaxError);
				return true;
			}
			
			if (args.length == 1) {
				
				if ( !(Checker.isInteger(args[0])) ) {
					sender.sendMessage(Main.gamePrefix + ChatColor.RED + "Password length must be an integer!");
					return true;
				}
				
				int count = Integer.parseInt(args[0]);
				
				if (count > 1000) {
					sender.sendMessage(Main.gamePrefix + ChatColor.RED + "That's a one way ticket to crashing your server, pal.");
					return true;
				}
				
				String pass = Generator.newPassword(count);
				
				sender.sendMessage(Main.gamePrefix + ChatColor.GOLD + "Password: " + ChatColor.DARK_AQUA + pass);
				return true;
			}
			
			sender.sendMessage(Main.gamePrefix + ChatColor.GOLD + "Random Password Generator" + ChatColor.DARK_GRAY + " | " + ChatColor.GRAY + "Version 1.0");
			sender.sendMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.DARK_AQUA + "/password [length]");
			return true;
		}		
		
		Player player = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("password")) {
			
			if (!(player.hasPermission(new Permission("voxela.password"))) ) {
				player.sendMessage(noPerm);
				return true;
			}
			
			if (args.length > 1) {
				player.sendMessage(syntaxError);
				return true;
			}
			
			if (args.length == 1) {
				
				if ( !(Checker.isInteger(args[0])) ) {
					player.sendMessage(Main.gamePrefix + ChatColor.RED + "Password length must be an integer!");
					return true;
				}
				
				int count = Integer.parseInt(args[0]);
				
				if (count > 1000) {
					player.sendMessage(Main.gamePrefix + ChatColor.RED + "That's a one way ticket to crashing your server, pal.");
					return true;
				}
				
				String pass = Generator.newPassword(count);
				
				player.sendMessage(Main.gamePrefix + ChatColor.GOLD + "Password: " + ChatColor.DARK_AQUA + pass);
				Message.newMessage(player, pass);
				return true;
			}
			
			player.sendMessage(Main.gamePrefix + ChatColor.GOLD + "Random Password Generator" + ChatColor.DARK_GRAY + " | " + ChatColor.GRAY + "Version 1.0");
			player.sendMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.DARK_AQUA + "/password [length]");
			return true;			
			
		} return false;
	}

}
