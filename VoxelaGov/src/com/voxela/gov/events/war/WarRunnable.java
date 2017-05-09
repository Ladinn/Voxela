package com.voxela.gov.events.war;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.gov.Main;
import com.voxela.gov.checker.CheckFed;
import com.voxela.gov.handlers.BarHandler;
import com.voxela.gov.players.CombatantPlayer;

import net.md_5.bungee.api.ChatColor;

public class WarRunnable {

	static Main instance = Main.getInstance();

	private static BarHandler bar = new BarHandler(" ", BarColor.RED, BarStyle.SEGMENTED_6);
	private static int timer = 60;
	private static double barTime = 0;

	public static void runWarClaim(ProtectedRegion region, String fed, String objective, String objectiveNiceName) {

		ArrayList<Player> online = CheckFed.getOnlinePlayersInFed(fed);

		for (Player player : online) {
			player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Securing objective: " + ChatColor.GOLD
					+ objectiveNiceName + ".");
			player.sendMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.GRAY + "60 seconds remaining.");
		}
		
		new BukkitRunnable() {
			public void run() {

				if (barTime <= 1.0)
					barTime += 0.01666666667;

				if (timer >= 1) {

					timer--;

					for (Player player : online) {
						
						String text = ChatColor.GOLD + objectiveNiceName + ChatColor.GRAY + ": " + getTime(timer);

						bar.setText(text);
						bar.setProgress(barTime);
						bar.addPlayer(player);
					}
				}

				if (timer == 50) {
					for (Player player : online) {
						player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Securing objective: " + ChatColor.GOLD
								+ objectiveNiceName + ".");
						player.sendMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.GRAY + "50 seconds remaining.");
					}
				}

				if (timer == 40) {
					for (Player player : online) {
						player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Securing objective: " + ChatColor.GOLD
								+ objectiveNiceName + ".");
						player.sendMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.GRAY + "40 seconds remaining.");
					}
				}

				if (timer == 30) {
					for (Player player : online) {
						player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Securing objective: " + ChatColor.GOLD
								+ objectiveNiceName + ".");
						player.sendMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.GRAY + "30 seconds remaining.");
					}
				}

				if (timer == 20) {
					for (Player player : online) {
						player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Securing objective: " + ChatColor.GOLD
								+ objectiveNiceName + ".");
						player.sendMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.GRAY + "20 seconds remaining.");
					}
				}

				if (timer == 10) {
					for (Player player : online) {
						player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Securing objective: " + ChatColor.GOLD
								+ objectiveNiceName + ".");
						player.sendMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.GRAY + "10 seconds remaining.");
					}
				}

				if (timer == 0) {

					timer = 60;
					barTime = 0;

					this.cancel();

					Bukkit.broadcastMessage(
							Main.gamePrefix + ChatColor.RED + "WAR: " + ChatColor.GREEN + "Objective secured.");
					Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.DARK_AQUA + "Federation: "
							+ ChatColor.GOLD + CheckFed.getNiceName(fed));
					Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.DARK_AQUA + "Objective: "
							+ ChatColor.GOLD + objectiveNiceName);

					for (Player player : online) {

						Location loc = player.getLocation();
						player.playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 2.0F);
					}

					WarPoints.changeObjectiveControl(region.getId(), fed);
					bar.resetAllPlayersBars();
					WarPoints.checkProvinces(fed);
				}

				if (!(runCheck(region, fed, objectiveNiceName))) {

					this.cancel();

					for (Player player : online) {
						player.sendMessage(Main.gamePrefix + ChatColor.RED + "Failed to secure objective:");
						player.sendMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.GRAY + objectiveNiceName);
					}

					timer = 60;
					barTime = 0;
					bar.resetAllPlayersBars();
				}

			}
		}.runTaskTimer(instance, 0L, 20L);
	}

	private static String getTime(int timer) {

		String time = String.format("%d seconds.", TimeUnit.SECONDS.toSeconds(timer));

		return time;
	}

	private static boolean runCheck(ProtectedRegion region, String fed, String objectiveNiceName) {

		if (!(War.isWarring()))
			return false;

		ArrayList<Player> online = CheckFed.getOnlinePlayersInFed(fed);

		for (Player player : Bukkit.getOnlinePlayers()) {

			if (CheckFed.getPlayerFed(player).equalsIgnoreCase(fed))
				continue;

			if (CombatantPlayer.isCombatant(player.getName())) {

				Location loc = player.getLocation();
				if (Main.getWorldGuard().getRegionManager(player.getWorld()).getApplicableRegions(loc).getRegions()
						.contains(region)) {

					System.out.print(player.getName());

					for (Player fedPlayer : online) {
						fedPlayer.sendMessage(Main.gamePrefix + ChatColor.RED + "Enemy combatant is in the objective!");
					}

					return false;
				}

			}

		}

		for (Player player : online) {

			if (player.isDead())
				continue;

			Location loc = player.getLocation();
			if (Main.getWorldGuard().getRegionManager(player.getWorld()).getApplicableRegions(loc).getRegions()
					.contains(region))
				return true;

		}

		return false;

	}
}
