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
import com.voxela.gov.checker.CheckMandate;
import com.voxela.gov.handlers.InterfaceHandler;

import net.md_5.bungee.api.ChatColor;

public class MandateCommand {

	public static void mandateGui(Player player) {

		Inventory inv = Bukkit.createInventory(player, 9, InterfaceHandler.mandateName());

		String fed = CheckFed.getPlayerFed(player);

		inv.addItem(createItem("Transportation Subsidy",
				Arrays.asList(
						ChatColor.GREEN + "Allows players in provinces you control",
						ChatColor.GREEN + "to use " + ChatColor.GOLD + "/tpa" + ChatColor.GREEN + " to teleport to",
						ChatColor.GREEN + "other players.",
						ChatColor.DARK_AQUA + "Tax Increase: " + ChatColor.GRAY + "18%"), fed));

		inv.addItem(createItem("Universal Healthcare",
				Arrays.asList(
						ChatColor.GREEN + "When a player dies, they respawn",
						ChatColor.GREEN + "in a nearby hospital in your province",
						ChatColor.GREEN + "as opposed to spawn.",
						ChatColor.DARK_AQUA + "Tax Increase: " + ChatColor.GRAY + "10%"), fed));
		
		inv.addItem(createItem("Department of Housing",
				Arrays.asList(
						ChatColor.GREEN + "Allows players in provinces you control",
						ChatColor.GREEN + "to use " + ChatColor.GOLD + "/home" + ChatColor.GREEN + " and " + ChatColor.GOLD + "/sethome",
						ChatColor.GREEN + "to teleport to their residence.",
						ChatColor.DARK_AQUA + "Tax Increase: " + ChatColor.GRAY + "14%"), fed));
		
		player.openInventory(inv);
		player.sendMessage(Main.gamePrefix + ChatColor.GREEN + "Opening mandate console...");
	}

	private static ItemStack createItem(String name, List<String> lore, String fed) {

		String mandate = name;
		
		ItemStack item = null;

		if (CheckMandate.isMandateEnabled(fed, mandate)) {
			item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
		} else {
			item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
		}

		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);
		meta.setLore(lore);

		item.setItemMeta(meta);

		return item;

	}
}
