package com.voxela.lockpick.runnables;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class UserMap {
	
	private static HashMap<Player, Boolean> map = new HashMap<Player, Boolean>();
	
	public static boolean isLockpicking(Player player) {
		if (!(map.containsKey(player))) return false;
		return map.get(player);
	}
	
	public static void setLockpicking(Player player) {
		map.put(player, true);
	}
	
	public static void setNotLockpicking(Player player) {
		map.put(player, false);
	}

}
