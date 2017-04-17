package com.voxela.plots.plotManagement;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.voxela.plots.Main;

public class PlotPrice {
	
	public static int getPlotPrice(ProtectedRegion region) {
		
		int priceVar = Main.getInstance().getConfig().getInt("pricevar");
		
		int price = region.volume() / priceVar;
		return price;
		
	}

}
