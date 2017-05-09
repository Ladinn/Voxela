package com.voxela.gov.checker;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.gov.Main;
import com.voxela.gov.players.primeminister.PrimeMinisterPlayer;
import com.voxela.gov.utils.ChatUtils;
import com.voxela.gov.utils.FileManager;

public class CheckFed {

	public static String getTitle(String fed) {

		String ideology = getIdeology(fed);
		
		if (fed.equalsIgnoreCase("acadia")) {
			return "Prime Minister";
		}

		if (ideology.equals("Monarchy")) {
			return "King";
		}

		if (ideology.equals("Communism")) {
			return "Chairman";
		}

		if (ideology.equals("Democracy")) {
			return "President";
		}

		if (ideology.equals("Republic")) {
			return "Chancellor";
		}

		if (ideology.equals("Caliphate")) {
			return "Ayatollah";
		}

		if (ideology.equals("Imperialism")) {
			return "Caesar";
		}

		if (ideology.equals("Ecclesiology")) {
			return "His Holiness";
		}

		if (ideology.equals("National Socialism")) {
			return "Führer";
		}

		if (ideology.equals("Despotism")) {
			return "Supreme Leader";
		}

		return "";

	}

	public static String getChief(String fed) {
		return (String) FileManager.dataFileCfg.get("federations." + fed + ".chief");
	}

	public static String getFullChief(String fed) {

		String name = ChatUtils.fromUUID((String) FileManager.dataFileCfg.get("federations." + fed + ".chief"));
		
		if (fed.equalsIgnoreCase("acadia")) {
			
			if (PrimeMinisterPlayer.isPrimeMinister()) {
				name = PrimeMinisterPlayer.getPrimeMinister().getName();
			} else {
				return "The Prime Minister";
			}
		}
		
		String title = getTitle(fed);

		return title + " " + name;

	}

	public static String[] getAllies(String fed) {

		List<String> list = FileManager.dataFileCfg.getStringList("federations." + fed + ".allies");
		String[] alliesArray = list.toArray(new String[0]);

		return alliesArray;
	}

	public static String getNiceName(String fed) {

		String prefix = getFullPrefix(fed);
		fed.replaceAll("_", " ");

		return prefix + " " + fed;

	}

	public static boolean fedExists(String fed) {
		
		if (FileManager.dataFileCfg.getConfigurationSection("federations").contains(fed))
			return true;

		return false;
	}

	public static String getFullPrefix(String fed) {

		String ideology = getIdeology(fed);
		
		if (ideology.equals("Monarchy")) {
			return "The Kingdom of";
		}

		if (ideology.equals("Communism")) {
			return "The People's Republic of";
		}

		if (ideology.equals("Democracy")) {
			return "The United Federation of";
		}

		if (ideology.equals("Republic")) {
			return "The Democratic Republic of";
		}

		if (ideology.equals("Caliphate")) {
			return "The Sharia Caliphate of";
		}

		if (ideology.equals("Imperialism")) {
			return "The Empire of";
		}

		if (ideology.equals("Ecclesiology")) {
			return "The Holy See of";
		}

		if (ideology.equals("National Socialism")) {
			return "Das Reich von";
		}

		if (ideology.equals("Despotism")) {
			return "The Totalitarian State of";
		}

		return "";

	}

	public static String getPlayerFed(Player player) {

		String fed = "";

		for (String key : FileManager.dataFileCfg.getConfigurationSection("federations").getKeys(false)) {

			if (FileManager.dataFileCfg.getString("federations." + key + ".members")
					.contains(player.getUniqueId().toString())) {
				fed = key;
			}
			if (FileManager.dataFileCfg.getString("federations." + key + ".chief")
					.contains(player.getUniqueId().toString())) {
				fed = key;
			}
			if (FileManager.dataFileCfg.getString("federations." + key + ".deputies")
					.contains(player.getUniqueId().toString())) {
				fed = key;
			}
		}

		if (fed == "") {
			return "None!";
		}

		return fed;
	}

	public static ArrayList<String> getAllPlayersInFed() {

		ArrayList<String> players = new ArrayList<String>();

		for (String key : FileManager.dataFileCfg.getConfigurationSection("federations").getKeys(false)) {

			@SuppressWarnings("unchecked")
			List<String> memberList = (List<String>) FileManager.dataFileCfg.getList("federations." + key + ".members");

			if (!(memberList == null)) {

				for (String uuidString : memberList)
					players.add(ChatUtils.fromUUID(uuidString));

			}

			@SuppressWarnings("unchecked")
			List<String> deputiesList = (List<String>) FileManager.dataFileCfg
					.getList("federations." + key + ".deputies");

			if (!(deputiesList == null)) {

				for (String uuidString : deputiesList)
					players.add(ChatUtils.fromUUID(uuidString));

			}

			String chief = (String) FileManager.dataFileCfg.get("federations." + key + ".chief");

			if (!(chief == null)) {
				players.add(ChatUtils.fromUUID(chief));
			}
		}

		return players;
	}

	public static ArrayList<Player> getOnlinePlayersInFed(String fed) {

		ArrayList<Player> online = new ArrayList<Player>();

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (getPlayerFed(player).contains(fed))
				online.add(player);
		}
		return online;
	}

	public boolean fedInRegion(ProtectedRegion region, ArrayList<Player> online) {

		System.out.print(region);
		System.out.print(online);

		for (Player player : online) {

			if (player.isDead())
				return false;

			Location loc = player.getLocation();
			System.out.print(
					Main.getWorldGuard().getRegionManager(player.getWorld()).getApplicableRegions(loc).getRegions());
			if (Main.getWorldGuard().getRegionManager(player.getWorld()).getApplicableRegions(loc).getRegions()
					.contains(region))
				return true;

		}
		return false;
	}

	public static void addMember(Player player, String fed) {

		String playerUUID = ChatUtils.toUUID(player.getName()).toString();

		String memKey = "federations." + fed + ".members";
		List<String> currentMem = FileManager.dataFileCfg.getStringList(memKey);
		currentMem.add(playerUUID);

		FileManager.dataFileCfg.set(memKey, currentMem);
		FileManager.saveDataFile();

	}

	public static void removeMember(String player, String fed) {

		String memKey = "federations." + fed + ".members";
		String depKey = "federations." + fed + ".deputies";

		List<String> currentMem = FileManager.dataFileCfg.getStringList(memKey);
		List<String> currentDep = FileManager.dataFileCfg.getStringList(depKey);

		String playerUUID = ChatUtils.toUUID(player).toString();

		currentMem.remove(playerUUID);
		currentDep.remove(playerUUID);

		FileManager.dataFileCfg.set(memKey, currentMem);
		FileManager.dataFileCfg.set(depKey, currentDep);
		FileManager.saveDataFile();
	}

	public static String getIdeology(String fed) {
		return FileManager.dataFileCfg.getString("federations." + fed + ".ideology");
	}

	public static int getTotalTaxRate(String fed) {

		int serverTax = Main.getInstance().getConfig().getInt("servertax");
		int fedTax = getTaxRate(fed);
		int total = serverTax + fedTax;
		
		if (CheckMandate.isMandateEnabled(fed, "Transportation Subsidy")) {
			total += 18;
		}
		
		if (CheckMandate.isMandateEnabled(fed, "Universal Healthcare")) {
			total += 10;
		}
		
		if (CheckMandate.isMandateEnabled(fed, "Department of Housing")) {
			total += 14;
		}
		
		return total;

	}
	
	public static int getTaxRate(String fed) { return FileManager.dataFileCfg.getInt("federations." + fed + ".taxrate"); }

	public static void setIdeology(String fed, String newIdeology) {
		FileManager.dataFileCfg.set("federations." + fed + ".ideology", newIdeology);
		FileManager.saveDataFile();
	}

	@SuppressWarnings("deprecation")
	private static OfflinePlayer getEco(String fed) {
		return Bukkit.getOfflinePlayer("fed-" + fed);
	}

	public static double getBal(String fed) {
		return Main.getEconomy().getBalance(getEco(fed));
	}

	public static void deposit(String fed, double amount) {
		Main.getEconomy().depositPlayer(getEco(fed), amount);
	}

	public static void withdraw(String fed, double amount) {
		Main.getEconomy().withdrawPlayer(getEco(fed), amount);
	}
	
	public static String getMembers(String fed) {
		
		String memberName = "";		
		if (!(FileManager.dataFileCfg.getList("federations." + fed + ".members") == null)) {
			
			@SuppressWarnings("unchecked")
			ArrayList<String> membersList = (ArrayList<String>) FileManager.dataFileCfg.getList("federations." + fed + ".members");
			String[] membersArray = new String[membersList.size()];
			membersArray = membersList.toArray(membersArray);
			
            for(int i=0; i < membersArray.length; i++){
    			memberName += ChatUtils.fromUUID(membersArray[i]) + ", ";
            }
            return memberName;
		}
            
		memberName = "None!";
		return memberName;
		
	}
	
	public static String getDeputies(String fed) {
		
		String deputyName = "";		
		if (!(FileManager.dataFileCfg.getList("federations." + fed + ".deputies") == null)) {
			
			@SuppressWarnings("unchecked")
			ArrayList<String> deputyList = (ArrayList<String>) FileManager.dataFileCfg.getList("federations." + fed + ".deputies");
			String[] deputyArray = new String[deputyList.size()];
			deputyArray = deputyList.toArray(deputyArray);
			
            for(int i=0; i < deputyArray.length; i++){
            	deputyName += ChatUtils.fromUUID(deputyArray[i]) + ", ";
            }

            return deputyName;
		}
            
		deputyName = "None!";
		return deputyName;
		
	}
}
