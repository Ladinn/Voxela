package com.voxela.gov.events;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.connorlinfoot.titleapi.TitleAPI;
import com.mewin.WGRegionEvents.events.RegionEnterEvent;
import com.voxela.gov.Main;
import com.voxela.gov.checker.CheckFed;
import com.voxela.gov.checker.CheckProvince;
import com.voxela.gov.effects.SetEffect;
import com.voxela.gov.utils.ChatUtils;

import net.md_5.bungee.api.ChatColor;

public class ProvinceEnter implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onProvinceEnter(RegionEnterEvent e) {

		if (e.getRegion().getId().startsWith("prov_")) {

			String province = null;
			String provinceInfo = null;
			if (e.getRegion().getId().equals("prov_penobscot")) {
				province = "Penobscot";
				provinceInfo = "Penobscot";
			}
			if (e.getRegion().getId().equals("prov_persolumcrater")) {
				province = "Persolum Crater";
				provinceInfo = "Persolum";
			}
			if (e.getRegion().getId().equals("prov_schoodicglacier")) {
				province = "Schoodic Glacier";
				provinceInfo = "Schoodic";
			}
			if (e.getRegion().getId().equals("prov_torrhenvale")) {
				province = "Torrhen Vale";
				provinceInfo = "Torrhen";
			}
			if (e.getRegion().getId().equals("prov_thurmontplains")) {
				province = "Thurmont Plains";
				provinceInfo = "Thurmont";
			}
			if (e.getRegion().getId().equals("prov_rhinefield")) {
				province = "Rhinefield";
				provinceInfo = "Rhinefield";
			}
			if (e.getRegion().getId().equals("prov_atacamaoutlands")) {
				province = "Atacama Outlands";
				provinceInfo = "Atacama";
			}

			String fed = CheckProvince.getFed(CheckProvince.stringToDataFile(provinceInfo));
			String fedNiceName = CheckFed.getNiceName(fed);

			Player player = e.getPlayer();
			Location loc = player.getLocation();
			String title = ChatColor.GOLD + province;
			String subtitle = "" + ChatColor.GRAY + ChatColor.ITALIC + "Governed by " + fedNiceName + ".";

			player.playSound(loc, Sound.ENTITY_WITHER_SPAWN, 0.5F, 1.0F);
			player.playSound(loc, Sound.BLOCK_PORTAL_TRAVEL, 0.35F, 0.65F);

			TitleAPI.sendFullTitle(player, 50, 175, 50, title, subtitle);

			provinceInfo(player, provinceInfo);
			
			SetEffect.set(fed);
			
			List<Player> online = CheckProvince.getPlayersInProvince(provinceInfo.toLowerCase());
			
			for (Player playerInProvince : online) {
				playerInProvince.sendMessage(Main.gamePrefix + ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.AQUA + " has entered " + ChatColor.LIGHT_PURPLE + province + ".");
			}

		}
	}

	public void provinceInfo(Player player, String provinceInfo) {

		ChatColor provColor1 = ChatColor.GRAY;
		ChatColor provColor2 = ChatColor.GRAY;
		ChatColor provColor3 = ChatColor.GRAY;
		ChatColor provColor4 = ChatColor.GRAY;
		ChatColor provColor5 = ChatColor.GRAY;
		ChatColor provColor6 = ChatColor.GRAY;
		ChatColor provColor7 = ChatColor.GRAY;
		if (provinceInfo.equalsIgnoreCase("Penobscot")) {
			provColor1 = ChatColor.GREEN;
		}
		if (provinceInfo.equalsIgnoreCase("Persolum")) {
			provColor2 = ChatColor.GREEN;
		}
		if (provinceInfo.equalsIgnoreCase("Schoodic")) {
			provColor3 = ChatColor.GREEN;
		}
		if (provinceInfo.equalsIgnoreCase("Torrhen")) {
			provColor4 = ChatColor.GREEN;
		}
		if (provinceInfo.equalsIgnoreCase("Thurmont")) {
			provColor5 = ChatColor.GREEN;
		}
		if (provinceInfo.equalsIgnoreCase("Rhinefield")) {
			provColor6 = ChatColor.GREEN;
		}
		if (provinceInfo.equalsIgnoreCase("Atacama")) {
			provColor7 = ChatColor.GREEN;
		}

		player.sendMessage(ChatUtils.formatTitle("Voxela Government"));
		ChatUtils.sendCenteredMessage(player,
				provColor1 + " Penobscot " + ChatColor.DARK_GRAY + "|" + provColor2 + " Persolum Crater "
						+ ChatColor.DARK_GRAY + "|" + provColor3 + " Schoodic Glacier " + ChatColor.DARK_GRAY + "|"
						+ provColor4 + " Torrhen Vale");
		ChatUtils.sendCenteredMessage(player, provColor5 + "Thurmont Plains " + ChatColor.DARK_GRAY + "|" + provColor6
				+ " Rhinefield " + ChatColor.DARK_GRAY + "|" + provColor7 + " Atacama Outlands");
		player.sendMessage(ChatUtils.divider);
		ChatUtils.sendCenteredMessage(player, ChatColor.DARK_AQUA + "Type " + ChatColor.GOLD + "/gov info "
				+ provinceInfo + ChatColor.DARK_AQUA + " for more");
		ChatUtils.sendCenteredMessage(player, ChatColor.DARK_AQUA + "information on the province you are in.");
		player.sendMessage(ChatUtils.divider);

	}

}
