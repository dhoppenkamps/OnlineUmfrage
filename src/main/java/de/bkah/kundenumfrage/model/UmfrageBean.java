package de.bkah.kundenumfrage.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import de.bkah.kundenumfrage.email.EMailService;
import de.bkah.kundenumfrage.persistence.DBOperations;
import de.bkah.kundenumfrage.persistence.UmfrageDB;

/**
 * 
 * @author Dominik Hoppenkamps
 *
 */
@ManagedBean(name="umfrageBean")
@RequestScoped
public class UmfrageBean implements Serializable
{
	// -----------------
	// Attributes
	// -----------------
	
	private static final long serialVersionUID = 1L;
	
	private String msg;
	
	private static final Logger LOGGER = Logger.getLogger(UmfrageBean.class);
	
	// -----------------
	// Constructors
	// -----------------
	
	@PostConstruct
	public void init()
	{
		msg = "Hello World!";
	}

	// -----------------
	// Getter / Setter
	// -----------------
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	// -----------------
	// business logic
	// -----------------
	
	/**
	 * JSF action method. Verschickt eine E-Mail an die Teilnehmer der Umfrage.
	 * 
	 * @return Zielseite. (null = reload der aktuellen Seite)
	 * @throws MessagingException
	 */
	public String sendMail() throws MessagingException
	{
		// TODO implement
		
		if(LOGGER.isDebugEnabled())
			LOGGER.debug("sendMail() aufgerufen");
			
		EMailService mailService = new EMailService();
		String to 		= "test1@localhost";
		String from 	= "admin@localhost";
		String subject 	= "Testing Mail-Service...";
		String body 	= "If you receive this, Mail-Service is working";
		
		mailService.setHost("localhost");
		mailService.sendMail(to, from, subject, body);
		
		return null;
	}
	
	/*
	public String test() throws SQLException, ReflectiveOperationException
	{
		// TODO remove
		
		UmfrageDB db = new UmfrageDB();
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
		Antwort a = new Antwort();
		a.setUmfrage(u);
		a.setAntwort1("antwort1");
		a.setAntwort2("antwort2");
		a.setAntwort3("antwort3");
		a.setAntwort4("antwort4");
		a.setAntwort5("antwort5");
		//msg = "";
		DBOperations.insertUmfrage(u);
		return null;
	}*/
}
