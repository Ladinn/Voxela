package com.voxela.plots.timeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.plots.Main;
import com.voxela.plots.rent.Unrent;
import com.voxela.plots.utils.FileManager;

import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("deprecation")
public class RentCheck {
	
	public static int checkEveryTenMins() {
		
		int mins = 10;
		
		Calendar cal = Calendar.getInstance();

	    long now = cal.getTimeInMillis();

	    if(cal.get(Calendar.MINUTE) >= mins)
	        cal.add(Calendar.HOUR, 1);
	 
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, mins);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	 	 
	    long offset = cal.getTimeInMillis() - now;
	    long ticks = offset / 50L;
	    
	    return Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
		   
	    	public void run() {
		    	checkRenters();
	    	}
	    	
	    }, ticks, 12000L);
	}
	
	private static void checkRenters() {
		
		System.out.print(Main.consolePrefix + "Automatically checking renters...");
		
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");

		String dateCurrentString = TimeManager.timeNow();
		Date dateCurrentDate = null;
		try {
			dateCurrentDate = dateFormat.parse(dateCurrentString);
		} catch (ParseException e1) {
			System.out.print(Main.consolePrefix + "Error checking rent date! Failed formatting current date.");
			e1.printStackTrace();
		}
		
		Date datePlusDay = null;
		try {
			datePlusDay = dateFormat.parse(TimeManager.timePlusDay());
		} catch (ParseException e1) {
			System.out.print(Main.consolePrefix + "Error checking rent date! Failed formatting current date.");
			e1.printStackTrace();
		}
		
		for (String key : FileManager.dataFileCfg.getConfigurationSection("regions").getKeys(false)) {
			
			String regionString1 = FileManager.dataFileCfg.getString("regions." + key);
			String regionString2 = regionString1.replace("MemorySection[path='regions.", "");
			String regionString = regionString2.replace("', root='YamlConfiguration']", "");
			
			String dateString = FileManager.dataFileCfg.getString("regions." + key + ".rentuntil");
			String renterString = FileManager.dataFileCfg.getString("regions." + key + ".renter");
			
			double weeklyPriceCut = Main.getInstance().getConfig().getDouble("weeklypricecut");
			int price = (int) (FileManager.dataFileCfg.getInt("regions." + key + ".price") * weeklyPriceCut);
			
			if (dateString == null) {
				continue;				
			}
			
			World world = Main.getInstance().getServer().getWorld("world");
			ProtectedRegion region = Main.getWorldGuard().getRegionManager(world).getRegion(regionString);
			
			Date dateDate = null;
			try {
				dateDate = dateFormat.parse(dateString);
			} catch (ParseException e) {
				System.out.print(Main.consolePrefix + "Error checking rent date! Failed formatting data file date.");
				e.printStackTrace();
			}
						
			Main.getInstance();
			if (dateCurrentDate.after(dateDate)) {
				
				if (Main.getEconomy().getBalance(renterString) <= price) {
					
					Bukkit.broadcastMessage(Main.gamePrefix + ChatColor.RED + "Resetting " + ChatColor.GOLD + region.getId() + ChatColor.RED + 
							" owned by " + ChatColor.GOLD + renterString + ChatColor.RED + "!");
					Unrent.unrentMethod(region);
					continue;
					
				} else {
					System.out.print(Main.consolePrefix + "Player " + renterString + " has renewed rent for plot " + regionString + ".");
					Main.getEconomy().withdrawPlayer(renterString, price);
					FileManager.dataFileCfg.set("regions." + region.getId() + ".rentuntil", TimeManager.timePlusWeek());
					FileManager.saveDataFile();
					continue;
				}				
			}		
			
			if (datePlusDay.after(dateDate)) {
				
				Player player = Bukkit.getPlayerExact(renterString);
				
				if (player == null) {
					continue;
				} else {
					if (Main.getEconomy().getBalance(renterString) >= price) {
						player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Your rent for plot " + ChatColor.GOLD + region.getId() + ChatColor.GREEN + " is due soon!");
						System.out.print(Main.consolePrefix + renterString + " has enough money to afford " + region.getId() + ".");
					} else {
						player.sendMessage(Main.gamePrefix + ChatColor.RED + "You don't have " + ChatColor.GOLD + "$" + price 
								+ ChatColor.RED + " to pay the rent for " + ChatColor.GOLD + region.getId() + "!");
						player.sendMessage(Main.gamePrefix + ChatColor.GRAY + "Your rent is due soon! Make sure you have enough money in your balance to pay the "
								+ "rent or your plot will be automatically reset.");
						System.out.print(Main.consolePrefix + renterString + " does not enough money to afford " + region.getId() + ".");
					}
					continue;
				}
				
			}
			
			System.out.print(Main.consolePrefix + "Checked plot " + region.getId() + " owned by " + renterString + ".");
			
		}				
	}

}
