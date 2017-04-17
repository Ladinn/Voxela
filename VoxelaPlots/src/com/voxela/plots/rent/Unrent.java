package com.voxela.plots.rent;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.World;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.plots.Main;
import com.voxela.plots.utils.FileManager;
import com.voxela.plots.utils.WorldEditUtils;

import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("deprecation")
public class Unrent {
	
	public static void unrentMethod(ProtectedRegion region) {
		
		FileManager.dataFileCfg.set("regions." + region.getId() + ".renter", null);
		FileManager.dataFileCfg.set("regions." + region.getId() + ".rentuntil", null);
		FileManager.saveDataFile();
		
		region.setFlag(DefaultFlag.GREET_MESSAGE, Main.gamePrefix + ChatColor.GOLD + "Entering " + region.getId() + "." + ChatColor.RED + " Unowned! " 
				+ ChatColor.GRAY + ChatColor.ITALIC + "/plot info " + region.getId());
		region.setFlag(DefaultFlag.FAREWELL_MESSAGE, Main.gamePrefix + ChatColor.GOLD + "Leaving " + region.getId() + "." + ChatColor.RED + " Unowned! " 
				+ ChatColor.GRAY + ChatColor.ITALIC + "/plot info " + region.getId());

		region.getOwners().clear();
		region.getMembers().clear();
		
		World world = Bukkit.getWorld("world");
		
		WorldEditUtils.saveSelectionPreRestore(world, region);
		
		try {
			WorldEditUtils.restoreRegion(world, region);
		} catch (DataException | MaxChangedBlocksException | IOException e) {
			System.out.print(Main.consolePrefix + "Error restoring region: " + region.getId());
			e.printStackTrace();
        }
	}
}
