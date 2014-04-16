package de.bkah.kundenumfrage.persistence;

import java.sql.ResultSet;

/**
 * Diese Klasse implementiert den Kontext bzw. die Logik der Datenbank-Kommunikation.
 * 
 * @author Dominik Hoppenkamps
 *
 */
public class DBOperations {
	
	private static final UmfrageDB DB = new UmfrageDB();
	
	public static void insertUmfrage() {
		// TODO implement
	}
	
	public static void insertToken() {
		// TODO implement
	}
	
	public static void insertAntwort() {
		// TODO implement
	}
	
	public static void hasParticipated() {
		// TODO implement
	}
	
	public static boolean mayParticipate() {
		// TODO implement
		return false;
	}
	
	public static ResultSet selectResult() {
		// TODO implement
		return null;
	}
	
	public static ResultSet selectKunde() {
		// TODO implement
		return null;
	}
	
	public static ResultSet selectKunden() {
		// TODO implement
		return null;
	}

}
