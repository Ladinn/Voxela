package com.voxela.password.utils;

import java.security.SecureRandom;

public class Generator {

	private static final String AB = "00112233445566778899ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static SecureRandom rnd = new SecureRandom();

	public static String newPassword(int count) {
		
		StringBuilder sb = new StringBuilder( count );
		
		for( int i = 0; i < count; i++ ) 
			sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		
		return sb.toString();
	}

}
