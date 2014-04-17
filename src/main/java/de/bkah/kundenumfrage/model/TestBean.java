package de.bkah.kundenumfrage.model;

import java.sql.SQLException;
import java.util.Arrays;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.mail.MessagingException;

import de.bkah.kundenumfrage.email.EMailService;
import de.bkah.kundenumfrage.persistence.DBOperations;

/**
 * 
 * @author Dominik Hoppenkamps
 *
 */
@ManagedBean(name="testBean")
@RequestScoped
public class TestBean {
	
	private String hasParticipated 	= "";
	private String mayParticipate 	= "";
	private String kunde 			= "";
	private String kunden 			= "";
	private String ergebnis 		= "";
	
	public String testSendMail() throws MessagingException {
		EMailService mailService = new EMailService();
		String to 		= "test1@localhost";
		String from 	= "admin@localhost";
		String subject 	= "Testing Mail-Service...";
		String body 	= "If you receive this, Mail-Service is working";
		
		mailService.setHost("localhost");
		mailService.sendMail(to, from, subject, body);
		
		return null;
	}
	
	public String testHasParticipated() throws ReflectiveOperationException, SQLException {
		Umfrage u = new Umfrage();
		Kunde k = new Kunde();
		k.setId(1);
		u.setId(1);
		
		hasParticipated = ""+ DBOperations.hasParticipated(k, u);
		
		return null;
	}
	
	public String testMayParticipate() throws ReflectiveOperationException, SQLException {
		Umfrage u = new Umfrage();
		Kunde k = new Kunde();
		k.setId(1);
		u.setId(1);
		
		mayParticipate = ""+ DBOperations.mayParticipate(k, u);
		
		return null;
	}
	
	public String testInsertAntwort() throws ReflectiveOperationException, SQLException {
		Antwort a = new Antwort();
		Umfrage u = new Umfrage();
		u.setId(1);
		a.setUmfrage(u);
		a.setAntwort1("antwort1");
		a.setAntwort2("antwort2");
		a.setAntwort3("antwort3");
		a.setAntwort4("antwort4");
		a.setAntwort5("antwort5");
		
		DBOperations.insertAntwort(a);
		
		return null;
	}
	
	public String testInsertToken() throws ReflectiveOperationException, SQLException {
		Umfrage u = new Umfrage();
		Kunde k = new Kunde();
		k.setId(1);
		u.setId(1);
		u.setTitel("Test-Umfrage");
		u.setFrage1("frage1");
		u.setFrage2("frage2");
		u.setFrage3("frage3");
		u.setFrage4("frage4");
		u.setFrage5("frage5");
		u.setBeginn(new java.sql.Date((new java.util.Date()).getTime()));
		u.setEnde(new java.sql.Date((new java.util.Date()).getTime()));
		
		DBOperations.insertToken(u, k);
		
		return null;
	}
	
	public String testInsertUmfrage() throws ReflectiveOperationException, SQLException {
		Umfrage u = new Umfrage();
		u.setId(1);
		u.setTitel("Test-Umfrage");
		u.setFrage1("frage1");
		u.setFrage2("frage2");
		u.setFrage3("frage3");
		u.setFrage4("frage4");
		u.setFrage5("frage5");
		u.setBeginn(new java.sql.Date((new java.util.Date()).getTime()));
		u.setEnde(new java.sql.Date((new java.util.Date()).getTime()));
		
		DBOperations.insertUmfrage(u);
		
		return null;
	}
	
	public String testSelectErgebnis() throws ReflectiveOperationException, SQLException {
		Umfrage u = new Umfrage();
		u.setId(1);
		
		ergebnis = Arrays.toString(DBOperations.selectErgebnis(u).toArray());
		
		return null;
	}
	
	public String testSelectKunde() throws ReflectiveOperationException, SQLException {
		kunde = ""+ DBOperations.selectKunde(1);
		
		return null;
	}
	
	public String testSelectKunden() throws ReflectiveOperationException, SQLException {
		kunden = Arrays.toString(DBOperations.selectKunden().toArray());
		
		return null;
	}
	
	
	
	
	public String getHasParticipated() {
		return hasParticipated;
	}

	public void setHasParticipated(String hasParticipated) {
		this.hasParticipated = hasParticipated;
	}

	public String getMayParticipate() {
		return mayParticipate;
	}

	public void setMayParticipate(String mayParticipate) {
		this.mayParticipate = mayParticipate;
	}

	public String getKunde() {
		return kunde;
	}

	public void setKunde(String kunde) {
		this.kunde = kunde;
	}

	public String getKunden() {
		return kunden;
	}

	public void setKunden(String kunden) {
		this.kunden = kunden;
	}

	public String getErgebnis() {
		return ergebnis;
	}

	public void setErgebnis(String ergebnis) {
		this.ergebnis = ergebnis;
	}

}
