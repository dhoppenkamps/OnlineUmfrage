package de.bkah.kundenumfrage.persistence;

import java.sql.ResultSet;

/**
 * Diese Klasse implementiert die Kommunikation mit der Datenbank.
 * 
 * Dabei ist diese Klasse nur für den Auf- und Abbau der Verbindung sowie für das Absetzen der SQL-Statements zuständig.
 * Die eigentlichen Statements werden von außerhalb übergeben.
 * 
 * @author Dominik Hoppenkamps
 *
 */
public class UmfrageDB 
{
	
	public UmfrageDB()
	{
		
	}
	
	public static final void connect() {
		// TODO implement
	}
	
	public static final void disconnect() {
		// TODO implement
	}
	
	public static final ResultSet select() {
		// TODO implement
		return null;
	}
	
	public static final void update() {
		// TODO implement
	}
	
	public static final void insert() {
		// TODO implement
	}
	
	public static final void delete() {
		// TODO implement
	}
}
