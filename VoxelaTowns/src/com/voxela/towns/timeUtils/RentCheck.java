package com.voxela.towns.timeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.towns.Main;
import com.voxela.towns.mayor.TownUnrent;
import com.voxela.towns.plotManagement.PlotRent;
import com.voxela.towns.utils.ChatUtils;
import com.voxela.towns.utils.FileManager;

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
		    	checkTownRenters();
		    	checkPlayerRenters();
	    	}
	    	
	    }, ticks, 12000L);
	}

	private static void checkTownRenters() {
		
		System.out.print(Main.consolePrefix + "Automatically checking town renters...");
		
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
			String renterString = ChatUtils.fromUUID(FileManager.dataFileCfg.getString("regions." + key + ".mayor"));
			String town = FileManager.dataFileCfg.getString("regions." + key + ".name");
			
			OfflinePlayer account = Bukkit.getOfflinePlayer("town-" + town);
			
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
					TownUnrent.unrentMethod(region);
					continue;
					
				} else {
					System.out.print(Main.consolePrefix + "Player " + renterString + " has renewed rent for the town " + regionString + ".");
					Main.getEconomy().withdrawPlayer(account, price);
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
						player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Your rent for the town " + ChatColor.GOLD + region.getId() + ChatColor.GREEN + " is due soon!");
						System.out.print(Main.consolePrefix + renterString + " has enough money to afford " + region.getId() + ".");
					} else {
						player.sendMessage(Main.gamePrefix + ChatColor.RED + "You don't have " + ChatColor.GOLD + "$" + price 
								+ ChatColor.RED + " to pay the rent for " + ChatColor.GOLD + region.getId() + "!");
						player.sendMessage(Main.gamePrefix + ChatColor.GRAY + "Your rent is due soon! Make sure you have enough money in your town balance to pay the "
								+ "rent or your town will be automatically reset.");
						System.out.print(Main.consolePrefix + renterString + " does not enough money to afford " + region.getId() + ".");
					}
					continue;
				}
				
			}
			
			System.out.print(Main.consolePrefix + "Checked plot " + region.getId() + " owned by " + renterString + ".");
			
		}				
	}
	
	private static void checkPlayerRenters() {
		
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
		
		for (String townKey : FileManager.dataFileCfg.getConfigurationSection("regions").getKeys(false)) {
			
			World world = Main.getInstance().getServer().getWorld("world");
			
			String townRegionString1 = FileManager.dataFileCfg.getString("regions." + townKey);
			String townRegionString2 = townRegionString1.replace("MemorySection[path='regions.", "");
			String townRegionString = townRegionString2.replace("', root='YamlConfiguration']", "");
			ProtectedRegion townRegion = Main.getWorldGuard().getRegionManager(world).getRegion(townRegionString);
			String town = FileManager.dataFileCfg.getString("regions." + townKey + ".name");
			
			OfflinePlayer account = Bukkit.getOfflinePlayer("town-" + town);
			
			for (String plotKey : FileManager.dataFileCfg.getConfigurationSection("regions." + townKey + ".plots").getKeys(false)) {
				
				String plotRegionString1 = FileManager.dataFileCfg.getString("regions." + townKey + ".plots." + plotKey);
				String plotRegionString2 = plotRegionString1.replace("MemorySection[path='regions." + townKey + ".plots.", "");
				String plotRegionString = plotRegionString2.replace("', root='YamlConfiguration']", "");
				
				String dateString = FileManager.dataFileCfg.getString("regions." + townKey + ".plots." + plotKey + ".rentuntil");
				String renterString = FileManager.dataFileCfg.getString("regions." + townKey + ".plots." + plotKey + ".renter");
				
				double weeklyPriceCut = Main.getInstance().getConfig().getDouble("weeklypricecut");
				int price = (int) (FileManager.dataFileCfg.getInt("regions." + townKey + ".plots." + plotKey + ".price") * weeklyPriceCut);
				
				if (dateString == null) {
					continue;				
				}
				
				ProtectedRegion plotRegion = Main.getWorldGuard().getRegionManager(world).getRegion(plotRegionString);
				
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
						
						PlotRent.playerUnrentMethod(townRegion, plotRegion);
						continue;
						
					} else {
						System.out.print(Main.consolePrefix + "Player " + renterString + " has renewed rent for plot " + townRegionString + plotRegionString + ".");
						Main.getEconomy().withdrawPlayer(renterString, price);
						Main.getEconomy().depositPlayer(account, price);
						FileManager.dataFileCfg.set("regions." + townKey + ".plots." + plotKey + ".rentuntil", TimeManager.timePlusWeek());
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
							player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Your rent for plot " + ChatColor.GOLD + plotRegion.getId() + ChatColor.GREEN + " is due soon!");
							System.out.print(Main.consolePrefix + renterString + " has enough money to afford " + plotRegion.getId() + ".");
						} else {
							player.sendMessage(Main.gamePrefix + ChatColor.RED + "You don't have " + ChatColor.GOLD + "$" + price 
									+ ChatColor.RED + " to pay the rent for " + ChatColor.GOLD + plotRegion.getId() + "!");
							player.sendMessage(Main.gamePrefix + ChatColor.GRAY + "Your rent is due soon! Make sure you have enough money in your balance to pay the "
									+ "rent or your plot will be automatically reset.");
							System.out.print(Main.consolePrefix + renterString + " does not enough money to afford " + plotRegion.getId() + ".");
						}
						continue;
					}
				
				}
				
			}
			
			System.out.print(Main.consolePrefix + "Checked plots in town " + townRegion.getId() + ".");
			
		}
	}
}
