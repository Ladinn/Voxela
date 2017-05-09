package com.voxela.gov.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.voxela.gov.Main;
import com.voxela.gov.handlers.InterfaceHandler;
import com.voxela.gov.utils.TimeManager.PrimeMinister.VoteTime;

import net.md_5.bungee.api.ChatColor;

public class VoteCommand implements CommandExecutor {

	Main plugin;

	public VoteCommand(Main passedPlugin) {

		this.plugin = passedPlugin;

	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(Main.consolePrefix + ChatColor.RED + "Commands cannot be typed through console.");
			return true;
		}

		Player player = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("vote")) {

			if (VoteTime.canVote) {

				player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Opening voting console...");
				InterfaceHandler.vote();
				return true;
			}

			player.sendMessage(Main.gamePrefix + ChatColor.RED + "You cannot vote at this time.");
			return true;

		}
		
		return false;
	}
}
