package com.voxela.gov.utils.TimeManager;

import java.util.Calendar;

import org.bukkit.scheduler.BukkitRunnable;

import com.voxela.gov.Main;
import com.voxela.gov.events.war.War;

import net.md_5.bungee.api.ChatColor;

public class WarTime {
	
	public static Calendar nextWar() {
		
		Calendar nextWar = Calendar.getInstance();
		
		nextWar.set(Calendar.HOUR_OF_DAY, 22);
		nextWar.set(Calendar.MINUTE, 0);
		
		return nextWar;
	}
	
	public static Calendar warEnd() {
		
		Calendar warEnd = Calendar.getInstance();
		
		warEnd.add(Calendar.DAY_OF_YEAR, 1);
		warEnd.set(Calendar.HOUR_OF_DAY, 0);
		warEnd.set(Calendar.MINUTE, 0);
		
		return warEnd;
	}
		
	public static String warCountdown() {
		
		Calendar now = Calendar.getInstance();
		
		if (War.isWarring()) {
			
			Calendar warEnd = warEnd();
			
			long millisLeft = warEnd.getTimeInMillis() - now.getTimeInMillis();
			long hoursLeft = millisLeft / (60 * 60 * 1000);
			long minutesLeft = (millisLeft % (60 * 60 * 1000)) / (60 * 1000);
			
			String timeLeft = ChatColor.RED + "" + ChatColor.ITALIC + "War is on! " + ChatColor.GREEN + "Ceasefire: " + ChatColor.GRAY + Long.toString(hoursLeft) + " Hours and " + Long.toString(minutesLeft) + " Minutes";
			return timeLeft;
			
		}
		
		Calendar nextWar = nextWar();
		
		long millisLeft = nextWar.getTimeInMillis() - now.getTimeInMillis();
		long hoursLeft = millisLeft / (60 * 60 * 1000);
		long minutesLeft = (millisLeft % (60 * 60 * 1000)) / (60 * 1000);
		
		String timeLeft = ChatColor.RED + "Next war: " + ChatColor.GRAY + Long.toString(hoursLeft) + " Hours and " + Long.toString(minutesLeft) + " Minutes " + ChatColor.DARK_GRAY + "(10 PM EST)";
		return timeLeft;
	}
	
	public static void warCheck() {
		
		new BukkitRunnable() {
            public void run() {
            	            	
            	Calendar now = Calendar.getInstance();
            	
                if (War.isWarring()) {
                	if ( now.after(warEnd())) War.endWar();
                	return;
                }
                
                if ( !(War.isWarring()) ) {
                	if ( now.after(nextWar())) War.beginWar();
                	return;
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, 600L);
	}	
}