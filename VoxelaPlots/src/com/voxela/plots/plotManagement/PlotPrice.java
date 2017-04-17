package com.voxela.plots.plotManagement;

import org.bukkit.Location;

import com.sk89q.worldedit.bukkit.selections.Selection;
import com.voxela.plots.Main;

public class PlotPrice {
	
	public static int getPlotPrice(Selection sel) {
		
		Location max = sel.getMaximumPoint();
		Location min = sel.getMinimumPoint();
		
		int vol = (int) ((max.getX() - min.getX()) * (max.getZ() - min.getZ()) * (max.getY() - min.getY()));
				
		int priceVar = Main.getInstance().getConfig().getInt("pricevar");
		
		int price = vol / priceVar;
		return price;
		
	}
}
