package com.voxela.towns.mayor;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.managers.storage.StorageException;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion.CircularInheritanceException;
import com.voxela.towns.Main;
import com.voxela.towns.townInfo.GetChildren;
import com.voxela.towns.utils.ChatUtils;
import com.voxela.towns.utils.FileManager;

import net.md_5.bungee.api.ChatColor;

public class MayorCommand {
	
	public static void mayorHelp(Player player) {

		player.sendMessage(ChatUtils.formatTitle("Voxela Towns"));
		ChatUtils.sendCenteredMessage(player, ChatColor.GOLD + "  Mayor Commands");
		player.sendMessage("");
		player.sendMessage(ChatColor.GOLD + "/town mayor " + ChatColor.GRAY + "[town]" + ChatColor.GOLD + " plot");
		player.sendMessage(" " + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Manage plots inside your town.");
		player.sendMessage(ChatColor.GOLD + "/town mayor " + ChatColor.GRAY + "[town]" + ChatColor.GOLD + " add" + ChatColor.GRAY + " [player]");
		player.sendMessage(" " + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Add a player to your town.");
		player.sendMessage(ChatColor.GOLD + "/town mayor " + ChatColor.GRAY + "[town]" + ChatColor.GOLD + " kick" + ChatColor.GRAY + " [player]");
		player.sendMessage(" " + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Kick a player from your town.");
		player.sendMessage(ChatColor.GOLD + "/town mayor " + ChatColor.GRAY + "[town]" + ChatColor.GOLD + " deposit" + ChatColor.GRAY + " [amount]");
		player.sendMessage(" " + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Deposit money into your town bank.");
		player.sendMessage(ChatColor.GOLD + "/town mayor " + ChatColor.GRAY + "[town]" + ChatColor.GOLD + " withdraw" + ChatColor.GRAY + " [amount]");
		player.sendMessage(ChatColor.RED + " (Mayor only)" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Withdraw money from your town bank.");
		player.sendMessage(ChatColor.GOLD + "/town mayor " + ChatColor.GRAY + "[town]" + ChatColor.GOLD + " deputy" + ChatColor.GRAY + " [add/remove] [player]");
		player.sendMessage(ChatColor.RED + " (Mayor only)" + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Set whether a town member is a deputy.");
		player.sendMessage("");
		player.sendMessage(ChatUtils.divider);
		
	}
	
	public static void plotHelp(Player player, String town) {
		
		player.sendMessage(ChatUtils.formatTitle("Voxela Towns"));
		ChatUtils.sendCenteredMessage(player, ChatColor.GOLD + town + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Mayor Plot Commands");
		player.sendMessage("");
		player.sendMessage(ChatColor.GOLD + "/town mayor " + town + " plot list");
		player.sendMessage(" " + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "List all of the plots in your town.");
		player.sendMessage(ChatColor.GOLD + "/town mayor " + town + " plot create " + ChatColor.GRAY + "[name] [price]");
		player.sendMessage(" " + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Creates a plot within the selection. Use a wooden pickaxe.");
		player.sendMessage(ChatColor.GOLD + "/town mayor " + town + " plot delete " + ChatColor.GRAY + "[plot region]");
		player.sendMessage(" " + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Deletes an existing town plot.");
		player.sendMessage(ChatColor.GOLD + "/town mayor " + town + " plot price " + ChatColor.GRAY + "[plot region] [price]");
		player.sendMessage(" " + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Change the rent price of a plot.");
		player.sendMessage(ChatColor.GOLD + "/town mayor " + town + " plot evict " + ChatColor.GRAY + "[plot region]");
		player.sendMessage(" " + ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "Evict a player from their plot.");
		player.sendMessage(ChatUtils.divider);

	}
	
	public static void plotList(Player player, String town, ProtectedRegion region) {

        List<ProtectedRegion> childrenList = GetChildren.getChildren(region);
        
		player.sendMessage(ChatUtils.formatTitle("Voxela Towns"));
		ChatUtils.sendCenteredMessage(player, ChatColor.GOLD + "  " + town + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Plot List");
        player.sendMessage("");
        for (int i=0; i<childrenList.size(); i++) {
        	System.out.print(childrenList.get(i).getId());
        	String childRegion = childrenList.get(i).getId();
			String renterString = FileManager.dataFileCfg.getString("regions." + region.getId() + ".plots." + childRegion + ".renter");
			String price = FileManager.dataFileCfg.getString("regions." + region.getId() + ".plots." + childRegion + ".price");

			if (renterString.equals("unowned")) {
				renterString = ChatColor.RED + "Unowned!";				
			}
        	player.sendMessage(ChatColor.DARK_AQUA + childRegion + ": " + ChatColor.GREEN + renterString + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "$" + price);
        	continue;        	
        }
        player.sendMessage(ChatUtils.divider);
        return;
		
		

	}
	
	@SuppressWarnings("deprecation")
	public static void plotCreate(Player player, String town, ProtectedRegion townRegion, int price, String plotName) {
		
		Selection sel = Main.getWorldEdit().getSelection(player);
		
		Location locMax = sel.getMaximumPoint();
		Location locMin = sel.getMinimumPoint();

		if (!(Main.getWorldGuard().getRegionManager(Bukkit.getWorld("world")).getApplicableRegions(locMax).getRegions().contains(townRegion) 
				&& Main.getWorldGuard().getRegionManager(Bukkit.getWorld("world")).getApplicableRegions(locMin).getRegions().contains(townRegion) )) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "The plot boundaries are not within the town border!");
			return;			
		}

		ProtectedCuboidRegion region = new ProtectedCuboidRegion(
				townRegion.getId() + "-" + plotName,
				new BlockVector(sel.getNativeMinimumPoint()),
				new BlockVector(sel.getNativeMaximumPoint())
		);
		
		Main.getWorldGuard().getRegionManager(player.getWorld()).addRegion(region);
        region.setFlag(DefaultFlag.CHEST_ACCESS,StateFlag.State.DENY);     
        region.setFlag(DefaultFlag.CHEST_ACCESS.getRegionGroupFlag(), RegionGroup.NON_MEMBERS);
        region.setFlag(DefaultFlag.USE,StateFlag.State.DENY);     
        region.setFlag(DefaultFlag.USE.getRegionGroupFlag(), RegionGroup.NON_MEMBERS);
        
        try {
			region.setParent(townRegion);
		} catch (CircularInheritanceException e) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "Error setting plot parent. Contact administrator.");
			Main.getWorldGuard().getRegionManager(Bukkit.getWorld("world")).removeRegion(region.getId());
			e.printStackTrace();
			return;
		}
        
		region.setFlag(DefaultFlag.GREET_MESSAGE, Main.gamePrefix + ChatColor.GOLD + town + ":" + ChatColor.GREEN + " Entering unowned plot: " + 
		ChatColor.GOLD + region.getId() + ".");
		region.setFlag(DefaultFlag.FAREWELL_MESSAGE, Main.gamePrefix + ChatColor.GOLD + town + ":" + ChatColor.GREEN + " Leaving unowned plot: " + 
				ChatColor.GOLD + region.getId() + ".");
		
		try {
			Main.getWorldGuard().getGlobalRegionManager().get(player.getWorld()).save();
			player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Town plot created: " + ChatColor.GOLD + region.getId() + ". " + ChatColor.GREEN + "Price: " + ChatColor.GOLD + "$" + price + ".");
		} catch (StorageException e) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "Could not save plot:" + region.getId());
			Main.getWorldGuard().getRegionManager(Bukkit.getWorld("world")).removeRegion(region.getId());
			e.printStackTrace();
			return;
		}
		
		FileManager.dataFileCfg.set("regions." + townRegion.getId() + ".plots." + region.getId() + ".renter", "unowned");
		FileManager.dataFileCfg.set("regions." + townRegion.getId() + ".plots." + region.getId() + ".price", price);
		FileManager.saveDataFile();
		
	}
	
	public static void plotDelete(Player player, String town, ProtectedRegion townRegion, ProtectedRegion plotToDelete) {
		
		RegionContainer container = Main.getWorldGuard().getRegionContainer();
		RegionManager regionManager = container.get(player.getWorld());
		
	    regionManager.removeRegion(plotToDelete.getId());
	    
		FileManager.dataFileCfg.set("regions." + townRegion.getId() + ".plots." + plotToDelete.getId(), null);
		FileManager.saveDataFile();
		
		player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Successfully deleted town plot: " + ChatColor.GOLD + plotToDelete.getId());
		
	}
	
	public static void plotPrice(Player player, String town, ProtectedRegion townRegion, ProtectedRegion plotToPrice, int price) {
		
		FileManager.dataFileCfg.set("regions." + townRegion.getId() + ".plots." + plotToPrice.getId() + ".price", price);
		FileManager.saveDataFile();		
		player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Successfully changed price of town plot: " + ChatColor.GOLD + plotToPrice.getId() +
				ChatColor.GREEN + " to " + ChatColor.GOLD + price + ".");
		
	}
	
	public static void plotEvict(Player player, String town, ProtectedRegion townRegion, ProtectedRegion plotToEvict) {
		
		String renter = FileManager.dataFileCfg.getString("regions." + townRegion.getId() + ".plots." + plotToEvict.getId() + ".renter");
		FileManager.dataFileCfg.set("regions." + townRegion.getId() + ".plots." + plotToEvict.getId() + ".renter", null);
		FileManager.saveDataFile();		
		player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Successfully removed plot owner " + ChatColor.GOLD + renter +
				ChatColor.GREEN + " from " + ChatColor.GOLD + plotToEvict.getId() + ".");
		
	}
	
	public static void addPlayer(Player player, String town, ProtectedRegion region, Player playerToAdd) {
		
		String key = "regions." + region.getId() + ".residents";
	    List<String> current = FileManager.dataFileCfg.getStringList(key);
	    
	    String playerToAddUUID = playerToAdd.getUniqueId().toString();
	    
	    current.add(playerToAddUUID);
	    FileManager.dataFileCfg.set(key, current);
	    FileManager.saveDataFile();
	    
	    player.sendMessage(Main.gamePrefix + ChatColor.GOLD + playerToAdd.getName() + ChatColor.GREEN + " is no longer a resident.");
	    playerToAdd.sendMessage(Main.gamePrefix + ChatColor.GREEN + "You have been added to the town " + ChatColor.GOLD + town + ".");
	    return;
	}
	
	public static void kickPlayer(String playerToKick, ProtectedRegion region, Player player) {
		
		String key = "regions." + region.getId() + ".residents";
	    List<String> current = FileManager.dataFileCfg.getStringList(key);
	    
	    @SuppressWarnings("deprecation")
		String playerToKickUUID = Bukkit.getOfflinePlayer(playerToKick).getUniqueId().toString();
	    
	    current.remove(playerToKickUUID);
	    FileManager.dataFileCfg.set(key, current);
	    FileManager.saveDataFile();
	    
	    player.sendMessage(Main.gamePrefix + ChatColor.GOLD + playerToKick + ChatColor.GREEN + " is no longer a resident.");
	    return;
	    
	}
	
	@SuppressWarnings("deprecation")
	public static void deposit(String town, Player player, int amount) {
		
		if (Main.getEconomy().getBalance(player.getName()) >= amount) {

			Main.getEconomy().depositPlayer("town-" + town, amount);
			Main.getEconomy().withdrawPlayer(player.getName(), amount);
			player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Deposited " + ChatColor.GOLD + "$" + amount + ChatColor.GREEN + " into town bank.");
			return;
		}
		
		player.sendMessage(Main.gamePrefix + ChatColor.RED + "Insufficient funds!");
		return;
		
	}
	
	@SuppressWarnings("deprecation")
	public static void withdraw(String town, Player player, int amount) {
		
		if (Main.getEconomy().getBalance("town-" + town) >= amount) {

			Main.getEconomy().depositPlayer("town-" + town, amount);
			Main.getEconomy().withdrawPlayer("town-" + town, amount);
			player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Withdrew " + ChatColor.GOLD + "$" + amount + ChatColor.GREEN + " from town bank.");
			return;
		}
		
		player.sendMessage(Main.gamePrefix + ChatColor.RED + "Insufficient funds in town bank!");
		return;
		
		
		
	}
	
	public static void addDeputy(String town, Player player, ProtectedRegion region, Player deputy) {
		
		String key = "regions." + region.getId() + ".deputies";
	    List<String> current = FileManager.dataFileCfg.getStringList(key);
	    
	    current.add(deputy.getUniqueId().toString());
	    FileManager.dataFileCfg.set(key, current);
	    
		UUID playerUUID = ChatUtils.toUUID(player.getName());
	    
		DefaultDomain domain = region.getMembers();
		domain.addPlayer(playerUUID);
		region.setMembers(domain);
	    
	    FileManager.saveDataFile();
	    
	    player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Promoted " + ChatColor.GOLD + deputy + ChatColor.GREEN + " to deputy!");
	    return;
	    
	}
	
	public static void removeDeputy(String town, Player player, ProtectedRegion region, String deputy) {
		
	    @SuppressWarnings("deprecation")
	    UUID deputyUUID = Bukkit.getOfflinePlayer(deputy).getUniqueId();
		String deputyUUIDString = deputyUUID.toString();
		
		String key = "regions." + region.getId() + ".deputies";
	    List<String> current = FileManager.dataFileCfg.getStringList(key);
	    
	    current.remove(deputyUUIDString);
	    FileManager.dataFileCfg.set(key, current);
	    
		DefaultDomain domain = region.getMembers();
		domain.removePlayer(deputyUUID);
		region.setMembers(domain);
	    
	    FileManager.saveDataFile();
	    
	    player.sendMessage(Main.gamePrefix + ChatColor.GOLD + deputy + ChatColor.GREEN + " is no longer a deputy.");
	    return;
		
	}
	
	public static void delete(String town, ProtectedRegion region, Player player) {
		
		
		
	}

}
