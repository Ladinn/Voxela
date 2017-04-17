package com.voxela.menu;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class Main extends JavaPlugin implements Listener {

	String consoleprefix = "[Voxela] ";
	String gameprefix = "§8[§bVoxela§8] ";

	String invname = "§9§lVoxela Networks §8- §0Menu";

	public void onEnable() {
		System.out.print(consoleprefix + "Menu has been enabled.");
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		getServer().getMessenger().registerOutgoingPluginChannel(this,
				"BungeeCord");
	}

	public void onDisable() {
		System.out.print(consoleprefix + "Menu has been disabled.");
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		Player player = (Player) sender;

		if ((sender instanceof Player) && (commandLabel.equalsIgnoreCase("s"))) {
			player.sendMessage(gameprefix + ChatColor.GREEN
					+ "Opened server menu.");
			Location loc = player.getLocation();
			player.playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 5.0F, 2.0F);
			Menu.createMenu(player);
		} else {
			System.out.print(consoleprefix + "Command cannot be used in server console.");
		}
		return true;
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent event) {
		if (event.getInventory().getName() != invname)
			return;
		if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Choose")) {
			event.setCancelled(true);
			event.getWhoClicked().sendMessage(
					gameprefix + "§cChoose a server by clicking on its icon!");
		}
		if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Hub")) {
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();
			if (event.getCurrentItem().getItemMeta().getLore().get(0).contains("On")) {
				ByteArrayDataOutput out = ByteStreams.newDataOutput();
				out.writeUTF("Connect");
				out.writeUTF("Hub");
				player.sendPluginMessage((Plugin) this, "BungeeCord",
						out.toByteArray());
				event.getWhoClicked().closeInventory();
				player.sendMessage(this.gameprefix + "§dConnecting to server: " + event.getCurrentItem().getItemMeta().getDisplayName() + ".");	
				Location loc = player.getLocation();
				player.playSound(loc, Sound.BLOCK_PORTAL_TRIGGER , 5.0F, 1.0F);
			} else {
				player.sendMessage(this.gameprefix + "§dThe " + event.getCurrentItem().getItemMeta().getDisplayName() + "§d server is offline!");	
				Location loc = player.getLocation();
				player.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP , 5.0F, 0.1F);
			}
		}
		if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Economy")) {
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();
			if (event.getCurrentItem().getItemMeta().getLore().get(0).contains("On")) {
				ByteArrayDataOutput out = ByteStreams.newDataOutput();
				out.writeUTF("Connect");
				out.writeUTF("RP");
				player.sendPluginMessage((Plugin) this, "BungeeCord",
						out.toByteArray());
				event.getWhoClicked().closeInventory();
				player.sendMessage(this.gameprefix + "§dConnecting to server: " + event.getCurrentItem().getItemMeta().getDisplayName() + ".");	
				Location loc = player.getLocation();
				player.playSound(loc, Sound.BLOCK_PORTAL_TRIGGER , 5.0F, 1.0F);
			} else {
				player.sendMessage(this.gameprefix + "§dThe " + event.getCurrentItem().getItemMeta().getDisplayName() + "§d server is offline!");	
				Location loc = player.getLocation();
				player.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP , 5.0F, 0.1F);
			}
		}
		if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Creative")) {
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();
			if (event.getCurrentItem().getItemMeta().getLore().get(0).contains("On")) {
				ByteArrayDataOutput out = ByteStreams.newDataOutput();
				out.writeUTF("Connect");
				out.writeUTF("Creative");
				player.sendPluginMessage((Plugin) this, "BungeeCord",
					out.toByteArray());
				event.getWhoClicked().closeInventory();
				player.sendMessage(this.gameprefix + "§dConnecting to server: " + event.getCurrentItem().getItemMeta().getDisplayName() + ".");	
				Location loc = player.getLocation();
				player.playSound(loc, Sound.BLOCK_PORTAL_TRIGGER , 5.0F, 1.0F);
			} else {
				player.sendMessage(this.gameprefix + "§dThe " + event.getCurrentItem().getItemMeta().getDisplayName() + "§d server is offline!");	
				Location loc = player.getLocation();
				player.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP , 5.0F, 0.1F);
			}
		}
		if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Survival")) {
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();
			if (event.getCurrentItem().getItemMeta().getLore().get(0).contains("On")) {
				ByteArrayDataOutput out = ByteStreams.newDataOutput();
				out.writeUTF("Connect");
				out.writeUTF("Survival");
				player.sendPluginMessage((Plugin) this, "BungeeCord",
						out.toByteArray());
				event.getWhoClicked().closeInventory();
				player.sendMessage(this.gameprefix + "§dConnecting to server: " + event.getCurrentItem().getItemMeta().getDisplayName() + ".");	
				Location loc = player.getLocation();
				player.playSound(loc, Sound.BLOCK_PORTAL_TRIGGER , 5.0F, 1.0F);
			} else {
				player.sendMessage(this.gameprefix + "§dThe " + event.getCurrentItem().getItemMeta().getDisplayName() + "§d server is offline!");	
				Location loc = player.getLocation();
				player.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP , 5.0F, 0.1F);
			}
		}
	}
}