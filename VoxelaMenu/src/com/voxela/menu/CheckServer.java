package com.voxela.menu;

import java.io.*;
import java.net.*;

public class CheckServer {

	public @interface econServerCheck {

	}

	protected static String econIP = "10.0.0.102";
	protected static final int econPort = 25567;
	
	protected static String creativeIP = "10.0.0.102";
	protected static final int creativePort = 25569;
	
	protected static String hubIP = "10.0.0.102";
	protected static final int hubPort = 25566;
	
	protected static String survivalIP = "10.0.0.102";
	protected static final int survivalPort = 25571;

	static Socket s;

	public static boolean hubServerCheck() {
		try (Socket s = new Socket(hubIP, hubPort)) {
			return true;
		} catch (IOException ex) {
			/* ignore */
		}
		return false;
	}
	public static boolean econServerCheck() {
		try (Socket s = new Socket(econIP, econPort)) {
			return true;
		} catch (IOException ex) {
			/* ignore */
		}
		return false;
	}
	public static boolean creativeServerCheck() {
		try (Socket s = new Socket(creativeIP, creativePort)) {
			return true;
		} catch (IOException ex) {
			/* ignore */
		}
		return false;
	}
	public static boolean survivalServerCheck() {
		try (Socket s = new Socket(survivalIP, survivalPort)) {
			return true;
		} catch (IOException ex) {
			/* ignore */
		}
		return false;
	}
}
