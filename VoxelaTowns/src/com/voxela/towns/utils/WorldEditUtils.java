package com.voxela.towns.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.towns.Main;

@SuppressWarnings("deprecation")
public class WorldEditUtils {
	
	public static void saveSelection(World world, ProtectedRegion region) {
		
		EditSession es = new EditSession(new BukkitWorld(world),Integer.MAX_VALUE);
		Vector origin = new Vector(region.getMinimumPoint().getBlockX(), region.getMinimumPoint().getBlockY(), region.getMinimumPoint().getBlockZ());
		Vector size = (new Vector(region.getMaximumPoint().getBlockX(), region.getMaximumPoint().getBlockY(), region.getMaximumPoint().getBlockZ()).subtract(origin)).add(new Vector(1, 1, 1));			
		CuboidClipboard clipboard = new CuboidClipboard(size, origin);
		
		clipboard.copy(es);
		
		File saveFile = new File(Main.getInstance().getDataFolder() + File.separator + "schematics" + File.separator + region.getId() + ".schematic");

		try {
			SchematicFormat.MCEDIT.save(clipboard, saveFile);
		} catch (DataException | IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void saveSelectionPreRestore(World world, ProtectedRegion region) {
		
		EditSession es = new EditSession(new BukkitWorld(world),Integer.MAX_VALUE);
		Vector origin = new Vector(region.getMinimumPoint().getBlockX(), region.getMinimumPoint().getBlockY(), region.getMinimumPoint().getBlockZ());
		Vector size = (new Vector(region.getMaximumPoint().getBlockX(), region.getMaximumPoint().getBlockY(), region.getMaximumPoint().getBlockZ()).subtract(origin)).add(new Vector(1, 1, 1));			
		CuboidClipboard clipboard = new CuboidClipboard(size, origin);
		
		clipboard.copy(es);
		
		File saveFile = new File(Main.getInstance().getDataFolder() + File.separator + "schematics" + File.separator + region.getId() + "-RESTORE.schematic");

		Bukkit.getScheduler().runTaskAsynchronously(
				Main.getInstance(), new Runnable() {
			
				@Override
				public void run() {
					
					try {
						SchematicFormat.MCEDIT.save(clipboard, saveFile);
					} catch (DataException | IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		);
		
	}
	
	public static void restoreRegion(World world, ProtectedRegion region) throws DataException, IOException, MaxChangedBlocksException {
		
		EditSession es = new EditSession(new BukkitWorld(world), Integer.MAX_VALUE);
		Vector origin = new Vector(region.getMinimumPoint().getBlockX(), region.getMinimumPoint().getBlockY(), region.getMinimumPoint().getBlockZ());
		File regionSchem = new File(Main.getInstance().getDataFolder() + File.separator + "schematics" + File.separator + region.getId() + ".schematic");
		
		Bukkit.getScheduler().runTaskAsynchronously(
				Main.getInstance(), new Runnable() {
			
				@Override
				public void run() {
					
					try {
						CuboidClipboard cc = CuboidClipboard.loadSchematic(regionSchem);
						cc.paste(es, origin, false);
						System.out.print(Main.consolePrefix + "Pasted schematic for region " + region.getId());
					} catch (com.sk89q.worldedit.world.DataException | IOException | MaxChangedBlocksException e) {
						System.out.print(Main.consolePrefix + "DataException while loading schematic!");
						e.printStackTrace();
					}		
				}
			}
		);
	}
	
}
