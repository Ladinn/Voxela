package com.voxela.gov.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.voxela.gov.players.primeminister.CandidatePlayer;

import net.md_5.bungee.api.ChatColor;

public class InterfaceHandler {

	public static String voteName() { return ChatColor.BLUE + "" + ChatColor.BOLD + "Vote for Prime Minister";	}
	
	public static String titleName() { return ChatColor.DARK_BLUE + "Federation: Title Selector";	}
	
	public static String ideologyName() { return ChatColor.DARK_BLUE + "Federation: Ideology Selector";	}
	
	public static String mandateName() { return ChatColor.DARK_BLUE + "Federation: Mandate Console";	}
		
    public static void vote() {

    	for (Player player : Bukkit.getServer().getOnlinePlayers()) {
    		
            Inventory inv = Bukkit.createInventory(player, round(), voteName());

            for (Player candidate : CandidatePlayer.getCandidates()) inv.addItem(createSkull(candidate.getName()));

            player.openInventory(inv);   		
    	}
    }
    
    public static ItemStack createSkull(String name) {

        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        meta.setOwner(name);
        meta.setDisplayName(name);

        item.setItemMeta(meta);

        return item;
    }
	
    public static Integer round() {
    	
    	int size = CandidatePlayer.candidateMap.size();

        if (size <= 8) return 9;

        else if (size > 8 && size <= 17) return 18;

        else if (size > 17 && size <= 26) return 27;

        else if (size > 26 && size <= 35) return 36;

        else if (size > 36 && size <= 44) return 45;

        else if (size > 44 && size <= 53) return 54;

        return 54;

    }

}
