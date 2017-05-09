package com.voxela.gov.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.voxela.gov.Main;
import com.voxela.gov.checker.CheckFed;
import com.voxela.gov.checker.CheckMandate;
import com.voxela.gov.effects.SetEffect;
import com.voxela.gov.handlers.InterfaceHandler;
import com.voxela.gov.players.primeminister.CandidatePlayer;

import net.md_5.bungee.api.ChatColor;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        String name = e.getInventory().getName();
        ItemStack item = e.getCurrentItem();
        
        // Prime Minister Vote
        if (name.equalsIgnoreCase(InterfaceHandler.voteName())) {

            e.setCancelled(true);
            
            if (item == null) {
            	player.sendMessage(Main.gamePrefix + ChatColor.RED + "Click on the player you want to vote for!");
            	return;
            }

            for (Player online : Bukkit.getOnlinePlayers()) {

                if (item.getItemMeta().getDisplayName().equalsIgnoreCase(online.getName())) {

                	String votedFor = item.getItemMeta().getDisplayName();
                    CandidatePlayer.addVote(Bukkit.getPlayer(votedFor));
                    
                    System.out.print(Main.consolePrefix + "Player " + player.getName() + " has voted for " + votedFor);
                    
                	int votes = CandidatePlayer.getVotes(Bukkit.getPlayer(votedFor));
                	
                	player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Submitted a vote for " + ChatColor.GOLD + votedFor + ".");
                	player.sendMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.GRAY + "So far, this player has received " + ChatColor.DARK_AQUA + votes + ChatColor.GRAY + " votes.");
                
                    player.closeInventory();                	
                }

            }

        }
        
        // Set ideology
        if (name.equalsIgnoreCase(InterfaceHandler.ideologyName())) {
        	
        	e.setCancelled(true);
        	
            if (item == null) {
            	player.sendMessage(Main.gamePrefix + ChatColor.RED + "Choose your new ideology!");
            	return;
            }
            
            String fed = CheckFed.getPlayerFed(player);
            String ideology = item.getItemMeta().getDisplayName();
            
            CheckFed.setIdeology(fed, ideology);
            
			Bukkit.broadcastMessage(Main.gamePrefix + ChatColor.GOLD + fed + ChatColor.GREEN + " has changed their ideology to " + ChatColor.GOLD + ideology + "!");
			
			SetEffect.unset(fed);
			SetEffect.setIdeology(ideology, fed);
			
            player.closeInventory();
        }
        
        // Set mandate
        if (name.equalsIgnoreCase(InterfaceHandler.mandateName())) {
        	
        	e.setCancelled(true);
        	
            if (item == null) {
            	player.sendMessage(Main.gamePrefix + ChatColor.RED + "Click on a mandate!");
            	return;
            }
            
            String fed = CheckFed.getPlayerFed(player);
            String mandate = item.getItemMeta().getDisplayName();
            
            if (!(CheckMandate.isMandateEnabled(fed, mandate))) {
            	CheckMandate.setMandate(fed, mandate, true);	
                player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Enabled " + ChatColor.GOLD + mandate + ".");
            } else {
            	CheckMandate.setMandate(fed, mandate, false);
                player.sendMessage(Main.gamePrefix + ChatColor.RED + "Disabled " + ChatColor.GOLD + mandate + ".");
            }         
            
            player.closeInventory();

        }

    }

}

