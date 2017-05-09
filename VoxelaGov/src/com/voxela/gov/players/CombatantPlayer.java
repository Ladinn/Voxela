package com.voxela.gov.players;

import java.util.HashMap;

public class CombatantPlayer {
	
	private static HashMap<String, Integer> combatantMap = new HashMap<String, Integer>();
	
	public static void setCombatant(String player) { combatantMap.put(player, 1); }
	
	public static void delCombatant(String player) { combatantMap.remove(player); }
	
	public static void clearCombatants() { combatantMap.clear(); }
	
	public static boolean isCombatant(String player) {
		
		if (combatantMap.get(player) == null) return false;		
		return true;
		
	}
}
