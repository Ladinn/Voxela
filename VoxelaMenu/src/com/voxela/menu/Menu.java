package com.voxela.menu;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Menu {
	
	static String invname = "§9§lVoxela Networks §8- §0Menu";

	public static void createMenu(Player player) {
		
		Inventory inv = Bukkit.getServer().createInventory(null, 27, invname);

		// Green / red glass based on server state.
		
		byte hubOnlineByte = 14; // Red if server is offline.
		String hubOnline = "§cOffline!";
		if (CheckServer.hubServerCheck() == true) {
			hubOnlineByte = (byte) 5; // Green if server is online.
			hubOnline = "§aOnline!";
		}
		byte creativeOnlineByte = 14; // Red if server is offline.
		String creativeOnline = "§cOffline!";
		if (CheckServer.creativeServerCheck() == true) {
			creativeOnlineByte = (byte) 5; // Green if server is online.
			creativeOnline = "§aOnline!";
		}
		byte econOnlineByte = 14; // Red if server is offline.
		String econOnline = "§cOffline!";
		if (CheckServer.econServerCheck() == true) {
			econOnlineByte = (byte) 5; // Green if server is online.
			econOnline = "§aOnline!";
		}
		byte survivalOnlineByte = 14; // Red if server is offline.
		String survivalOnline = "§cOffline!";
		if (CheckServer.survivalServerCheck() == true) {
			survivalOnlineByte = (byte) 5; // Green if server is online.
			survivalOnline = "§aOnline!";
		}

		// Create black glass pane as placeholder.
		ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
		ItemMeta placeholderMeta = placeholder.getItemMeta();
		placeholderMeta.setDisplayName("§8Choose a server!");
		placeholder.setItemMeta(placeholderMeta);

		// Create item 1, Hub server.
		ItemStack item1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, hubOnlineByte);
		ItemMeta item1Meta = item1.getItemMeta();
		ArrayList<String> item1Lore = new ArrayList<String>();
		item1Meta.setDisplayName("§b§lHub");
		item1Lore.add(hubOnline);
		item1Meta.setLore(item1Lore);
		item1.setItemMeta(item1Meta);
		
		// Create item 2, Economy server.
		ItemStack item2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, econOnlineByte);
		ItemMeta item2Meta = item1.getItemMeta();
		ArrayList<String> item2Lore = new ArrayList<String>();
		item2Meta.setDisplayName("§b§lEconomy RP");
		item2Lore.add(econOnline);
		item2Meta.setLore(item2Lore);
		item2.setItemMeta(item2Meta);

		// Create item 3, Creative server.
		ItemStack item3 = new ItemStack(Material.STAINED_GLASS_PANE, 1, creativeOnlineByte);
		ItemMeta item3Meta = item1.getItemMeta();
		ArrayList<String> item3Lore = new ArrayList<String>();
		item3Meta.setDisplayName("§b§lCreative");
		item3Lore.add(creativeOnline);
		item3Meta.setLore(item3Lore);
		item3.setItemMeta(item3Meta);
		
		// Create item 4, Survival server.
		ItemStack item4 = new ItemStack(Material.STAINED_GLASS_PANE, 1, survivalOnlineByte);
		ItemMeta item4Meta = item1.getItemMeta();
		ArrayList<String> item4Lore = new ArrayList<String>();
		item4Meta.setDisplayName("§b§lSurvival");
		item4Lore.add(survivalOnline);
		item4Meta.setLore(item4Lore);
		item4.setItemMeta(item4Meta);
		
		
		inv.setItem(0, placeholder);
		inv.setItem(1, placeholder);
		inv.setItem(2, placeholder);
		inv.setItem(3, placeholder);
		inv.setItem(4, placeholder);
		inv.setItem(5, placeholder);
		inv.setItem(6, placeholder);
		inv.setItem(7, placeholder);
		inv.setItem(8, placeholder);
		inv.setItem(9, placeholder);
		inv.setItem(10, item1);
		inv.setItem(11, placeholder);
		inv.setItem(12, item2);
		inv.setItem(13, placeholder);
		inv.setItem(14, item3);
		inv.setItem(15, placeholder);
		inv.setItem(16, item4);
		inv.setItem(17, placeholder);
		inv.setItem(18, placeholder);
		inv.setItem(19, placeholder);
		inv.setItem(20, placeholder);
		inv.setItem(21, placeholder);
		inv.setItem(22, placeholder);
		inv.setItem(23, placeholder);
		inv.setItem(24, placeholder);
		inv.setItem(25, placeholder);
		inv.setItem(26, placeholder);
		
		player.openInventory(inv);
	}
}
