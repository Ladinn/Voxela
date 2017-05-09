package com.voxela.gov.effects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.voxela.gov.checker.CheckFed;
import com.voxela.gov.checker.CheckProvince;

public class SetEffect {
	
	private static PotionEffect luck = new PotionEffect(PotionEffectType.LUCK, Integer.MAX_VALUE, 1);
	private static PotionEffect unLuck = new PotionEffect(PotionEffectType.UNLUCK, Integer.MAX_VALUE, 1);
	private static PotionEffect miningFatigue = new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 1);
	private static PotionEffect haste = new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1);
	private static PotionEffect healthBoost = new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 1);
	private static PotionEffect weakness = new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 1);
	private static PotionEffect noHunger = new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 1);
	private static PotionEffect increasedHunger = new PotionEffect(PotionEffectType.HUNGER, Integer.MAX_VALUE, 1);
	private static PotionEffect jump = new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1);
	private static PotionEffect fire = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1);
	private static PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1);
	private static PotionEffect waterBreathing = new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 1);
	private static PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1);
	private static PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1);
	private static PotionEffect damageResistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1);
	private static PotionEffect increasedDamage = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1);
	private static PotionEffect nightVision = new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1);
		
	@SuppressWarnings("serial")
	private static List<PotionEffect> effects = new ArrayList<PotionEffect>(){
		{
			add(luck);
			add(unLuck);
			add(miningFatigue);
			add(haste);
			add(healthBoost);
			add(weakness);
			add(noHunger);
			add(increasedHunger);
			add(jump);
			add(fire);
			add(regen);
			add(waterBreathing);
			add(speed);
			add(slow);
			add(damageResistance);
			add(increasedDamage);
			add(nightVision);
		}
	};

	private static ArrayList<Player> getPlayers(String fed) {
		
		ArrayList<Player> players = new ArrayList<Player>();
		ArrayList<String> provinces = CheckProvince.getProvincesFedControls(fed);
		
		if (provinces.isEmpty()) return players;
				
		for (String province : provinces) {
			ArrayList<Player> playersInProvince = CheckProvince.getPlayersInProvince(province);
			for (Player player : playersInProvince) players.add(player);			
		}
		
		return players;
	}
	
	public static void set(String fed) {
		
		String ideology = CheckFed.getIdeology(fed);
		unset(fed);
		setIdeology(ideology, fed);		
	}
	
	public static void setIdeology(String ideology, String fed) {
		
		ArrayList<Player> players = getPlayers(fed);

		if (ideology.equalsIgnoreCase("monarchy")) {
			for (Player player : players) {
				player.addPotionEffect(luck);
			}
		}
		if (ideology.equalsIgnoreCase("republic")) {
			for (Player player : players) {
				player.addPotionEffect(healthBoost);
			}
		}
		if (ideology.equalsIgnoreCase("democracy")) {
			for (Player player : players) {
				player.addPotionEffect(noHunger);
			}
		}
		if (ideology.equalsIgnoreCase("communism")) {
			for (Player player : players) {
				player.addPotionEffect(haste);
			}
		}
		if (ideology.equalsIgnoreCase("imperialism")) {
			for (Player player : players) {
				player.addPotionEffect(nightVision);
			}
		}
		if (ideology.equalsIgnoreCase("caliphate")) {
			for (Player player : players) {
				player.addPotionEffect(fire);
			}
		}
		if (ideology.equalsIgnoreCase("ecclesiology")) {
			for (Player player : players) {
				player.addPotionEffect(regen);
			}
		}
		if (ideology.equalsIgnoreCase("national socialism")) {
			for (Player player : players) {
				player.addPotionEffect(speed);
			}
		}
		if (ideology.equalsIgnoreCase("despotism")) {
			for (Player player : players) {
				player.addPotionEffect(damageResistance);
			}
		}
	}

	public static void unset(String fed) {

		ArrayList<Player> players = getPlayers(fed);

		for (Player player : players) {

			for (PotionEffect effect : effects)
				player.removePotionEffect(effect.getType());

		}

	}

	public static void unsetPlayer(Player player) {

		for (PotionEffect effect : effects)
			player.removePotionEffect(effect.getType());

	}

}
