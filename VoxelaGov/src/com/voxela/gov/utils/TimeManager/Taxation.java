package com.voxela.gov.utils.TimeManager;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.voxela.gov.Main;
import com.voxela.gov.checker.CheckFed;
import com.voxela.gov.checker.CheckProvince;
import com.voxela.gov.utils.ChatUtils;
import com.voxela.gov.utils.FileManager;

import net.md_5.bungee.api.ChatColor;

public class Taxation {
	
	public static void checkTaxes() {
		
		HashMap<Player, Double> balances = new HashMap<Player, Double>();
		
		new BukkitRunnable() {
			public void run() {
				
				System.out.print(Main.consolePrefix + "Checking taxes...");
				
				double total = 0;
				double totalToServer = 0;
				
				if (!(balances.isEmpty())) {
					
					for (Player player : balances.keySet()) {
						
						if (CheckProvince.checkProvince(player).toString().contains("None!")) continue;
						
						if (!(balances.containsKey(player))) continue;
						
						String[] province = CheckProvince.checkProvince(player);
						String provinceDataFile = CheckProvince.stringToDataFile(province[1]);
						String fed = CheckProvince.getFed(provinceDataFile);
						String fedNiceName = CheckFed.getNiceName(fed);
						
						double fedTax = CheckFed.getTaxRate(fed) * 0.01;
						double totalTax = CheckFed.getTotalTaxRate(fed) * 0.01;
						
						double playerBalNow = Main.getEconomy().getBalance(player);
						double playerBalBefore = balances.get(player);
						double income = playerBalNow - playerBalBefore;
						
						// Withdraw from player
						if (income <= 0) continue;						
						double withdraw = income * totalTax;
						Main.getEconomy().withdrawPlayer(player, withdraw);
						
						// Deposit to federation						
						double deposit = income * fedTax;
						CheckFed.deposit(fed, deposit);
						
						if (Bukkit.getOnlinePlayers().contains(player)) {
						
							player.sendMessage(ChatUtils.formatTitle(ChatColor.RED + "Taxes Due!"));
							ChatUtils.sendCenteredMessage(player, ChatColor.GOLD + fedNiceName + ChatColor.GRAY + " in " + ChatColor.GOLD + province[0]);
							ChatUtils.sendCenteredMessage(player, ChatColor.GRAY + "Income: " + ChatColor.DARK_AQUA + "$" + (int) income + 
									ChatColor.DARK_GRAY + " | " + ChatColor.GRAY + "Tax Rate: " + ChatColor.DARK_AQUA + (int) totalTax * 100 + "%" +
									ChatColor.DARK_GRAY + " | " + ChatColor.GRAY + "Taxes Paid: " + ChatColor.DARK_AQUA + "$" + (int) withdraw);
							player.sendMessage(ChatUtils.divider);
							
						}
						
						total += withdraw;
						totalToServer += withdraw - deposit;
					}
					
					System.out.print(Main.consolePrefix + "Total taxes collected: " + total + ".");
					System.out.print(Main.consolePrefix + "Total money taken by server: " + totalToServer + ".");
					
					double dataFileBefore = FileManager.dataFileCfg.getDouble("taxesremoved");
					double toConfig = dataFileBefore + totalToServer;
					FileManager.dataFileCfg.set("taxesremoved", toConfig);
					FileManager.saveDataFile();
					
				}
				
				balances.clear();
				
				for (Player player : Bukkit.getOnlinePlayers()) balances.put(player, Main.getEconomy().getBalance(player));
				
			}
		}.runTaskTimer(Main.getInstance(), 0L, 12000L); //12000L = 10 mins
	}
	
}
