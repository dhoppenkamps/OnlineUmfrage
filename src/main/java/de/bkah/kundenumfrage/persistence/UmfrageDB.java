package de.bkah.kundenumfrage.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * Diese Klasse implementiert die Kommunikation mit der Datenbank.
 * 
 * Dabei ist diese Klasse nur f�r den Auf- und Abbau der Verbindung sowie f�r das Absetzen der SQL-Statements zust�ndig.
 * Die eigentlichen Statements werden von au�erhalb �bergeben.
 * 
 * @author Dominik Hoppenkamps
 *
 */
public class UmfrageDB 
{
	
	private String url, dbName, driver, userName, password;
	private Connection conn;
	
	private static final Logger LOGGER = Logger.getLogger(UmfrageDB.class);
	
	public UmfrageDB()
	{
		// TODO auslagern
		url = "jdbc:mysql://localhost:3306/";
		dbName = "onlineumfrage";
		driver = "com.mysql.jdbc.Driver";
		userName = "umfrage"; 
		password = "umfrage";
	}
	
	/**
	 * Stellt die Verbindung zur Datenbank her.
	 * 
	 * @throws SQLException
	 * @throws ReflectiveOperationException
	 */
	public final void connect() throws SQLException, ReflectiveOperationException { 
		if(LOGGER.isDebugEnabled())
			LOGGER.debug("connect() aufgerufen.");
		
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url+dbName,userName,password);
			if(LOGGER.isDebugEnabled())
				LOGGER.debug("Connect erfolgreich!");
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			LOGGER.fatal("Fehler beim setzen des Datenbank-Treibers.", e);
			throw e;
		} catch(SQLException e) {
			LOGGER.error("Fehler beim DB-Connect.", e);
			throw e;
		}
	}
	
	/**
	 * Schlie�t die Verbindung zur Datenbank.
	 * 
	 * @throws SQLException
	 */
	public final void disconnect() throws SQLException {
		// Nullpointer abfangen
		if(conn == null) {
			LOGGER.error("Connection ist null.");
			return;
		}
		
		if(LOGGER.isDebugEnabled())
			LOGGER.debug("Disconnect() aufgerufen!");
		try {
			conn.close();
			if(LOGGER.isDebugEnabled())
				LOGGER.debug("Disconnect erfolgreich!");
		} catch (SQLException e) {
			LOGGER.error("Fehler beim DB-Disconnect.", e);
			throw e;
		}
	}
	
	/**
	 * F�hrt ein SelectStatement aus und gibt das resultierende ResultSet zur�ck.
	 * 
	 * @param stmt Auszuf�hrendes Select-Statement
	 * @return ResultSet of the Select-Statement (null if error)
	 * @throws ReflectiveOperationException
	 * @throws SQLException
	 */
	public final ResultSet select(final PreparedStatement stmt) throws ReflectiveOperationException, SQLException {
		// Nullpointer abfangen
		if(stmt == null)
		{
			LOGGER.error("Statement ist null.");
			return null;
		}
		
		ResultSet rs = null;
		try {
			this.connect();
			rs = stmt.executeQuery();
		} catch(SQLException e) {
			LOGGER.error("Fehler beim Aus�hren des Statements.", e);
			throw e;
		} finally {
			stmt.close();
			this.disconnect();
		}
		
		return rs;
	}
	
	/**
	 * F�hrt ein DML-Statement aus (Insert, Delete, Update)
	 * 
	 * @param stmt DML-Statement
	 * @throws SQLException
	 */
	public final void executeDML(final PreparedStatement stmt) throws SQLException {
		// Nullpointer abfangen
		if(stmt == null)
		{
			LOGGER.error("Statement ist null.");
			return;
		}
		
		try {
			stmt.executeUpdate();
		} catch(SQLException e) {
			LOGGER.error("Fehler beim Aus�hren des Statements.", e);
			throw e;
		}
	}
}
