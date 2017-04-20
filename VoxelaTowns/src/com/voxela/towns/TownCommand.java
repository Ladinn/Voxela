package com.voxela.towns;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.towns.townManagement.CreateTown;
import com.voxela.towns.townManagement.DeleteTown;
import com.voxela.towns.townManagement.InfoTown;
import com.voxela.towns.townManagement.TownPrice;
import com.voxela.towns.utils.ChatUtils;
import com.voxela.towns.utils.FileManager;
import com.voxela.towns.utils.TownRegionConvert;
import com.voxela.towns.Main;
import com.voxela.towns.mayor.MayorCommand;
import com.voxela.towns.mayor.NewTown;
import com.voxela.towns.mayor.TownUnrent;
import com.voxela.towns.plotManagement.PlotCommand;
import com.voxela.towns.plotManagement.PlotRent;
import com.voxela.towns.townInfo.GetMembers;
import com.voxela.towns.townInfo.MayorCheck;
import com.voxela.towns.townInfo.TownInfo;

import net.md_5.bungee.api.ChatColor;

public class TownCommand implements CommandExecutor {

	Main plugin;

	public TownCommand(Main passedPlugin) {

		this.plugin = passedPlugin;

	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(Main.consolePrefix + ChatColor.RED + "Commands cannot be typed through console.");
			return true;
		}

		Player player = (Player) sender;

		String noPerm = Main.gamePrefix + ChatColor.RED + "You do not have permission to use this command!";
		String syntaxError = Main.gamePrefix + ChatColor.RED + "Incorrect command usage. Type " + ChatColor.GOLD
				+ "/town" + ChatColor.RED + " for help.";
		String integer = ".*\\d+.*";

		// Command: /town
		if (cmd.getName().equalsIgnoreCase("town")) {

			if (args.length > 0) {

				if (args[0].equalsIgnoreCase("info")) {

					if (!(args.length == 2)) {
						player.sendMessage(syntaxError);
						return true;
					}
					
					String town = args[1];

					if (!(TownInfo.townExists(town))) {

						player.sendMessage(Main.gamePrefix + ChatColor.RED + "The town " + ChatColor.GOLD + town
								+ ChatColor.RED + " does not exist!");
						return true;
					}
					
					ProtectedRegion region = TownRegionConvert.townToRegion(town);

					InfoTown.infoTown(player, region, town);
					return true;
				}
				
				if (args[0].equalsIgnoreCase("leave")) {
					
					if (!(args.length == 2)) {
						player.sendMessage(syntaxError);
						return true;
					}

					String town = args[1];
					ProtectedRegion region = TownRegionConvert.townToRegion(town);
					
					if (MayorCheck.isMayorOrDeputy(region, player)) {
						
						player.sendMessage(Main.gamePrefix + ChatColor.RED + "A mayor or deputy cannot abandon the town!");
						return true;
					}
					
				    String playerUUID = player.getUniqueId().toString();
					
					String key = "regions." + region.getId() + ".residents";
				    List<String> current = FileManager.dataFileCfg.getStringList(key);
				    
				    current.remove(playerUUID);
				    FileManager.dataFileCfg.set(key, current);
				    FileManager.saveDataFile();
				    player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Successfully left the town" + ChatColor.GOLD + town + ".");
				    return true;
					
				}
				
				if (args[0].equalsIgnoreCase("plot")) {
					
					String plotSyntaxError = Main.gamePrefix + ChatColor.RED + "Incorrect command usage. Type " + ChatColor.GOLD
							+ "/town plot" + ChatColor.RED + " for help.";

					if (args.length == 1) {
						
						player.sendMessage(ChatUtils.formatTitle("Voxela Towns"));
						ChatUtils.sendCenteredMessage(player, ChatColor.GOLD + " Town Plot Commands");
						player.sendMessage("");
						player.sendMessage(ChatColor.GOLD + "/town plot list " + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "List town plots you have access to.");
						player.sendMessage(ChatColor.GOLD + "/town plot info " + ChatColor.GRAY + "[town] [plot]" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Get info on a specific town plot.");
						player.sendMessage(ChatColor.GOLD + "/town plot rent " + ChatColor.GRAY + "[town] [plot]" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Rent a town plot.");
						player.sendMessage(ChatColor.GOLD + "/town plot unrent " + ChatColor.GRAY + "[town] [plot]" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Stop renting a town plot.");
						player.sendMessage(ChatColor.GOLD + "/town plot add " + ChatColor.GRAY + "[plot] [player]" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Add a new member.");
						player.sendMessage(ChatColor.GOLD + "/town plot del " + ChatColor.GRAY + "[plot] [player]" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Delete a member.");
						player.sendMessage(ChatUtils.divider);
						return true;
						
					}
					
					if (args[1].equalsIgnoreCase("list")) {
						
					        Map<String, ProtectedRegion> regionMap = Main.getWorldGuard().getRegionManager(player.getWorld()).getRegions();
					        
							player.sendMessage(ChatUtils.formatTitle("Voxela Towns"));
							
							ChatUtils.sendCenteredMessage(player, ChatColor.GOLD + "Plots you have access to:");
					        for (ProtectedRegion region : regionMap.values()) {
					        	
					            if(region.isOwner(Main.getWorldGuard().wrapPlayer(player))) {
					            	            	
					                player.sendMessage(ChatColor.DARK_AQUA + region.getId() + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Owner");
					                continue;
					                
					            } else if(region.isMember(Main.getWorldGuard().wrapPlayer(player))) {
					                player.sendMessage(ChatColor.DARK_AQUA + region.getId() + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Member");
					            }
					        }
					        return true;
					}
					
					if (args[1].equalsIgnoreCase("add")) {
						
						if (!(args.length == 4)) {
							player.sendMessage(plotSyntaxError);
							return true;
						} else {

							ProtectedRegion region = Main.getWorldGuard().getRegionManager(player.getWorld()).getRegion(args[2]);
							String playerToAdd = args[3];
							
							if (region == null) {
								
								player.sendMessage(Main.gamePrefix + ChatColor.RED + "Invalid plot name!");
								return true;
								
							} else {
							
								if (!(region.getOwners().getUniqueIds().toString().contains(player.getUniqueId().toString()))) {
									
									player.sendMessage(Main.gamePrefix + ChatColor.RED + "You do not own this plot!");
									return true;
									
								}
															
								if (!(Main.getInstance().getServer().getPlayer(playerToAdd) == null) || (Main.getInstance().getServer().getOfflinePlayer(playerToAdd).hasPlayedBefore() == true)) {
									
									PlotCommand.add(player, playerToAdd, region);
									
									return true;
								} else {
									player.sendMessage(Main.gamePrefix + ChatColor.RED + "Player " + ChatColor.GOLD + playerToAdd + ChatColor.RED + " does not exist!");
									return true;
								}
								
							}
						
						}
						
					}
					
					if (args[1].equalsIgnoreCase("del")) {
						
						if (!(args.length == 4)) {
							player.sendMessage(plotSyntaxError);
							return true;
						} else {

							ProtectedRegion region = Main.getWorldGuard().getRegionManager(player.getWorld()).getRegion(args[2]);
							String playerToDelete = args[3];
							
							if (region == null) {
								
								player.sendMessage(Main.gamePrefix + ChatColor.RED + "Invalid region name!");
								return true;
								
							} else {
							
								if (!(region.getOwners().getUniqueIds().toString().contains(player.getUniqueId().toString()))) {
									
									player.sendMessage(Main.gamePrefix + ChatColor.RED + "You do not own this plot!");
									return true;
									
								}
								
								if (region.getMembers().contains(ChatUtils.toUUID(playerToDelete))) {
									
									PlotCommand.delete(player, playerToDelete, region);
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
					
					if (args[1].equalsIgnoreCase("info")) {
						
						if (!(args.length == 4)) {
							player.sendMessage(plotSyntaxError);
							return true;
						}
						
						if (TownRegionConvert.townToRegion(args[2]) == null) {
							player.sendMessage(Main.gamePrefix + ChatColor.RED + "The town " + ChatColor.GOLD + args[2]
									+ ChatColor.RED + " does not exist!");
							return true;
						}
						
						if (Main.getWorldGuard().getRegionManager(player.getWorld()).getRegion(args[3]) == null) {
							
							player.sendMessage(Main.gamePrefix + ChatColor.RED + "Invalid plot name!");
							return true;
							
						}
						
						ProtectedRegion townRegion = TownRegionConvert.townToRegion(args[2]);
						ProtectedRegion region = Main.getWorldGuard().getRegionManager(player.getWorld()).getRegion(args[3]);

						PlotCommand.plotInfo(player, integer, townRegion, region);
						return true;
						
					}

					if (args[1].equalsIgnoreCase("rent")) {
						
						if (!(args.length == 4)) {
							player.sendMessage(plotSyntaxError);
							return true;
						}
						
						String town = args[2];
						
						ProtectedRegion townRegion = TownRegionConvert.townToRegion(args[2]);
						ProtectedRegion plotRegion = Main.getWorldGuard().getRegionManager(player.getWorld()).getRegion(args[3]);
						
						PlotRent.rentMethod(player, town, townRegion, plotRegion);
						return true;
					}
					
					if (args[1].equalsIgnoreCase("unrent")) {
						
						if (!(args.length == 4)) {
							player.sendMessage(plotSyntaxError);
							return true;
						}
						
						ProtectedRegion townRegion = TownRegionConvert.townToRegion(args[2]);
						ProtectedRegion plotRegion = Main.getWorldGuard().getRegionManager(player.getWorld()).getRegion(args[3]);
						
						PlotRent.playerUnrentMethod(townRegion, plotRegion);
						player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "You are no longer renting " + ChatColor.GOLD + plotRegion.getId() + "!");
						return true;
					}
					
					player.sendMessage(plotSyntaxError);
					return true;
				}

				if (args[0].equalsIgnoreCase("new")) {
					
					if (!(args.length == 3)) {
						player.sendMessage(syntaxError);
						return true;
					}
					
					String availTowns = TownInfo.getAvailableTowns();

					if (!(Main.getWorldGuard().getRegionManager(player.getWorld()).hasRegion(args[2]))) {

						player.sendMessage(Main.gamePrefix + ChatColor.RED + "Invalid town region!");
						player.sendMessage(Main.gamePrefix + ChatColor.GRAY + "Available towns regions: " + availTowns);
						return true;

					}
					
					String town = args[1];
					
					if (TownInfo.townExists(town)) {

						player.sendMessage(Main.gamePrefix + ChatColor.RED + "The town name " + ChatColor.GOLD + town
								+ ChatColor.RED + " is taken!");
						return true;
					}

					ProtectedRegion region = Main.getWorldGuard().getRegionManager(Bukkit.getWorld("world"))
							.getRegion(args[2]);

					if (TownInfo.regionIsTaken(region)) {
						player.sendMessage(Main.gamePrefix + ChatColor.RED + "The town region " + ChatColor.GOLD
								+ region.getId() + ChatColor.RED + " is taken!");
						player.sendMessage(Main.gamePrefix + ChatColor.GRAY + "Available towns regions: " + availTowns);
					}

					NewTown.rentMethod(player, region, town);
					return true;
				}

				if (args[0].equalsIgnoreCase("mayor")) {

					if (args.length >= 7) {
						player.sendMessage(syntaxError);
						return true;
					}
					
					if (args.length == 1) {
						
						MayorCommand.mayorHelp(player);
						return true;
					}	

					String town = args[1];

					if (TownRegionConvert.townToRegion(town) == null) {
						player.sendMessage(Main.gamePrefix + ChatColor.RED + "The town " + ChatColor.GOLD + args[1]
								+ ChatColor.RED + " does not exist!");
						return true;
					}

					ProtectedRegion region = TownRegionConvert.townToRegion(town);
					String mayor = MayorCheck.getMayor(region);

					if (MayorCheck.isMayorOrDeputy(region, player)) {		

						if (args[2].equalsIgnoreCase("plot")) {
							
							if (args.length == 3) {
								
								MayorCommand.plotHelp(player, town);
								return true;
								
							}
							
							if (args[3].equalsIgnoreCase("list")) {
								
								if (!(args.length == 4)) {
									player.sendMessage(syntaxError);
									return true;
								}
								
								MayorCommand.plotList(player, town, region);
								return true;							
							}							
							
							if (args[3].equalsIgnoreCase("create")) {

								if ( !(args.length == 6)) {
									
									player.sendMessage(Main.gamePrefix + ChatColor.RED + "Incorrect command usage. Create a plot with:");
									player.sendMessage(Main.gamePrefix + ChatColor.RED + ChatColor.ITALIC + "/town mayor " + ChatColor.GOLD + ChatColor.ITALIC + town + ChatColor.RED + ChatColor.ITALIC + " create "
											+ ChatColor.GRAY + ChatColor.ITALIC + "[name] [price]");
									player.sendMessage(Main.gamePrefix + ChatColor.DARK_GRAY + "Stand in one corner of the plot you are creating and type //pos1."
											+ " Then go to the other corner and type //pos2. This selects the area in between the two points.");
									return true;
								}
								
								Selection sel = Main.getWorldEdit().getSelection(player);

								// If player has not selected a region.
								if (sel == null) {
									player.sendMessage(Main.gamePrefix + ChatColor.RED + "You must make a selection first!");
									return true;
								}
								
								String plotName = args[4];
								
								if (Main.getWorldGuard().getRegionManager(Bukkit.getWorld("world")).hasRegion(region.getId() + "-" + plotName)) {
									player.sendMessage(Main.gamePrefix + ChatColor.RED + "This plot name already exists in " + town + "!");
									return true;
								}
																
								int price = Integer.parseInt(args[5]);

								MayorCommand.plotCreate(player, town, region, price, plotName);
								return true;
								
							}
							
							if (args[3].equalsIgnoreCase("delete")) {
								
								if (!(args.length == 5)) {
									player.sendMessage(syntaxError);
									return true;
								}
								
								if (!(Main.getWorldGuard().getRegionManager(Bukkit.getWorld("world")).hasRegion(args[4]))) {
									
									player.sendMessage(Main.gamePrefix + ChatColor.RED + "This town plot does not exist!");
									return true;
								}
								
								ProtectedRegion plotToDelete = Main.getWorldGuard().getRegionManager(Bukkit.getWorld("world")).getRegion(args[4]);
								MayorCommand.plotDelete(player, town, region, plotToDelete);
								return true;							
							}
							
							if (args[3].equalsIgnoreCase("price")) {
								
								if (!(args.length == 6) || !(args[5].matches(integer))) {
									player.sendMessage(syntaxError);
									return true;
								}
								
								if (!(Main.getWorldGuard().getRegionManager(Bukkit.getWorld("world")).hasRegion(args[4]))) {
									
									player.sendMessage(Main.gamePrefix + ChatColor.RED + "This town plot does not exist!");
									return true;
								}
								
								int price = Integer.parseInt(args[5]);

								ProtectedRegion plotToPrice = Main.getWorldGuard().getRegionManager(Bukkit.getWorld("world")).getRegion(args[4]);
								MayorCommand.plotPrice(player, town, region, plotToPrice, price);
								return true;							
							}
							
							if (args[3].equalsIgnoreCase("evict")) {
								
								if (!(Main.getWorldGuard().getRegionManager(Bukkit.getWorld("world")).hasRegion(args[4]))) {
									
									player.sendMessage(Main.gamePrefix + ChatColor.RED + "This town plot does not exist!");
									return true;
								}
								
								ProtectedRegion plotToEvict = Main.getWorldGuard().getRegionManager(Bukkit.getWorld("world")).getRegion(args[4]);
								MayorCommand.plotEvict(player, town, region, plotToEvict);
								return true;
								
							}
							return true;						
						}

						if (args[2].equalsIgnoreCase("add")) {
							
							if (!(args.length == 3)) {
								player.sendMessage(syntaxError);
								return true;
							}
							
							if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayerExact(args[3]))) {
								
								Player playerInvited = Bukkit.getPlayer(args[3]);
								
								if (GetMembers.getResidents(town, region).contains(args[3]) || MayorCheck.isMayor(region, Bukkit.getPlayer(args[3]))) {
									
									player.sendMessage(Main.consolePrefix + ChatColor.RED + "The player " + ChatColor.GOLD + args[3] + ChatColor.RED + " is already in this town!");
									return true;
									
								}

								MayorCommand.addPlayer(player, town, region, playerInvited);
								return true;
								
							} else {
								player.sendMessage(Main.gamePrefix + ChatColor.RED + "The player " + ChatColor.GOLD + args[3] + ChatColor.RED + " is not online!");
								return true;
							}
						}

						if (args[2].equalsIgnoreCase("kick")) {
							
							if (!(args.length == 3)) {
								player.sendMessage(syntaxError);
								return true;
							}

							String playerToKick = args[3];
							
							if (MayorCheck.isMayorOrDeputy(region, player)) {
								player.sendMessage(Main.consolePrefix + ChatColor.RED + "Are you serious? You cant kick the mayor or a town deputy.");
								return true;
							}

							if (GetMembers.getResidents(town, region).contains(playerToKick)) {
								
								MayorCommand.kickPlayer(playerToKick, region, player);
								return true;								
							} else {
								player.sendMessage(Main.gamePrefix + ChatColor.RED + "The player " + ChatColor.GOLD + playerToKick + ChatColor.RED + " is not a resident!");
								return true;
							}
						}

						if (args[2].equalsIgnoreCase("deposit")) {
							
							if (!(args.length == 3)) {
								player.sendMessage(syntaxError);
								return true;
							}
							
							if (!(args[3].matches(integer))) {
								player.sendMessage(Main.gamePrefix + ChatColor.RED + "You must specify an integer!");
								return true;
							}
							
							int amount = Integer.parseInt(args[3]);
							
							MayorCommand.deposit(mayor, player, amount);
							return true;
						}

						if (args[2].equalsIgnoreCase("withdraw")) {

							if (MayorCheck.isMayor(region, player)) {
								
								if (!(args.length == 3)) {
									player.sendMessage(syntaxError);
									return true;
								}
								
								if (!(args[3].matches(".*\\d+.*"))) {
									player.sendMessage(Main.gamePrefix + ChatColor.RED + "You must specify an integer!");
									return true;
								}
								
								int amount = Integer.parseInt(args[3]);
								
								MayorCommand.withdraw(mayor, player, amount);
								return true;
							}
							player.sendMessage(Main.gamePrefix + ChatColor.RED + "Only the town mayor can do this!");
							return true;
						}

						if (args[2].equalsIgnoreCase("deputy")) {

							if (MayorCheck.isMayor(region, player)) {
								
								if (!(args.length == 4)) {
									player.sendMessage(syntaxError);
									return true;
								}
								
								if (args[3].equalsIgnoreCase("add")) {
									
									if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayerExact(args[3]))) {
										
										Player deputy = Bukkit.getPlayer(args[3]);

										MayorCommand.addDeputy(mayor, player, region, deputy);
										return true;
										
									} else {
										player.sendMessage(Main.gamePrefix + ChatColor.RED + "The player " + ChatColor.GOLD + args[3] + ChatColor.RED + " is not online!");
										return true;
									}
									
								}
								
								if (args[3].equalsIgnoreCase("remove")) {
									
									String deputy = args[3];
									
									if (MayorCheck.isDeputy(region, deputy)) {
										
										MayorCommand.removeDeputy(town, player, region, deputy);
										return true;
										
									} else {
										player.sendMessage(Main.gamePrefix + ChatColor.RED + "The player " + ChatColor.GOLD + args[3] + ChatColor.RED + " is not a deputy!");
										return true;										
									}
									
									
								}
								
								player.sendMessage(syntaxError);								
								
							}
							
							player.sendMessage(Main.gamePrefix + ChatColor.RED + "Only the town mayor can do this!");
							return true;
						}

					}
					player.sendMessage(
							Main.gamePrefix + ChatColor.RED + "You are not a mayor or a deputy in this town!");
					player.sendMessage(Main.gamePrefix + ChatColor.GRAY + "Try asking the town mayor, "
							+ ChatColor.DARK_AQUA + mayor + ChatColor.GRAY + ", to promote you to deputy!");
					return true;
				}

				// Admin commands...

				if (args[0].equalsIgnoreCase("create")) {

					if (player.isOp() || player.hasPermission(new Permission("voxela.towns.admin"))) {

						if (!(player.getWorld() == Main.getInstance().getServer().getWorld("world"))) {
							player.sendMessage(
									Main.gamePrefix + ChatColor.RED + "Towns cannot be created in this world!");
							return true;
						}

						if (args.length > 2) {
							player.sendMessage(syntaxError);
							return true;
						}

						Selection sel = Main.getWorldEdit().getSelection(player);

						// If player has not selected a region.
						if (sel == null) {
							player.sendMessage(Main.gamePrefix + ChatColor.RED + "You must make a selection first!");
							return true;
						}

						int priceVol = TownPrice.getTownPrice(sel);

						if (args.length == 1) {

							CreateTown.createTown(player, player.getWorld(), priceVol);
							return true;
						}

						if (args.length == 2) {

							if (!(Bukkit.getWorlds().contains(Bukkit.getWorld(args[1])))) {
								player.sendMessage(Main.gamePrefix + ChatColor.RED + "Unknown world!");
								return true;
							} else {

								World world = Bukkit.getWorld(args[1]);

								CreateTown.createTown(player, world, priceVol);
								return true;

							}
						}

						player.sendMessage(Main.gamePrefix + ChatColor.RED + "Unknown error.");
						return true;

					} else
						player.sendMessage(noPerm);
					return true;
				}

				if (args[0].equalsIgnoreCase("restore")) {

					if (player.isOp() || player.hasPermission(new Permission("voxela.towns.admin"))) {

						if (!(args.length > 1) || args.length > 2) {
							
							player.sendMessage(syntaxError);
							return true;
							
						} else {

							String regionString = args[1];

							// If player has not selected a region.
							if (Main.getWorldGuard().getRegionManager(player.getWorld())
									.getRegion(regionString) == null) {
								player.sendMessage(Main.gamePrefix + ChatColor.RED + "Invalid region!");
								return true;
							}

							World world = Main.getInstance().getServer().getWorld("world");

							ProtectedRegion region = Main.getWorldGuard().getRegionManager(world)
									.getRegion(regionString);

							TownUnrent.unrentMethod(region);
							player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Restored plot: " + ChatColor.GOLD + region.getId());
							return true;
						}

					} else
						player.sendMessage(noPerm);
					return true;
				}

				if (args[0].equalsIgnoreCase("delete")) {

					if (!(args.length > 1) || args.length > 2) {
						player.sendMessage(syntaxError);
						return true;
					} else {

						if (player.isOp() || player.hasPermission(new Permission("voxela.towns.admin"))) {

							if (Main.getWorldGuard().getRegionManager(player.getWorld()).getRegion(args[1]) == null) {

								player.sendMessage(Main.gamePrefix + ChatColor.RED + "Invalid region name!");
								return true;

							} else {

								ProtectedRegion region = Main.getWorldGuard().getRegionManager(player.getWorld())
										.getRegion(args[1]);
								player.sendMessage(Main.gamePrefix + ChatColor.GRAY + "Valid region. Deleting.");
								DeleteTown.delPlot(player, region);
								return true;

							}
						} else
							player.sendMessage(noPerm);
						return true;
					}
				} player.sendMessage(syntaxError);
			}
		}

		player.sendMessage(ChatUtils.formatTitle("Voxela Towns"));
		player.sendMessage("");
		player.sendMessage(ChatColor.GOLD + "/town plot" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA
				+ "Manage a plot you rent in a town.");
		player.sendMessage(ChatColor.GOLD + "/town info" + ChatColor.GRAY + " [town name]" + ChatColor.DARK_GRAY + " - "
				+ ChatColor.DARK_AQUA + "Get information about a town.");
		player.sendMessage(ChatColor.GOLD + "/town new" + ChatColor.GRAY + " [town name] [region]" + ChatColor.DARK_GRAY
				+ " - " + ChatColor.DARK_AQUA + "Create a new town.");
		player.sendMessage(ChatColor.GOLD + "/town leave" + ChatColor.GRAY + " [town name]" + ChatColor.DARK_GRAY
				+ " - " + ChatColor.DARK_AQUA + "Leave a town.");
		player.sendMessage(ChatColor.GOLD + "/town mayor" + ChatColor.GRAY + " [town name]" + ChatColor.DARK_GRAY
				+ " - " + ChatColor.DARK_AQUA + "Manage a town you run.");
		player.sendMessage("");
		player.sendMessage(ChatUtils.divider);
		return true;
	}
}