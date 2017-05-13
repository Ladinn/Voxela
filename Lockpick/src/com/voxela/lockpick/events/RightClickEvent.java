package com.voxela.lockpick.events;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.voxela.lockpick.Main;
import com.voxela.lockpick.items.LockpickItem;
import com.voxela.lockpick.runnables.PickLock;
import com.voxela.lockpick.runnables.UserMap;

import net.md_5.bungee.api.ChatColor;

public class RightClickEvent implements Listener {
	
	@SuppressWarnings("serial")
	private ArrayList<Material> materials = new ArrayList<Material>(){
		{
			add(Material.ACACIA_DOOR);
			add(Material.BIRCH_DOOR);
			add(Material.DARK_OAK_DOOR);
			add(Material.JUNGLE_DOOR);
			add(Material.SPRUCE_DOOR);
			add(Material.TRAP_DOOR);
			add(Material.WOODEN_DOOR);
			add(Material.IRON_TRAPDOOR);
			add(Material.IRON_DOOR_BLOCK);
			add(Material.CHEST);
			add(Material.TRAPPED_CHEST);
			add(Material.BLACK_SHULKER_BOX);
			add(Material.BLUE_SHULKER_BOX);
			add(Material.BROWN_SHULKER_BOX);
			add(Material.CYAN_SHULKER_BOX);
			add(Material.GRAY_SHULKER_BOX);
			add(Material.GREEN_SHULKER_BOX);
			add(Material.LIGHT_BLUE_SHULKER_BOX);
			add(Material.LIME_SHULKER_BOX);
			add(Material.MAGENTA_SHULKER_BOX);
			add(Material.ORANGE_SHULKER_BOX);
			add(Material.PINK_SHULKER_BOX);
			add(Material.PURPLE_SHULKER_BOX);
			add(Material.ORANGE_SHULKER_BOX);
			add(Material.RED_SHULKER_BOX);
			add(Material.SILVER_SHULKER_BOX);
			add(Material.WHITE_SHULKER_BOX);
			add(Material.YELLOW_SHULKER_BOX);
		}
	};
	
	@EventHandler
	public void rightClick(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack item = player.getInventory().getItemInMainHand();
		
		if (UserMap.isLockpicking(player)) return;
				
		if ( (action == Action.RIGHT_CLICK_BLOCK)) {
						
			Block clickedBlock = event.getClickedBlock();
						
			for (Material mat : materials) {
			
				if (clickedBlock.getType().equals(mat)) {
					
					if (Main.getWorldGuard().canBuild(player, clickedBlock)) return;
					
					event.setCancelled(true);
					
					if ( (item.toString().toLowerCase().contains("air")) || !(LockpickItem.isItem(item)) ) {
						player.sendMessage(Main.gamePrefix + ChatColor.RED + "This thing is locked tight.");
						player.sendMessage(ChatColor.DARK_GRAY + "  - " + ChatColor.GRAY + "Try crafting a lockpick... Type " + ChatColor.DARK_AQUA + "/lockpick.");
						return;
					}					
					
					if (!(player.isSneaking())) {
						player.sendMessage(Main.gamePrefix + ChatColor.RED + "You must be crouching to use this.");
						return;
					}
					
					PickLock.run(player, clickedBlock, item, mat);
					
				}				
			}			
		}		
	}
}
