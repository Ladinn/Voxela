package com.voxela.plots;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.plots.plotManagement.CreatePlot;
import com.voxela.plots.plotManagement.DeletePlot;
import com.voxela.plots.plotManagement.UserPlot;
import com.voxela.plots.rent.rent;
import com.voxela.plots.rent.unrent;
import com.voxela.plots.utils.ChatUtils;

public class PlotCommand implements CommandExecutor  {
	
	Main plugin;
	
	public PlotCommand (Main passedPlugin) {
		
		this.plugin = passedPlugin;
		
	}
		
    @SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(Main.consolePrefix + ChatColor.RED
					+ "Commands cannot be typed through console.");
			return true;
		}
		
		Player player = (Player) sender;
		
		String noPerm = Main.gamePrefix + ChatColor.RED + "You do not have permission to use this command!";
		String syntaxError = Main.gamePrefix + ChatColor.RED + "Incorrect command usage. Type " + ChatColor.GOLD + "/plot" + ChatColor.RED + " for help.";
		
		// Command: /plot
		if (cmd.getName().equalsIgnoreCase("plot")) {
			
			if (args.length > 0) {

				if (args[0].equalsIgnoreCase("list")) {
					
					UserPlot.listPlots(player);
					return true;
					
				}
				
				if (args[0].equalsIgnoreCase("rent")) {
					
					if (!(args.length > 1) || args.length > 2) {
						player.sendMessage(syntaxError);
						return true;
					} else {
						
						if (Main.getWorldGuard().getRegionManager(player.getWorld()).getRegion(args[1]) == null) {
							
							player.sendMessage(Main.gamePrefix + ChatColor.RED + "Invalid plot name!");
							return true;
							
						}

						ProtectedRegion region = Main.getWorldGuard().getRegionManager(player.getWorld()).getRegion(args[1]);

						rent.rentMethod(player, region);
						return true;
						
					}
					
				}
				
				if (args[0].equalsIgnoreCase("info")) {
					
					if (!(args.length > 1) || args.length > 2) {
						player.sendMessage(syntaxError);
						return true;
					} else {
					
						if (Main.getWorldGuard().getRegionManager(player.getWorld()).getRegion(args[1]) == null) {
							
							player.sendMessage(Main.gamePrefix + ChatColor.RED + "Invalid plot name!");
							return true;
							
						}
						
						ProtectedRegion region = Main.getWorldGuard().getRegionManager(player.getWorld()).getRegion(args[1]);
						
						UserPlot.plotInfo(player, region);
						return true;
					
					}
					
				}
				
				if (args[0].equalsIgnoreCase("add")) {
					
					if (!(args.length > 2) || args.length > 3) {
						player.sendMessage(syntaxError);
						return true;
					} else {

						ProtectedRegion region = Main.getWorldGuard().getRegionManager(player.getWorld()).getRegion(args[1]);
						String playerToAdd = args[2];
						
						if (region == null) {
							
							player.sendMessage(Main.gamePrefix + ChatColor.RED + "Invalid plot name!");
							return true;
							
						} else {
						
							if (!(region.getOwners().getUniqueIds().toString().contains(player.getUniqueId().toString()))) {
								
								player.sendMessage(Main.gamePrefix + ChatColor.RED + "You do not own this plot!");
								return true;
								
							}
														
							if (!(Main.getInstance().getServer().getPlayer(playerToAdd) == null) || (Main.getInstance().getServer().getOfflinePlayer(playerToAdd).hasPlayedBefore() == true)) {
								
								UserPlot.add(player, playerToAdd, region);
								
								return true;
							} else {
								player.sendMessage(Main.gamePrefix + ChatColor.RED + "Player " + ChatColor.GOLD + playerToAdd + ChatColor.RED + " does not exist!");
								return true;
							}
							
						}
					
					}
					
				}
				
				if (args[0].equalsIgnoreCase("del")) {
					
					if (!(args.length > 2) || args.length > 3) {
						player.sendMessage(syntaxError);
						return true;
					} else {

						ProtectedRegion region = Main.getWorldGuard().getRegionManager(player.getWorld()).getRegion(args[1]);
						String playerToDelete = args[2];
						
						if (region == null) {
							
							player.sendMessage(Main.gamePrefix + ChatColor.RED + "Invalid region name!");
							return true;
							
						} else {
						
							if (!(region.getOwners().getUniqueIds().toString().contains(player.getUniqueId().toString()))) {
								
								player.sendMessage(Main.gamePrefix + ChatColor.RED + "You do not own this plot!");
								return true;
								
							}
							
							if (region.getMembers().contains(ChatUtils.toUUID(playerToDelete))) {
								
								UserPlot.delete(player, playerToDelete, region);
								player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Deleted " + ChatColor.GOLD + 
										playerToDelete + ChatColor.GREEN + " from " + ChatColor.GOLD + region.getId() + "!");
								return true;
								
							} else {
								player.sendMessage(Main.gamePrefix + ChatColor.RED + "Player " + ChatColor.GOLD + playerToDelete + ChatColor.RED + " is not a member!");
								return true;
							}
							
						}
					
					}
					
				}
				
				if (args[0].equalsIgnoreCase("unrent")) {
					
					if (!(args.length > 1) || args.length > 2) {
						player.sendMessage(syntaxError);
						return true;
					} else {

						ProtectedRegion region = Main.getWorldGuard().getRegionManager(player.getWorld()).getRegion(args[1]);
						
						if (region == null) {
							
							player.sendMessage(Main.gamePrefix + ChatColor.RED + "Invalid region name!");
							return true;
							
						} else {
						
							if (!(region.getOwners().getUniqueIds().toString().contains(player.getUniqueId().toString()))) {
								
								player.sendMessage(Main.gamePrefix + ChatColor.RED + "You do not own this plot!");
								return true;
								
							}
							
							unrent.unrentMethod(region);
							player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "You are no longer renting " + ChatColor.GOLD + region.getId() + "!");
							return true;
							
						}
					
					}
					
				}
				
				// Admin commands...
				
				if (args[0].equalsIgnoreCase("create")) {
					
					if (player.isOp() || player.hasPermission(new Permission("voxela.plots.admin"))) {
						
						if (!(player.getWorld() == Main.getInstance().getServer().getWorld("world"))) {
							player.sendMessage(Main.gamePrefix + ChatColor.RED + "Plots cannot be created in this world!");
							return true;							
						}
						
						if (!(args.length > 2) || args.length > 3) {
							player.sendMessage(syntaxError);
							return true;
						} else {
						
							int price = Integer.parseInt(args[1]);
							
							Selection sel = Main.getWorldEdit().getSelection(player);
							
							// If player has not selected a region.
							if (sel == null) {
								player.sendMessage(Main.gamePrefix + ChatColor.RED
										+ "You must make a selection first!");
								return true;
							}
							
							World world = Bukkit.getWorld(args[2]);
							CreatePlot.createPlot(player, price, world);
							return true;
						}
						
					} else player.sendMessage(noPerm); return true;
				}
				
				if (args[0].equalsIgnoreCase("restore")) {
					
					if (player.isOp() || player.hasPermission(new Permission("voxela.plots.admin"))) {
						
						if (!(args.length > 1) || args.length > 2) {
							player.sendMessage(syntaxError);
							return true;
						} else {
							
							String regionString = args[1];
							
							// If player has not selected a region.
							if (Main.getWorldGuard().getRegionManager(player.getWorld()).getRegion(regionString) == null) {
								player.sendMessage(Main.gamePrefix + ChatColor.RED
										+ "Invalid region!");
								return true;
							}
							
							World world = Main.getInstance().getServer().getWorld("world");
							
							ProtectedRegion region = Main.getWorldGuard().getRegionManager(world).getRegion(regionString);
							
							unrent.unrentMethod(region);
							
							return true;
						}
						
					} else player.sendMessage(noPerm); return true;
				}
					
				if (args[0].equalsIgnoreCase("delete")) {
					
					if (!(args.length > 1) || args.length > 2) {
						player.sendMessage(syntaxError);
						return true;
					} else {
					
						if (player.isOp() || player.hasPermission(new Permission("voxela.plots.admin"))) {
																			
							if (Main.getWorldGuard().getRegionManager(player.getWorld()).getRegion(args[1]) == null) {
								
								player.sendMessage(Main.gamePrefix + ChatColor.RED + "Invalid region name!");
								return true;
								
							} else {
								
								ProtectedRegion region = Main.getWorldGuard().getRegionManager(player.getWorld()).getRegion(args[1]);
								player.sendMessage(Main.gamePrefix + ChatColor.GRAY + "Valid region. Deleting.");
								DeletePlot.delPlot(player, region);
								return true;
							
							}						
						} else player.sendMessage(noPerm); return true;
					}
				}
				
				else {
					player.sendMessage(syntaxError);
				}
					
			} else {
				
				player.sendMessage(ChatUtils.formatTitle("Voxela Plots"));
				player.sendMessage("");
				player.sendMessage(ChatColor.GOLD + "/plot list" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "List the plots that you're renting.");
				player.sendMessage(ChatColor.GOLD + "/plot info" + ChatColor.GRAY + " [plot name]"+ ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Get info on a plot.");
				player.sendMessage(ChatColor.GOLD + "/plot rent" + ChatColor.GRAY + " [plot name]"+ ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Rent a new plot.");
				player.sendMessage(ChatColor.GOLD + "/plot unrent" + ChatColor.GRAY + " [plot name]"+ ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Stop renting a plot.");
				player.sendMessage(ChatColor.GOLD + "/plot add" + ChatColor.GRAY + " [plot name] [player]" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Add a player to your plot.");
				player.sendMessage(ChatColor.GOLD + "/plot del" + ChatColor.GRAY + " [plot name] [player]" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Remove a player.");
				player.sendMessage("");
				player.sendMessage(ChatUtils.divider);
				
			}
					
		}
		
		return true;
		
	}
}
