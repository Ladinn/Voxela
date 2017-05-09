package com.voxela.gov.utils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.voxela.gov.Main;

public class FileManager {

	public static File dataFile = new File(Main.getInstance().getDataFolder() + File.separator + "data.yml");
	public static FileConfiguration dataFileCfg = YamlConfiguration.loadConfiguration(dataFile);
	
	public static void loadFiles() {
		
		Main.getInstance().getConfig().options().header("Voxela Government - Configuration");
		Main.getInstance().getConfig().options().copyHeader();
		Main.getInstance().getConfig().addDefault("elections", true);
		Main.getInstance().getConfig().addDefault("runfee", 75);
		Main.getInstance().getConfig().addDefault("fedprice", 2500);
		Main.getInstance().getConfig().addDefault("servertax", 5);
		Main.getInstance().getConfig().addDefault("maxtaxrate", 20);
		Main.getInstance().getConfig().options().copyDefaults(true);
		Main.getInstance().saveConfig();
		
		if (!dataFile.exists()) {
			try {
				System.out.print(Main.consolePrefix + "Creating data file...");
				dataFile.createNewFile();
				dataFileCfg.options().header("Voxela Government - Data File");
				dataFileCfg.options().copyHeader();
				
				dataFileCfg.addDefault("federations.Acadia.chief", "");
				dataFileCfg.addDefault("federations.Acadia.ideology", "Monarchy");
				dataFileCfg.addDefault("federations.Acadia.taxrate", 5);
				dataFileCfg.createSection("federations.Acadia.deputies");
				dataFileCfg.createSection("federations.Acadia.members");
				dataFileCfg.addDefault("federations.Acadia.laws", Arrays.asList(new String[] {"None!"}));
				
				dataFileCfg.addDefault("provinces.penobscot.federation", "Acadia");
				dataFileCfg.addDefault("provinces.persolum.federation", "Acadia");
				dataFileCfg.addDefault("provinces.schoodic.federation", "Acadia");
				dataFileCfg.addDefault("provinces.torrhen.federation", "Acadia");
				dataFileCfg.addDefault("provinces.thurmont.federation", "Acadia");
				dataFileCfg.addDefault("provinces.rhinefield.federation", "Acadia");
				dataFileCfg.addDefault("provinces.atacama.federation", "Acadia");
				
				dataFileCfg.options().copyDefaults(true);
				dataFileCfg.save(dataFile);
			} catch (IOException e) {
				System.out.print(Main.consolePrefix + "Error creating data file!");
				e.printStackTrace();
			}
		}
		
		//dataFileCfg.set("federations.The_Acadian_Government.chief", "");
		//dataFileCfg.set("federations.The_Acadian_Government.laws", Arrays.asList(new String[] {"None!"}));
		
	}
	
	public static void unloadFiles() {
		
		File dataFile = new File(Main.getInstance().getDataFolder() + File.separator + "data.yml");
		FileConfiguration dataFileCfg = YamlConfiguration.loadConfiguration(dataFile);
		try {
			dataFileCfg.save(dataFile);
		} catch (IOException e) {
			System.out.print(Main.consolePrefix + "Could not save data file!");
			e.printStackTrace();
		}
	}
	
	public static void saveDataFile() {
		try {
			dataFileCfg.save(dataFile);
		} catch (IOException e) {
			System.out.print(Main.consolePrefix + "Error creating data file!");
			e.printStackTrace();
		}		
	}
}
