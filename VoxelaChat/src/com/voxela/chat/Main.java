package com.voxela.chat;

import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.voxela.chat.commands.GlobalChatCommand;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.chat.Chat;

public class Main extends JavaPlugin implements Listener {
	
	private static Main instance;
    private static Chat chat = null;

	public static String consolePrefix = "[VoxelaChat] ";
	public static String gamePrefix = ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "Voxela" + ChatColor.DARK_GRAY + "] ";
	
	@Override
	public void onEnable() {
		System.out.print(consolePrefix + "Chat has been enabled.");
		instance = this;

		this.getCommand("g").setExecutor(new GlobalChatCommand(this));
        this.getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(), getInstance());

		setupChat();
	}

	public void onDisable() {
		System.out.print(consolePrefix + "Chat has been disabled.");
	}
	
	public static Main getInstance() {
		return instance;
	}
    
    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }
    
    public static Chat getChat() {
        return chat;
    }
    
}
