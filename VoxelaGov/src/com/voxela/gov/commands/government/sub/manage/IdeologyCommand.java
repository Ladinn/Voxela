package com.voxela.gov.commands.government.sub.manage;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.voxela.gov.Main;
import com.voxela.gov.checker.CheckFed;
import com.voxela.gov.handlers.InterfaceHandler;

import net.md_5.bungee.api.ChatColor;

public class IdeologyCommand {
	
	public static void ideology(Player player, String[] args) {
		
		String fed = CheckFed.getPlayerFed(player);
		
		if (!(CheckFed.getChief(fed).equalsIgnoreCase(player.getUniqueId().toString()))) {
			player.sendMessage(Main.gamePrefix + ChatColor.RED + "This can only be done by the federation chief.");
			return;
		}
		
		player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Opening ideology selector...");
		
		ideologyGui(player);
		return;
	}

	public static void ideologyGui(Player player) {

		Inventory inv = Bukkit.createInventory(player, 9, InterfaceHandler.ideologyName());

		String fed = CheckFed.getPlayerFed(player);
		
		inv.addItem(createItem("Monarchy", Arrays.asList(
				ChatColor.GOLD + "The Kingdom of " + fed,
				"",
				ChatColor.GRAY + "Effect: " + ChatColor.GREEN + "Luck"
				)));
		
        inv.addItem(createItem("Republic", Arrays.asList(
				ChatColor.GOLD + "The Democratic Republic of " + fed,
				"",
				ChatColor.GRAY + "Effect: " + ChatColor.GREEN + "+4 Health"
				)));

        inv.addItem(createItem("Democracy", Arrays.asList(
				ChatColor.GOLD + "The United Federation of " + fed,
				"",
				ChatColor.GRAY + "Effect: " + ChatColor.GREEN + "No Hunger"
				)));

        inv.addItem(createItem("Communism", Arrays.asList(
				ChatColor.GOLD + "The People's Republic of " + fed,
				"",
				ChatColor.GRAY + "Effect: " + ChatColor.GREEN + "Haste (Mining Speed)"
				)));

        inv.addItem(createItem("Imperialism", Arrays.asList(
				ChatColor.GOLD + "The Empire of " + fed,
				"",
				ChatColor.GRAY + "Effect: " + ChatColor.GREEN + "Night Vision"
				)));

        inv.addItem(createItem("Caliphate", Arrays.asList(
				ChatColor.GOLD + "The Sharia Caliphate of " + fed,
				"",
				ChatColor.GRAY + "Effect: " + ChatColor.GREEN + "Fire Resistance"
				)));

        inv.addItem(createItem("Ecclesiology", Arrays.asList(
				ChatColor.GOLD + "The Holy See of " + fed,
				"",
				ChatColor.GRAY + "Effect: " + ChatColor.GREEN + "Regeneration"
				)));
        
        inv.addItem(createItem("National Socialism", Arrays.asList(
				ChatColor.GOLD + "Das Reich von " + fed,
				"",
				ChatColor.GRAY + "Effect: " + ChatColor.GREEN + "Speed"
				)));

        inv.addItem(createItem("Despotism", Arrays.asList(
				ChatColor.GOLD + "The Totalitarian State of " + fed,
				"",
				ChatColor.GRAY + "Effect: " + ChatColor.GREEN + "Damage Resistance"
				)));
        
		player.openInventory(inv);
	}

	private static ItemStack createItem(String name, List<String> lore) {

		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);
		meta.setLore(lore);
		
		item.setItemMeta(meta);

		return item;

	}

}
