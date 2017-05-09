package com.voxela.gov.events.war;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.connorlinfoot.titleapi.TitleAPI;
import com.mewin.WGRegionEvents.events.RegionEnterEvent;
import com.voxela.gov.Main;
import com.voxela.gov.checker.CheckFed;
import com.voxela.gov.checker.CheckProvince;
import com.voxela.gov.players.CombatantPlayer;
import com.voxela.gov.utils.TimeManager.WarTime;

import net.md_5.bungee.api.ChatColor;


public class WarEvents implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEnter (RegionEnterEvent e) {		
		
		if (e.getRegion().getId().startsWith("war_")) {
			
			if (!(War.isWarring())) {
				e.getPlayer().sendMessage(Main.gamePrefix + WarTime.warCountdown());
				return;
			}	
			
			String region = e.getRegion().getId();
			String[] regionSplit = region.split("_");
			String province = CheckProvince.stringToProvince(regionSplit[1]);
			String objectiveType = regionSplit[2];
			int objectiveNum = Integer.parseInt(regionSplit[3]);
			String objective = regionSplit[1] + objectiveType + objectiveNum;
			String objectiveNiceName = ChatColor.GOLD + objectiveType.substring(0, 1).toUpperCase() + objectiveType.substring(1) + " " + objectiveNum + ChatColor.GRAY + " in " + ChatColor.GOLD + province; 
			
			String fed = CheckFed.getPlayerFed(e.getPlayer());
			String objectiveController = WarPoints.getObjectiveController(e.getRegion().getId());
			
			if (fed == "None!") {
				e.getPlayer().sendMessage(Main.gamePrefix + ChatColor.RED + "You are not in a federation!");	
				return;
			}
							
			if (!(objectiveController == null)) {
				
				ArrayList<Player> objectiveControllerPlayersInFed = CheckFed.getOnlinePlayersInFed(objectiveController);
				
				if (objectiveController.equals(fed)) {
					e.getPlayer().sendMessage(Main.gamePrefix + ChatColor.GREEN + "Your federation contols this objective.");
					return;					
				}
								
				for (Player player : objectiveControllerPlayersInFed) {
					
					
					String title = ChatColor.RED + "Under Attack!";
					String subtitle = ChatColor.GRAY + "Objective: " + objectiveNiceName;
										
					TitleAPI.sendFullTitle(player, 10, 150, 10, title, subtitle);
					
					Location loc = player.getLocation();
					player.playSound(loc, Sound.AMBIENT_CAVE, 2.0F, 1.0F);
					
				}
				
			}
			
			ArrayList<Player> onlineMinusPlayer = CheckFed.getOnlinePlayersInFed(fed);
			onlineMinusPlayer.remove(e.getPlayer());
			
			for (Player player : onlineMinusPlayer) {
				Location loc = player.getLocation();
				if (Main.getWorldGuard().getRegionManager(player.getWorld()).getApplicableRegions(loc).getRegions().contains(e.getRegion())) return;
			}
			
			if (objectiveType.equalsIgnoreCase("outpost")) {
				if ((WarPoints.checkCamps(fed, province)) == false) {
					e.getPlayer().sendMessage(Main.gamePrefix + ChatColor.RED + "You do not control the camps in this province!");
					return;
				}
			}
			
			if (objectiveType.equalsIgnoreCase("stronghold")) {
				if ( (WarPoints.checkOutposts(fed, province) == false) || (WarPoints.checkCamps(fed, province) == false) ) {
					e.getPlayer().sendMessage(Main.gamePrefix + ChatColor.RED + "You do not control the outposts in this province!");
					return;
				}
			}
			
			if (WarPoints.getObjectiveController(objective) == fed) return;
			if (CheckProvince.getFed(CheckProvince.stringToDataFile(province)) == fed) return;
			
			WarRunnable.runWarClaim(e.getRegion(), fed, objective, objectiveNiceName);
		}
	}
	
	@EventHandler
	public void onDamageEvent (EntityDamageByEntityEvent e) {
				
		if (!(e.getDamager() instanceof Player)) return;
		
		if (!(e.getEntity() instanceof Player)) return;
		
		Player player = Bukkit.getServer().getPlayer(e.getDamager().getName());
				
		if (!(War.isWarring())) return;
		
		if (CombatantPlayer.isCombatant(player.getName())) return;
		
		player.sendMessage(Main.gamePrefix + ChatColor.RED + "Warning:");
		player.sendMessage(ChatColor.DARK_GRAY + "  -" + ChatColor.GRAY + ChatColor.ITALIC + " Killing a player during a war will mark you as a combatant!");
		
	}
	
	@EventHandler
	public void onPlayerKill (PlayerDeathEvent e) {
		
		if (!(e.getEntity().getKiller() instanceof Player)) return;
		
		if (!(War.isWarring())) return;
		
		//Player killed = e.getEntity().getPlayer();
		Player killer = e.getEntity().getKiller();
		
		if (!(CombatantPlayer.isCombatant(killer.getName()))) {
			
			CombatantPlayer.setCombatant(killer.getName());
			killer.sendMessage(Main.gamePrefix + ChatColor.RED + ChatColor.BOLD + "You are now a combatant!");
		}		
		
	}	
}
