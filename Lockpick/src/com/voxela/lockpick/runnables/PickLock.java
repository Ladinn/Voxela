package com.voxela.lockpick.runnables;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;
import org.bukkit.scheduler.BukkitRunnable;

import com.voxela.lockpick.Main;
import com.voxela.lockpick.handlers.BarHandler;
import com.voxela.lockpick.items.LockpickItem;

import net.md_5.bungee.api.ChatColor;

public class PickLock {
				
	public static void run(Player player, Block clickedBlock, ItemStack item, Material mat) {
		
		System.out.print(Main.consolePrefix + player.getName() + " is attempting to lockpick...");
		
		UserMap.setLockpicking(player);
		
		player.sendMessage(Main.gamePrefix + ChatColor.GOLD + "Picking lock...");
		
		new BukkitRunnable() {
			
			// Get config values
			int time = Main.getInstance().getConfig().getInt("time");
			int chance = Main.getInstance().getConfig().getInt("chance");
			
			// Boss bar math
			double barTime = 1.0;
			double subtract = (0.001111111111 * time);
						
			Location original = player.getLocation();			
			BarHandler bar = new BarHandler(" ", BarColor.RED, BarStyle.SOLID);
									
			public void run() {
				
				bar.setText(ChatColor.GOLD + "Picking lock...");
				bar.setProgress(barTime);
				bar.addPlayer(player);
				
				Location loc = player.getLocation();
				World world = player.getWorld();
				ItemStack mainHand = player.getInventory().getItemInMainHand();
				
				if ( !(player.isSneaking()) || !(loc.getX() == original.getX()) || !(loc.getZ() == original.getZ()) || !(LockpickItem.isItem(mainHand)) ) {
					
					failure(player, item);
					player.sendMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.GRAY + "Next time, remain crouching and don't move.");
					bar.resetAllPlayersBars();
					this.cancel();
				}
				
				if (time == 0) {
					
					bar.resetAllPlayersBars();
					success(player, loc, clickedBlock, item, mat);					
					this.cancel();
				}

				int random = (int) (Math.random() * chance + 1);
				if (random == 1) {
					failure(player, item);
					bar.resetAllPlayersBars();
					this.cancel();
				}
				
				float randFloat = (float) (Math.random() * 5.0 + 1.75);
				world.playSound(loc, Sound.BLOCK_DISPENSER_LAUNCH, 0.75F, randFloat);
				world.playSound(loc, Sound.BLOCK_CHEST_LOCKED, 0.75F, 2.0F);
								
				world.spawnParticle(Particle.CRIT, clickedBlock.getLocation().add(0, 1, 0), random += 25);
				
				time--;
				barTime -= subtract;
				
			}
			
		}.runTaskTimer(Main.getInstance(), 20L, 20L);
	}
	
	public static void success(Player player, Location loc, Block clickedBlock, ItemStack item, Material mat) {
		
		System.out.print(Main.consolePrefix + player.getName() + " has successfully lockpicked a(n) " + mat.toString() + "!");
		
		World world = player.getWorld();
		String material = mat.toString().toLowerCase();
		
		UserMap.setNotLockpicking(player);
		
		if (material.contains("door")) {
		
			world.playSound(loc, Sound.BLOCK_IRON_DOOR_OPEN, 1.0F, 1.25F);
			
			BlockState state = clickedBlock.getState();
			Openable openable = (Openable) state.getData();
			
			if (openable.isOpen()) {
				openable.setOpen(false);
			} else openable.setOpen(true);

			state.setData((MaterialData) openable);
			state.update();	
			
		}
		
		if (material.contains("chest")) {
			
			world.playSound(loc, Sound.BLOCK_CHEST_OPEN, 1.25F, 1.0F);
			
			Chest chest = (Chest) clickedBlock.getState();
			InventoryHolder holder = chest.getInventory().getHolder();
			
			player.openInventory(holder.getInventory());
			
		}
		
		removeLockpick(player, item);
	}
	
	private static void failure(Player player, ItemStack item) {
		
		UserMap.setNotLockpicking(player);
		player.sendMessage(Main.gamePrefix + ChatColor.RED + "Lock picking failed!");
		removeLockpick(player, item);
		
	}
	
	private static void removeLockpick(Player player, ItemStack item) {
		
		int amount = item.getAmount();		
		item.setAmount(amount-=1);
		player.updateInventory();
		
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
		
	}
	
}
