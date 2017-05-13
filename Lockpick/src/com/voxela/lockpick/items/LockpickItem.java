package com.voxela.lockpick.items;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.voxela.lockpick.Main;

import net.md_5.bungee.api.ChatColor;

public class LockpickItem {
	
	private static ItemStack item;
	
	public static void init() {
		
		ItemStack lockpick = new ItemStack(Material.TRIPWIRE_HOOK, 1);
		ItemMeta meta = lockpick.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Lockpick");
		meta.setLore(Arrays.asList("Used to break open a", "sealed door or chest."));
		meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
		lockpick.setItemMeta(meta);
		
		item = lockpick;
		
		ShapedRecipe recipe = new ShapedRecipe(lockpick);
		
		recipe.shape(
				"-@#",
				"#@#",
				"#*#"
				);
		recipe.setIngredient('#', Material.AIR);
		recipe.setIngredient('@', Material.IRON_INGOT);
		recipe.setIngredient('*', Material.STICK);
		recipe.setIngredient('-', Material.DIAMOND);
		
		Main.getInstance().getServer().addRecipe(recipe);
		
	}
	
	public static boolean isItem(ItemStack hand) {
		
		item.setAmount(hand.getAmount());
		
		if (hand.equals(item)) return true;
		
		return false;
	}

}
