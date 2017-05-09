package com.voxela.gov.checker;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.gov.Main;
import com.voxela.gov.utils.FileManager;

public class CheckProvince {
	
	public static ArrayList<String> getProvinces() {
		
		ArrayList<String> provinces = new ArrayList<String>();
		provinces.add("penobscot");
		provinces.add("persolum");
		provinces.add("schoodic");
		provinces.add("torrhen");
		provinces.add("thurmont");
		provinces.add("rhinefield");
		provinces.add("atacama");
		
		return provinces;
	}
	
	public static String[] checkProvince(Player player) {
		
		Location playerLoc = player.getLocation();
		World playerWorld = player.getWorld();
		
		ApplicableRegionSet set = Main.getWorldGuard().getRegionManager(playerWorld).getApplicableRegions(playerLoc);
		
		for (ProtectedRegion each : set) {
			
			if (each.getId().equals("prov_penobscot")) { return new String[] { "Penobscot", "Penobscot" }; }
			if (each.getId().equals("prov_persolumcrater")) { return new String[] { "Persolum Crater", "Persolum" }; }
			if (each.getId().equals("prov_schoodicglacier")) { return new String[] { "Schoodic Glacier", "Schoodic" }; }
			if (each.getId().equals("prov_torrhenvale")) { return new String[] { "Torrhen Vale", "Torrhen" }; }
			if (each.getId().equals("prov_thurmontplains")) { return new String[] { "Thurmont Plains", "Thurmont" }; }
			if (each.getId().equals("prov_rhinefield")) { return new String[] { "Rhinefield", "Rhinefield" }; }
			if (each.getId().equals("prov_atacamaoutlands")) { return new String[] { "Atacama Outlands", "Atacama" }; }
			
		}
		
		return new String[] { "None!", "none" };
		
	}
	
	public static String stringToProvince(String province) {
		
		if (province.equalsIgnoreCase("penobscot")) { return "Penobscot"; }
		if (province.equalsIgnoreCase("persolum") || (province.equalsIgnoreCase("persolumcrater"))) { return "Persolum Crater"; }
		if (province.equalsIgnoreCase("schoodic") || (province.equalsIgnoreCase("schoodicglacier"))) { return "Schoodic Glacier"; }
		if (province.equalsIgnoreCase("torrhen") || (province.equalsIgnoreCase("torrhenvale"))) { return "Torrhen Vale"; }
		if (province.equalsIgnoreCase("thurmont") || (province.equalsIgnoreCase("thurmontplains"))) { return "Thurmont Plains"; }
		if (province.equalsIgnoreCase("rhinefield")) { return "Rhinefield"; }
		if (province.equalsIgnoreCase("atacama") || (province.equalsIgnoreCase("atacamaoutlands"))) { return "Atacama Outlands"; }
		return null;
		
	}
	
	public static String stringToDataFile(String province) {
		
		if (province.equalsIgnoreCase("penobscot")) { return "penobscot"; }
		if (province.equalsIgnoreCase("persolum") || (province.equalsIgnoreCase("persolumcrater"))) { return "persolum"; }
		if (province.equalsIgnoreCase("schoodic") || (province.equalsIgnoreCase("schoodicglacier"))) { return "schoodic"; }
		if (province.equalsIgnoreCase("torrhen") || (province.equalsIgnoreCase("torrhenvale"))) { return "torrhen"; }
		if (province.equalsIgnoreCase("thurmont") || (province.equalsIgnoreCase("thurmontplains"))) { return "thurmont"; }
		if (province.equalsIgnoreCase("rhinefield")) { return "rhinefield"; }
		if (province.equalsIgnoreCase("atacama") || (province.equalsIgnoreCase("atacamaoutlands"))) { return "atacama"; }
		return null;
		
	}
	
	public static String getFed(String province) {
		
		return (String) FileManager.dataFileCfg.get("provinces." + province + ".federation");
		
	}
	
	public static ArrayList<String> getProvincesFedControls(String fed) {
		
		ArrayList<String> provinces = new ArrayList<String>();
		
		if (getFed("penobscot").equals(fed)) provinces.add("penobscot");
		if (getFed("persolum").equals(fed)) provinces.add("persolum");
		if (getFed("schoodic").equals(fed)) provinces.add("schoodic");
		if (getFed("torrhen").equals(fed)) provinces.add("torrhen");
		if (getFed("thurmont").equals(fed)) provinces.add("thurmont");
		if (getFed("rhinefield").equals(fed)) provinces.add("rhinefield");
		if (getFed("atacama").equals(fed)) provinces.add("atacama");

		return provinces;
	}
	
	public static ArrayList<Player> getPlayersInProvince(String province) {

		ArrayList<Player> online = new ArrayList<Player>();

		for (Player player : Bukkit.getOnlinePlayers()) {
			
			Location loc = player.getLocation();
			World world = player.getWorld();
			
			if (Main.getWorldGuard().getRegionManager(world).getApplicableRegions(loc).getRegions().toString().contains(province)) {
				online.add(player);			
			}
		}
		
		return online;
	}

}
