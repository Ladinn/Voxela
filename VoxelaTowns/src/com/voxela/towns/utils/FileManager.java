package com.voxela.towns.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.voxela.towns.Main;

public class FileManager {

	public static File dataFile = new File(Main.getInstance().getDataFolder() + File.separator + "data.yml");
	public static FileConfiguration dataFileCfg = YamlConfiguration.loadConfiguration(dataFile);
	
	public static void loadFiles() {
		
		File schemFolder = new File(Main.getInstance().getDataFolder() + File.separator + "schematics");
		if (!schemFolder.exists()) {
			System.out.print(Main.consolePrefix + "Creating schematic folder...");
			schemFolder.mkdirs();
		}
		
		Main.getInstance().getConfig().options().header("Voxela Towns - Configuration");
		Main.getInstance().getConfig().options().copyHeader();
		Main.getInstance().getConfig().addDefault("pricevar", 100);
		Main.getInstance().getConfig().addDefault("weeklypricecut", 0.75);
		Main.getInstance().getConfig().options().copyDefaults(true);
		Main.getInstance().saveConfig();
		
		if (!dataFile.exists()) {
			try {
				System.out.print(Main.consolePrefix + "Creating data file...");
				dataFile.createNewFile();
				dataFileCfg.options().header("Voxela Towns - Data File");
				dataFileCfg.options().copyHeader();
				dataFileCfg.addDefault("towncount", 1);
				dataFileCfg.options().copyDefaults(true);
				dataFileCfg.save(dataFile);
			} catch (IOException e) {
				System.out.print(Main.consolePrefix + "Error creating data file!");
				e.printStackTrace();
			}
		}	
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
