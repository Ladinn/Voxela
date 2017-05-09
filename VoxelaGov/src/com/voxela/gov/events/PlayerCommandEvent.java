package com.voxela.gov.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.voxela.gov.Main;
import com.voxela.gov.checker.CheckMandate;
import com.voxela.gov.checker.CheckProvince;

import net.md_5.bungee.api.ChatColor;

public class PlayerCommandEvent implements Listener {

	@EventHandler
	public void onPlayerCommandEvent(PlayerCommandPreprocessEvent event) {

		Player player = event.getPlayer();
		String fed = CheckProvince.getFed(CheckProvince.checkProvince(player)[1].toLowerCase());
		String message = event.getMessage().toLowerCase().substring(1);

		if (!player.hasPermission("voxela.gov.bypass")) {

			//Transportation Subsidy
			if ( (message.startsWith("tpa") || message.startsWith("etpa") || message.startsWith("spawn") || message.startsWith("espawn") || message.startsWith("warp") || message.startsWith("ewarp")) 
					&& !(CheckMandate.isMandateEnabled(fed, "Transportation Subsidy"))) {

				event.setCancelled(true);
				player.sendMessage(Main.gamePrefix + ChatColor.RED + "The federation that governs the province you are in has not enabled the "
						+ ChatColor.GOLD + "Transportation Subsidy" + ChatColor.RED + " mandate.");
				player.sendMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.GRAY + "For more info, type "
						+ ChatColor.DARK_AQUA + "/gov help mandates.");
			}
			
			//Department of Housing
			if ( (message.startsWith("home") || message.startsWith("ehome") || message.startsWith("sethome") || message.startsWith("esethome") ) 
					&& !(CheckMandate.isMandateEnabled(fed, "Department of Housing"))) {

				event.setCancelled(true);
				player.sendMessage(Main.gamePrefix + ChatColor.RED + "The federation that governs the province you are in has not enabled the "
						+ ChatColor.GOLD + "Department of Housing" + ChatColor.RED + " mandate.");
				player.sendMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.GRAY + "For more info, type "
						+ ChatColor.DARK_AQUA + "/gov help mandates.");
			}

		}

	}

}
