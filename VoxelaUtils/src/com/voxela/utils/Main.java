package com.voxela.utils;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	static String consoleprefix = "[Voxela] ";
	static String gameprefix = "§8[§bVoxela§8] ";
	
	@Override
	public void onEnable() {
		System.out.print(consoleprefix + "Utils has been enabled.");
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}

	public void onDisable() {
		System.out.print(consoleprefix + "Utils has been disabled.");
	}
	
    @EventHandler
    public void onTeleport(PlayerTeleportEvent event){
    	Player player = event.getPlayer();
        if (event.getCause() == TeleportCause.ENDER_PEARL && !player.getWorld().getName().equals("world_the_end")) {
            event.setCancelled(true);
            player.sendMessage(gameprefix + ChatColor.RED + "You can only use this in the End!");
        }
        if (event.getCause() == TeleportCause.CHORUS_FRUIT && !player.getWorld().getName().equals("world_the_end")) {
        	event.setCancelled(true);
            player.sendMessage(gameprefix + ChatColor.RED + "You can only use this in the End!");
        }
    }
}