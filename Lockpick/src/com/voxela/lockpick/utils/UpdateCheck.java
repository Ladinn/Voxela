package com.voxela.lockpick.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.bukkit.scheduler.BukkitRunnable;

import com.voxela.lockpick.Main;

public class UpdateCheck {
	
	public static void check() {
		
		new BukkitRunnable() {
			public void run() {
				
				Properties prop = new Properties();
				InputStream is = null;
				
				try {
					is = new ByteArrayInputStream(HttpUtil.requestHttp("https://raw.githubusercontent.com/Ladinn/Voxela/master/Lockpick/plugin.yml").getBytes(StandardCharsets.UTF_8));
					prop.load(is);
				} catch (IOException e) {
					Main.getInstance().getLogger().info("Error checking for update.");
					e.printStackTrace();
				}

				double latest = Double.parseDouble(prop.getProperty("version"));
				String currentString = Main.getInstance().getDescription().getVersion();
				double current = Double.parseDouble(currentString.replace("version: ", ""));
						
				if (latest == current) System.out.print(Main.consolePrefix + "You have the latest version! v" + latest);
				
				if (latest > current) {
					
					double behind = (latest - current) * 10;
					String msg = HttpUtil.requestHttp("http://net.voxela.com/lockpick/outdated.html");
					
					Main.getInstance().getLogger().warning(msg);
					Main.getInstance().getLogger().warning("You are " + behind + " versions behind.");
				}
				
			}
		}.runTaskLater(Main.getInstance(), 100L);
		
    }

}
