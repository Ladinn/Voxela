package com.voxela.password;

import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {
	
	public static String consolePrefix = "[Voxela] ";
	public static String gamePrefix = ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "Voxela" + ChatColor.DARK_GRAY + "] ";
	
	@Override
	public void onEnable() {
		System.out.print(consolePrefix + "Random Password Generator has been enabled.");
		this.getCommand("password").setExecutor(new PasswordCommand(this));
	}

	public void onDisable() {
		System.out.print(consolePrefix + "Random Password Generator has been disabled.");
	}

}
