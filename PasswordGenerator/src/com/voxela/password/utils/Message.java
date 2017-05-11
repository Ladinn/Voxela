package com.voxela.password.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import mkremins.fanciful.FancyMessage;

public class Message {
	
	public static void newMessage(Player player, String password) {
		
		new FancyMessage("  - ")
		.color(ChatColor.DARK_GRAY)
		.then("Click to copy.")
		.color(ChatColor.GRAY)
		.style(ChatColor.UNDERLINE)
		.style(ChatColor.ITALIC)
		.link("http://voxe.la/pass.php?password=" + password + "&name=" + player.getName())
		.send(player);
		
	}

}
