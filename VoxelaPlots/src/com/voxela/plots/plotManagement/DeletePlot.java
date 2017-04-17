package com.voxela.plots.plotManagement;

import java.io.IOException;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.plots.Main;
import com.voxela.plots.utils.FileManager;
import com.voxela.plots.utils.WorldEditUtils;

@SuppressWarnings("deprecation")
public class DeletePlot {
	
	public static void delPlot(Player player, ProtectedRegion region) {
	
		// Paste schematic...
		long startMillis = System.currentTimeMillis();
		
		try {
			player.sendMessage(Main.gamePrefix + ChatColor.GRAY + "Restoring region to original schematic...");
			WorldEditUtils.restoreRegion(player.getWorld(), region);
		} catch (DataException | MaxChangedBlocksException | IOException e) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "Exception while restoring plot schematic!");
			e.printStackTrace();
		}
					
		// Delete WorldGuard region...
		RegionContainer container = Main.getWorldGuard().getRegionContainer();
		RegionManager regionManager = container.get(player.getWorld());

	    regionManager.removeRegion(region.getId());
	    
	    FileManager.dataFileCfg.set("regions." + region.getId(), null);
		FileManager.saveDataFile();
		
		long endMillis = System.currentTimeMillis();

		player.sendMessage(Main.gamePrefix + ChatColor.GRAY + "Took " + (endMillis - startMillis) + "ms.");
		player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Successfully deleted region: " + ChatColor.GOLD + region.getId());
	}
}
