package com.voxela.towns.townManagement;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.World;
import org.bukkit.entity.Player;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.protection.managers.storage.StorageException;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.voxela.towns.Main;
import com.voxela.towns.utils.FileManager;
import com.voxela.towns.utils.WorldEditUtils;

public class CreateTown {
	
	@SuppressWarnings("deprecation")
	public static void createTown(Player player, World world, int price) {
		
		Selection sel = Main.getWorldEdit().getSelection(player);
		
		int townNum = FileManager.dataFileCfg.getInt("towncount");
			
		ProtectedCuboidRegion region = new ProtectedCuboidRegion(
				"town_" + townNum,
				new BlockVector(sel.getNativeMinimumPoint()),
				new BlockVector(sel.getNativeMaximumPoint())
		);
		
		// Create the region.
		Main.getWorldGuard().getRegionManager(player.getWorld()).addRegion(region);
		
		// Add 1 to data.yml.
		FileManager.dataFileCfg.set("towncount", townNum + 1);
		FileManager.saveDataFile();
		
		// Save the region as a schematic.
		WorldEditUtils.saveSelection(world, region);
		
		// Set the region flags.        
        region.setFlag(DefaultFlag.CHEST_ACCESS,StateFlag.State.DENY);     
        region.setFlag(DefaultFlag.CHEST_ACCESS.getRegionGroupFlag(), RegionGroup.NON_MEMBERS);
        region.setFlag(DefaultFlag.USE,StateFlag.State.DENY);     
        region.setFlag(DefaultFlag.USE.getRegionGroupFlag(), RegionGroup.NON_MEMBERS);
		
		region.setFlag(DefaultFlag.GREET_MESSAGE, Main.gamePrefix + ChatColor.GOLD + "Entering unowned town! " 
		+ ChatColor.GRAY + "$" + price + " per week." + ChatColor.DARK_GRAY + " - " + region.getId());
		region.setFlag(DefaultFlag.FAREWELL_MESSAGE, Main.gamePrefix + ChatColor.GOLD + "Leaving unowned town! " 
		+ ChatColor.GRAY + "$" + price + " per week." + ChatColor.DARK_GRAY + " - " + region.getId());
				
		try {
			Main.getWorldGuard().getGlobalRegionManager().get(player.getWorld()).save();
			player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Town created: " + ChatColor.GOLD + region.getId() + ". " + ChatColor.GREEN + "Price: " + ChatColor.GOLD + "$" + price + ".");
		} catch (StorageException e) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "Could not save town:" + region.getId());
			e.printStackTrace();
		}
				
		FileManager.dataFileCfg.set("regions." + region.getId() + ".price", price);
		FileManager.dataFileCfg.set("regions." + region.getId() + ".name", "unowned");
		FileManager.saveDataFile();

	}
}
