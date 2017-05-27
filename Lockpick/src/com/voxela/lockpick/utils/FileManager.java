package com.voxela.lockpick.utils;

import com.voxela.lockpick.Main;

public class FileManager {
		
	public static void createConfig() {
		
	    Main.getInstance().getConfig().options().copyDefaults();
	    Main.getInstance().saveConfig();

	}

}
