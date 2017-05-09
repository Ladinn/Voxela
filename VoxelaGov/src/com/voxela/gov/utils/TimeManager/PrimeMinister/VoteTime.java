package com.voxela.gov.utils.TimeManager.PrimeMinister;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.voxela.gov.Main;
import com.voxela.gov.handlers.BarHandler;
import com.voxela.gov.handlers.InterfaceHandler;
import com.voxela.gov.players.primeminister.CandidatePlayer;
import com.voxela.gov.players.primeminister.PrimeMinisterPlayer;
import com.voxela.gov.utils.ChatUtils;

import net.md_5.bungee.api.ChatColor;

public class VoteTime {
	
	public static BarHandler bar = new BarHandler(" ", BarColor.BLUE, BarStyle.SOLID);
	
	private static int electionTime = 360;
	private static double barTime = 1.0;
	
	public static boolean canVote = false;
	public static boolean canRun = false;
	public static boolean activeVote = false;
	
	public static void run() { voteTimer();	}
	
	public static void voteTimer() {

		new BukkitRunnable() {
			public void run() {
				
				if (electionTime == 360) {					
					Bukkit.broadcastMessage(ChatUtils.divider);
					ChatUtils.broadcastCenteredMessage(ChatColor.GOLD + "A new election for Prime Minister has begun!");
					ChatUtils.broadcastCenteredMessage(ChatColor.GRAY + "Type /run to become a candidate!");
					Bukkit.broadcastMessage(ChatUtils.divider);		
				}
				
				if (electionTime > 60) {
					
					activeVote = true;
					canRun = true;
				
					for (Player player : Bukkit.getServer().getOnlinePlayers()) {
						
						String text = ChatColor.DARK_AQUA + "Election: " + ChatColor.GRAY + getVoteTime();
						
						bar.setText(text);
						bar.setProgress(barTime);
						bar.addPlayer(player);
					}					
				}
				
				electionTime--;
								
				if (electionTime == 60) {
					
					canRun = false;
										
					if (CandidatePlayer.getCandidates() == null) {
						activeVote = false;
						bar.resetAllPlayersBars();
						this.cancel();
						
					}
					
					canVote = true;
					
					Bukkit.broadcastMessage(ChatUtils.divider);
					ChatUtils.broadcastCenteredMessage(ChatColor.GOLD + "Voting has begun!");
					ChatUtils.broadcastCenteredMessage(ChatColor.GRAY + "Choose your candidate in the popup screen, or type /vote.");
					Bukkit.broadcastMessage(ChatUtils.divider);
					
					bar.setText(ChatColor.WHITE + "Vote for Prime Minister with /vote!");
					bar.setColor(BarColor.WHITE);
					
					InterfaceHandler.vote();
				}
								
				if (electionTime <= 0) {
					
					activeVote = false;
					canVote = false;
					electionTime = 360;
					
					Player winner = CandidatePlayer.getWinner();

					if (winner == null) {
						bar.resetAllPlayersBars();
						CandidatePlayer.resetCandidates();
						this.cancel();
					}
					
					CandidatePlayer.resetCandidates();
					PrimeMinisterPlayer.setPrimeMinister(winner);
					bar.resetAllPlayersBars();
					this.cancel();					
				}
				
				if (barTime < 0) barTime = 0.0;
				
                if (barTime > 0.003) barTime -= 0.00333333333;
				
			}
		}.runTaskTimer(Main.getInstance(), 0L, 20L);
		
	}
	
	public static void removeAllBars() { bar.resetAllPlayersBars();	}
	
    private static String getVoteTime() {
    	
    	String time = String.format("%d minutes, %d seconds.",
    			TimeUnit.SECONDS.toMinutes(electionTime - 60),
    			TimeUnit.SECONDS.toSeconds(electionTime - 60) -
    			TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(electionTime - 60))   			
    	);
    	
    	return time;
    }
}
