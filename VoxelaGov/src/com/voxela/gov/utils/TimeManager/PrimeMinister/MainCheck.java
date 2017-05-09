package com.voxela.gov.utils.TimeManager.PrimeMinister;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.voxela.gov.Main;
import com.voxela.gov.players.primeminister.PrimeMinisterPlayer;

import net.md_5.bungee.api.ChatColor;

public class MainCheck {
	
	private static int pmTime = 3600;
	
	public static void check() {
		
		new BukkitRunnable() {
			
			public void run() {
				
				if (!(PrimeMinisterPlayer.electionsEnabled())) return;
								
				if (!(VoteTime.activeVote) && !(PrimeMinisterPlayer.isPrimeMinister())) {
					VoteTime.run();
					return;
				}
				
				if (PrimeMinisterPlayer.isPrimeMinister()) {
					
					pmTime--;
					
					String pmName = PrimeMinisterPlayer.getPrimeMinister().getName();
					
					if (pmTime == 3000) Bukkit.broadcastMessage(Main.gamePrefix + ChatColor.GOLD + "Prime Minister " + pmName + "'s" + ChatColor.GRAY + " term ends in 50 minutes.");
					
					if (pmTime == 2400) Bukkit.broadcastMessage(Main.gamePrefix + ChatColor.GOLD + "Prime Minister " + pmName + "'s" + ChatColor.GRAY + " term ends in 40 minutes.");
					
					if (pmTime == 1800) Bukkit.broadcastMessage(Main.gamePrefix + ChatColor.GOLD + "Prime Minister " + pmName + "'s" + ChatColor.GRAY + " term ends in 30 minutes.");
					
					if (pmTime == 1200) Bukkit.broadcastMessage(Main.gamePrefix + ChatColor.GOLD + "Prime Minister " + pmName + "'s" + ChatColor.GRAY + " term ends in 20 minutes.");
					
					if (pmTime == 600) Bukkit.broadcastMessage(Main.gamePrefix + ChatColor.GOLD + "Prime Minister " + pmName + "'s" + ChatColor.GRAY + " term ends in 10 minutes.");
					
				}
				
				if (pmTime == 0) {
					
					PrimeMinisterPlayer.removePrimeMinister();
					VoteTime.voteTimer();
					pmTime = 3600;
					
				}
			}			
		}.runTaskTimer(Main.getInstance(), 0L, 20L);		
	}
}
