package com.voxela.towns.mayor;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.World;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.towns.Main;
import com.voxela.towns.townManagement.DeleteTown;
import com.voxela.towns.utils.FileManager;
import com.voxela.towns.utils.WorldEditUtils;

import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("deprecation")
public class TownUnrent {
	
	public static void unrentMethod(ProtectedRegion region) {
		
		FileManager.dataFileCfg.set("regions." + region.getId() + ".name", "unowned");
		FileManager.dataFileCfg.set("regions." + region.getId() + ".renter", null);
		FileManager.dataFileCfg.set("regions." + region.getId() + ".rentuntil", null);
		FileManager.saveDataFile();
		
		int price = FileManager.dataFileCfg.getInt("regions." + region.getId() + ".price");
		
		region.setFlag(DefaultFlag.GREET_MESSAGE, Main.gamePrefix + ChatColor.GOLD + "Entering unowned town! " 
		+ ChatColor.GRAY + "$" + price + ChatColor.DARK_GRAY + " - " + region.getId());
		region.setFlag(DefaultFlag.FAREWELL_MESSAGE, Main.gamePrefix + ChatColor.GOLD + "Leaving unowned town! " 
		+ ChatColor.GRAY + "$" + price + ChatColor.DARK_GRAY + " - " + region.getId());

		region.getOwners().clear();
		region.getMembers().clear();
		
		DeleteTown.deleteChildren(region);
		
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
