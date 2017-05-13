package com.voxela.lockpick.handlers;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BarHandler {

	private BossBar b;
	
	public BarHandler(String text, BarColor color, BarStyle style) { this.b = Bukkit.createBossBar(text, color, style, new org.bukkit.boss.BarFlag[0]); }

    public List<Player> getPlayers() { return this.b.getPlayers(); }
		
	public void setText(String text) { this.b.setTitle(text); }
	
	public void setColor(BarColor color) { this.b.setColor(color); }
	
	public void setStyle(BarStyle style) { this.b.setStyle(style); }
	
	public void setProgress(double d) { this.b.setProgress(d); }
	
	public void addPlayer(Player player) { this.b.addPlayer(player); }
		
    public void resetAllPlayersBars() { for (Player player : Bukkit.getOnlinePlayers()) this.b.removePlayer(player); }
}
