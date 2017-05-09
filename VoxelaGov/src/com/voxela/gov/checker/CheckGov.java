package com.voxela.gov.checker;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.voxela.gov.players.PolicePlayer;
import com.voxela.gov.players.primeminister.PrimeMinisterPlayer;

public class CheckGov {
	
	public static Player getPrimeMinister() { return PrimeMinisterPlayer.getPrimeMinister(); }
		
	public static String policeList() {

		String none = "None!";
		
		ArrayList<Player> policeArray = PolicePlayer.getPolice();
		
		if (policeArray.isEmpty()) return none;
		
		String police = "";
		
        for (Player player : policeArray) police += player.getName() + ", ";
        
        if (police.equals("")) return none;
        
        return police;
	}

}
