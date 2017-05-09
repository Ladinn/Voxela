package com.voxela.gov.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.voxela.gov.Main;
import com.voxela.gov.players.primeminister.CandidatePlayer;
import com.voxela.gov.utils.TimeManager.PrimeMinister.VoteTime;

import net.md_5.bungee.api.ChatColor;

public class RunCommand implements CommandExecutor {

	Main plugin;

	public RunCommand(Main passedPlugin) {

		this.plugin = passedPlugin;

	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(Main.consolePrefix + ChatColor.RED + "Commands cannot be typed through console.");
			return true;
		}

		int runFee = Main.getInstance().getConfig().getInt("runfee");

		Player player = (Player) sender;

		String syntaxError = Main.gamePrefix + ChatColor.RED + "Incorrect command usage.";

		if (cmd.getName().equalsIgnoreCase("run")) {
			
			if (CandidatePlayer.getCandidates().contains(player)) {
				player.sendMessage(Main.gamePrefix + ChatColor.RED + "You are already running for Prime Minister!");
				return true;
			}
			
			if (VoteTime.canRun) {
			
				if (args.length > 0) {

					if (args[0].equalsIgnoreCase("confirm")) {

						if (Main.getEconomy().getBalance(player) >= runFee) {
							Main.getEconomy().withdrawPlayer(player, runFee);
							CandidatePlayer.addCandidate(player);
							return true;
						}
						player.sendMessage(Main.gamePrefix + ChatColor.RED + "Insufficient balance to pay the filing fee!");
						return true;
					}

					player.sendMessage(syntaxError);
					return true;
				}
				
				player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Running for Prime Minister will cost "
						+ ChatColor.GOLD + "$" + runFee + ChatColor.GREEN + " in filing fees.");
				player.sendMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.GRAY + "Type " + ChatColor.DARK_AQUA
						+ "/run confirm" + ChatColor.GRAY + " if you'd like to continue.");
				return true;
				
			}

			player.sendMessage(Main.gamePrefix + ChatColor.RED + "You cannot run for Prime Minister at this time.");
			return true;

		}
		return false;
	}
}
