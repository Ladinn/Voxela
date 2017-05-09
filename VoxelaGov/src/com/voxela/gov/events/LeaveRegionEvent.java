package com.voxela.gov.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.mewin.WGRegionEvents.events.RegionLeaveEvent;
import com.voxela.gov.Main;
import com.voxela.gov.players.primeminister.PrimeMinisterPlayer;

import net.md_5.bungee.api.ChatColor;

public class LeaveRegionEvent implements Listener {

	@EventHandler
	public void onRegionLeave(RegionLeaveEvent e) {

		if (!(PrimeMinisterPlayer.playerIsPrimeMinister(e.getPlayer()))) return;
		
		if (e.getRegion().getId().equals("primeminister") && e.isCancellable()) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(Main.gamePrefix + ChatColor.RED + "You may not leave the Prime Minister's mansion.");
		}
	}

}
