package com.voxela.gov.commands.government.sub;

import org.bukkit.entity.Player;

import com.voxela.gov.players.CombatantPlayer;
import com.voxela.gov.utils.ChatUtils;
import com.voxela.gov.utils.TimeManager.WarTime;

import net.md_5.bungee.api.ChatColor;

public class WarCommand {
	
	public static void war(Player player, String[] args) {
		
		String warCountdown = WarTime.warCountdown();
		
		player.sendMessage(ChatUtils.formatTitle("Voxela Government"));
		ChatUtils.sendCenteredMessage(player, warCountdown);
		player.sendMessage("");
		player.sendMessage(ChatColor.DARK_AQUA + "Leading Federation:");
		player.sendMessage(ChatColor.DARK_AQUA + "Provinces Seized: ");
		player.sendMessage(ChatColor.DARK_AQUA + "Combatants Killed: ");
		player.sendMessage(ChatUtils.divider);
		if (CombatantPlayer.isCombatant(player.getName())) {
			ChatUtils.sendCenteredMessage(player, ChatColor.RED + "You are marked as a combatant!");
			ChatUtils.sendCenteredMessage(player, ChatColor.GRAY + "Warring federations can kill you.");
		} else {
			ChatUtils.sendCenteredMessage(player, ChatColor.GREEN + "You are not marked as a combatant.");
			ChatUtils.sendCenteredMessage(player, ChatColor.GRAY + "Warring federations can't kill you.");
		}
		player.sendMessage(ChatUtils.divider);

	}
	
	

}
