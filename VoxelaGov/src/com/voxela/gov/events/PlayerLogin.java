package com.voxela.gov.events;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.voxela.gov.Main;
import com.voxela.gov.checker.CheckFed;
import com.voxela.gov.effects.SetEffect;

import net.md_5.bungee.api.ChatColor;

public class PlayerLogin implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void playerLogin(PlayerLoginEvent e) {

		Player player = e.getPlayer();
		
		new BukkitRunnable() {
			public void run() {
				
				if (CheckFed.getPlayerFed(player).equals("None!")) {
					SetEffect.unsetPlayer(player);
					return;
				}

				String fed = CheckFed.getPlayerFed(player);

				SetEffect.set(fed);

				ArrayList<Player> online = CheckFed.getOnlinePlayersInFed(fed);

				for (Player fedPlayer : online) {
					fedPlayer.sendMessage(ChatColor.DARK_GRAY + " [" + ChatColor.GREEN + "+"
							+ ChatColor.DARK_GRAY + "]" + ChatColor.AQUA + " Federation member " + ChatColor.LIGHT_PURPLE
							+ player.getName() + ChatColor.AQUA + " joined the server.");
				}
				
				
			}
		}.runTaskLater(Main.getInstance(), 20L);
	}

}
