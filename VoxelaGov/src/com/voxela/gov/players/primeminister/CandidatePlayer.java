package com.voxela.gov.players.primeminister;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.voxela.gov.Main;

import net.md_5.bungee.api.ChatColor;

public class CandidatePlayer {
	
	public static HashMap<Player, Integer> candidateMap = new HashMap<Player, Integer>();
	
	public static Set<Player> getCandidates() { return candidateMap.keySet(); }

	public static void addCandidate(Player player) { 
		Bukkit.broadcastMessage(Main.gamePrefix + ChatColor.GOLD + player.getName() + ChatColor.GREEN + " is running for Prime Minister!");
		candidateMap.put(player, 0); 
	}
	
	public static void resetCandidates() { candidateMap.clear(); }
	
	public static void addVote(Player player) { 
		
		Integer votes = candidateMap.get(player);
		int newVotes = new Integer(votes.intValue() + 1);
		candidateMap.put(player, newVotes);
	}
	
	public static int getVotes(Player player) { return candidateMap.get(player); }
	
	public static Player getWinner() {
		
		Integer largestVoteCount = 0;
		Player winner = null;
		
		for (Player player : candidateMap.keySet()) {
			
			Integer votes = candidateMap.get(player);
			if (votes > largestVoteCount) {
				largestVoteCount = votes;
				winner = player;
			}
		}

		System.out.print(Main.consolePrefix + "Election winner: " + winner.getName());
        return winner;		
	}
	
}
