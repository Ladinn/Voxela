package com.voxela.lockpick.items;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.voxela.lockpick.Main;

import net.md_5.bungee.api.ChatColor;

public class MasterKeyItem {

	private static ItemStack item;
	
	public static void init() {
		
		int itemId = Main.getInstance().getConfig().getInt("lockpick-item");
		
		@SuppressWarnings("deprecation")
		ItemStack masterKey = new ItemStack(Material.getMaterial(itemId), 1);
		ItemMeta meta = masterKey.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "Master Key");
		meta.setLore(Arrays.asList("Used to open any", "sealed door or chest."));
		meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
		masterKey.setItemMeta(meta);
		
		item = masterKey;
	}
	
	public static boolean isItem(ItemStack hand) {
		
		item.setAmount(hand.getAmount());
		
		if (hand.equals(item)) return true;
		
		return false;
	}
	
}
