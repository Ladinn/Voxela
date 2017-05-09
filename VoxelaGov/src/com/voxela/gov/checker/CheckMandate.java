package com.voxela.gov.checker;

import java.util.ArrayList;

import com.voxela.gov.utils.FileManager;

public class CheckMandate {
	
	public static boolean isMandateEnabled(String fed, String mandate) {

		return FileManager.dataFileCfg.getBoolean("federations." + fed + ".mandates." + mandate);
		
	}
	
	public static void setMandate(String fed, String mandate, boolean bool) {
		FileManager.dataFileCfg.set("federations." + fed + ".mandates." + mandate, bool);
		FileManager.saveDataFile();
	}
	
	public static ArrayList<String> getMandates() {
		
		ArrayList<String> mandates = new ArrayList<String>();
		mandates.add("Transportation Subsidy");
		mandates.add("Universal Healthcare");
		mandates.add("Department of Housing");
		
		return mandates;
		
	}
	
	public static ArrayList<String> getFedMandates(String fed) {
		
		ArrayList<String> fedMandates = new ArrayList<String>();
				
		for (String mandate : getMandates()) {
			if (isMandateEnabled(fed, mandate)) {
				fedMandates.add(mandate);
			}
		}
		return fedMandates;
		
	}
	
	public static String mandateToPermGroup(String mandate) {
		
		mandate.replaceAll(" ", "");
		return mandate;
	}

}
