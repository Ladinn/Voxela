package com.voxela.plots.plotManagement;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.protection.managers.storage.StorageException;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.voxela.plots.Main;
import com.voxela.plots.utils.FileManager;
import com.voxela.plots.utils.WorldEditUtils;

public class CreatePlot {
	
	@SuppressWarnings("deprecation")
	public static void createPlot(Player player, int price) {
		
		Selection sel = Main.getWorldEdit().getSelection(player);
		
		int plotNum = FileManager.dataFileCfg.getInt("plotcount");
			
		ProtectedCuboidRegion region = new ProtectedCuboidRegion(
				"plot_" + plotNum,
				new BlockVector(sel.getNativeMinimumPoint()),
				new BlockVector(sel.getNativeMaximumPoint())
		);
		
		// Create the region.
		Main.getWorldGuard().getRegionManager(player.getWorld()).addRegion(region);
		
		// Add 1 to data.yml.
		FileManager.dataFileCfg.set("plotcount", plotNum + 1);
		FileManager.saveDataFile();
		
		// Save the region as a schematic.
		WorldEditUtils.saveSelection(region, player);
		
		// Set the region flags.        
        region.setFlag(DefaultFlag.CHEST_ACCESS,StateFlag.State.DENY);     
        region.setFlag(DefaultFlag.CHEST_ACCESS.getRegionGroupFlag(), RegionGroup.NON_MEMBERS);
        region.setFlag(DefaultFlag.USE,StateFlag.State.DENY);     
        region.setFlag(DefaultFlag.USE.getRegionGroupFlag(), RegionGroup.NON_MEMBERS);
		
		region.setFlag(DefaultFlag.GREET_MESSAGE, Main.gamePrefix + ChatColor.GOLD + "Entering " + region.getId() + "." + ChatColor.RED + " Unowned!");
		region.setFlag(DefaultFlag.FAREWELL_MESSAGE, Main.gamePrefix + ChatColor.GOLD + "Leaving " + region.getId() + "." + ChatColor.RED + " Unowned!");
		
		try {
			Main.getWorldGuard().getGlobalRegionManager().get(player.getWorld()).save();
			player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Plot created: " + ChatColor.GOLD + region.getId());
		} catch (StorageException e) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "Could not save region:" + region.getId());
			e.printStackTrace();
		}
		
		// Create tangible outline.
		FileManager.dataFileCfg.set("regions." + region.getId() + ".price", price);
		FileManager.saveDataFile();

	}
}
