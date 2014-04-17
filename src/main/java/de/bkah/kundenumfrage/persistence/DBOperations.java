package de.bkah.kundenumfrage.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import de.bkah.kundenumfrage.model.Antwort;
import de.bkah.kundenumfrage.model.Kunde;
import de.bkah.kundenumfrage.model.Umfrage;

/**
 * Diese Klasse implementiert den Kontext bzw. die Logik der Datenbank-Kommunikation.
 * 
 * @author Dominik Hoppenkamps
 *
 */
public class DBOperations {
	
	private static final UmfrageDB DB = new UmfrageDB();
	private static final Logger LOGGER = Logger.getLogger(DBOperations.class);
	
	
	/**
	 * Fügt eine neue Umfrage in die Datenbank ein.
	 * 
	 * @param umfrage
	 * @throws ReflectiveOperationException
	 * @throws SQLException
	 */
	public static void insertUmfrage(final Umfrage umfrage) throws ReflectiveOperationException, SQLException {
		PreparedStatement stmt = null;
		String sql = "INSERT INTO umfrage(Titel, Frage1, Frage2, Frage3, Frage4, Frage5, Beginn, Ende) VALUES (?,?,?,?,?,?,?,?)";
		
		try {
			DB.connect();
			stmt = DB.getConnection().prepareStatement(sql);
			stmt.setString(1, umfrage.getTitel());
			stmt.setString(2, umfrage.getFrage1());
			stmt.setString(3, umfrage.getFrage2());
			stmt.setString(4, umfrage.getFrage3());
			stmt.setString(5, umfrage.getFrage4());
			stmt.setString(6, umfrage.getFrage5());
			stmt.setDate(7, umfrage.getBeginn());
			stmt.setDate(8, umfrage.getEnde());
			
			DB.executeDML(stmt);
		} catch (SQLException e) {
			LOGGER.error("Fehler beim  Ermitteln des Umfrageergebnisses.", e);
			throw e;
		} finally {
			if(stmt != null) {
				stmt.close();
			}
			DB.disconnect();
		}
	}
	
	/**
	 * Generiert einen Token und schreibt ihn in die Datenbank. Die verknüpfte Umfrage und der Kunde muss übergeben werden.
	 * 
	 * @param umfrage
	 * @param kunde
	 * @throws ReflectiveOperationException
	 * @throws SQLException
	 */
	public static void insertToken(final Umfrage umfrage, final Kunde kunde) throws ReflectiveOperationException, SQLException {
		PreparedStatement stmt = null;
		String token = generateToken();
		String sql = "INSERT INTO token(Token, ID_Umfrage, ID_Kunde) VALUES (?,?,?)";
		
		try {
			DB.connect();
			stmt = DB.getConnection().prepareStatement(sql);
			stmt.setString(1, token);
			stmt.setLong(2, umfrage.getId());
			stmt.setLong(3, kunde.getId());
			
			DB.executeDML(stmt);
		} catch (SQLException e) {
			LOGGER.error("Fehler beim  Ermitteln des Umfrageergebnisses.", e);
			throw e;
		} finally {
			if(stmt != null) {
				stmt.close();
			}
			DB.disconnect();
		}
	}
	
	/**
	 * Schreibt die Antworten eines Teilnehmers in die Datenbank.
	 * 
	 * @param antwort
	 * @throws ReflectiveOperationException
	 * @throws SQLException
	 */
	public static void insertAntwort(final Antwort antwort) throws ReflectiveOperationException, SQLException {
		PreparedStatement stmt = null;
		String sql = "INSERT INTO antworten(ID_Umfrage, Antwort1, Antwort2, Antwort3, Antwort4, Antwort5) VALUES (?,?,?,?,?,?)";
		
		try {
			DB.connect();
			stmt = DB.getConnection().prepareStatement(sql);
			stmt.setLong(1, antwort.getUmfrage().getId());
			stmt.setString(2, antwort.getAntwort1());
			stmt.setString(3, antwort.getAntwort2());
			stmt.setString(4, antwort.getAntwort3());
			stmt.setString(5, antwort.getAntwort4());
			stmt.setString(6, antwort.getAntwort5());
			
			DB.executeDML(stmt);
		} catch (SQLException e) {
			LOGGER.error("Fehler beim  Ermitteln des Umfrageergebnisses.", e);
			throw e;
		} finally {
			if(stmt != null) {
				stmt.close();
			}
			DB.disconnect();
		}
	}
	
	/**
	 * Gibt zurück, ob der Kunde an der übergebenen Umfrage teilgenommen hat.
	 * 
	 * @param kunde
	 * @param umfrage
	 * @return true = teilgenommen, false = nicht teilgenommen
	 * @throws ReflectiveOperationException
	 * @throws SQLException
	 */
	public static boolean hasParticipated(final Kunde kunde, final Umfrage umfrage) throws ReflectiveOperationException, SQLException {
		boolean teilgenommen = false;		
		ResultSet rs = null;
		PreparedStatement stmt = null;
		String sql = "SELECT * FROM token WHERE (token.ID_Umfrage = ? AND token.ID_Kunde = ?) AND benutzt = ?";
		
		try {
			DB.connect();
			stmt = DB.getConnection().prepareStatement(sql);
			stmt.setLong(1, umfrage.getId());
			stmt.setLong(2, kunde.getId());
			stmt.setBoolean(3, true);
			rs = DB.select(stmt);
			
			int rowCount = 0;
			if(rs.last()) {
				rowCount = rs.getRow();
			}
			
			teilgenommen = rowCount == 1;
		} catch (SQLException e) {
			LOGGER.error("Fehler beim  Ermitteln des Umfrageergebnisses.", e);
			throw e;
		} finally {
			if(rs != null) {
				rs.close();
			}
			if(stmt != null) {
				stmt.close();
			}
			DB.disconnect();
		}
		
		return teilgenommen;
	}
	
	/**
	 * Gibt zurück, ob der Kunde an der Umfrage teilnehmen darf.
	 * 
	 * @param kunde 
	 * @param umfrage
	 * @return true = teilnahmeberechtigt, false = nicht berechtigt
	 * @throws ReflectiveOperationException
	 * @throws SQLException
	 */
	public static boolean mayParticipate(final Kunde kunde, final Umfrage umfrage) throws ReflectiveOperationException, SQLException {
		// FIXME  - Das Datenbankmodell ist fehlerhaft / ungünstig. Die komplette Kreuztabelle umfrage_x_token ist überflüssig
		// und könnte durch eine boolsche Spalte "benutzt" in der Tabelle token ersetzt werden. Die Kreuztabelle wird gar nicht benötigt,
		// weil jeder Token eindeutig einem Kunden und der Umfrage zugeordnet werden kann.
		
		boolean berechtigt = false;		
		ResultSet rs = null;
		PreparedStatement stmt = null;
		String sql = "SELECT * FROM token WHERE (token.ID_Umfrage = ? AND token.ID_Kunde = ?) AND benutzt = ?";
		
		try {
			DB.connect();
			stmt = DB.getConnection().prepareStatement(sql);
			stmt.setLong(1, umfrage.getId());
			stmt.setLong(2, kunde.getId());
			stmt.setBoolean(3, false);
			rs = DB.select(stmt);
			
			int rowCount = 0;
			if(rs.last()) {
				rowCount = rs.getRow();
			}
			
			berechtigt = rowCount == 1;
		} catch (SQLException e) {
			LOGGER.error("Fehler beim  Ermitteln des Umfrageergebnisses.", e);
			throw e;
		} finally {
			if(rs != null) {
				rs.close();
			}
			if(stmt != null) {
				stmt.close();
			}
			DB.disconnect();
		}
		
		return berechtigt;
	}
	
	/**
	 * Gibt das Ergebnis der übergebenen Umfrage zurück. Das Ergebnis ist die Liste der abgegegen Antworten.
	 * 
	 * @param umfrage Umfrage, zu der das Ergebnis ermittelt werden soll
	 * @return Liste aller Antworten
	 * @throws ReflectiveOperationException
	 * @throws SQLException
	 */
	public static List<Antwort> selectErgebnis(final Umfrage umfrage) throws ReflectiveOperationException, SQLException {
		ArrayList<Antwort> ergebnis = new ArrayList<Antwort>();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		String sql = "SELECT * FROM antworten WHERE ID_Umfrage = ?";
		
		try {
			DB.connect();
			stmt = DB.getConnection().prepareStatement(sql);
			stmt.setLong(1, umfrage.getId());
			rs = DB.select(stmt);
			
			while(rs.next()) {
				Antwort a = new Antwort();
				a.setId(rs.getLong("ID_Antwort"));
				a.setUmfrage(umfrage);
				a.setAntwort1(rs.getString("Antwort1"));
				a.setAntwort2(rs.getString("Antwort2"));
				a.setAntwort3(rs.getString("Antwort3"));
				a.setAntwort4(rs.getString("Antwort4"));
				a.setAntwort5(rs.getString("Antwort5"));
				ergebnis.add(a);
			}
		} catch (SQLException e) {
			LOGGER.error("Fehler beim  Ermitteln des Umfrageergebnisses.", e);
			throw e;
		} finally {
			if(rs != null) {
				rs.close();
			}
			if(stmt != null) {
				stmt.close();
			}
			DB.disconnect();
		}
		
		return ergebnis;
	}
	
	/**
	 * Selektiert den Kunden mit der übergeben Id aus der Datenbank und gibt ihn zurück.
	 * 
	 * @param id
	 * @return Kunde
	 * @throws ReflectiveOperationException
	 * @throws SQLException
	 */
	public static Kunde selectKunde(final long id) throws ReflectiveOperationException, SQLException {
		Kunde kunde = new Kunde();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		String sql = "SELECT * FROM kunde WHERE ID_Kunde = ? LIMIT 1";
		
		try {
			DB.connect();
			stmt = DB.getConnection().prepareStatement(sql);
			stmt.setLong(1, id);
			rs = DB.select(stmt);
			
			while(rs.next()) {
				kunde.setId(rs.getLong("ID_Kunde"));
				kunde.setVorname(rs.getString("Vorname"));
				kunde.setName(rs.getString("Nachname"));
				kunde.setEmail(rs.getString("Email"));
			}
		} catch (SQLException e) {
			LOGGER.error("Fehler beim  Select des Kunden.", e);
			throw e;
		} finally {
			if(rs != null) {
				rs.close();
			}
			if(stmt != null) {
				stmt.close();
			}
			DB.disconnect();
		}
		
		return kunde;
	}
	
	/**
	 * Selektiert den Kundenstammdaten aus der Datenbank und gibt die Liste aller Kunden zurück.
	 * 
	 * @return Liste aller Kunden
	 * @throws ReflectiveOperationException
	 * @throws SQLException
	 */
	public static List<Kunde> selectKunden() throws ReflectiveOperationException, SQLException {
		ArrayList<Kunde> kundenListe = new ArrayList<Kunde>();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		String sql = "SELECT * FROM kunde";
		
		try {
			DB.connect();
			stmt = DB.getConnection().prepareStatement(sql);
			rs = DB.select(stmt);
			
			while(rs.next()) {
				Kunde k = new Kunde();
				k.setId(rs.getLong("ID_Kunde"));
				k.setVorname(rs.getString("Vorname"));
				k.setName(rs.getString("Nachname"));
				k.setEmail(rs.getString("Email"));
				kundenListe.add(k);
			}
		} catch (SQLException e) {
			LOGGER.error("Fehler beim  Abfragen der Kundenliste.", e);
			throw e;
		} finally {
			if(rs != null) {
				rs.close();
			}
			if(stmt != null) {
				stmt.close();
			}
			DB.disconnect();
		}
		
		return kundenListe;
	}

	private static String generateToken() {
		// TODO implement
		return "token";
	}
}
