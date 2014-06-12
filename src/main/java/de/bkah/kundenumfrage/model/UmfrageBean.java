package de.bkah.kundenumfrage.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import de.bkah.kundenumfrage.email.EMailService;
import de.bkah.kundenumfrage.persistence.DBOperations;

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
	
	private Antwort antwort;
	private static final Logger LOGGER = Logger.getLogger(UmfrageBean.class);
	
	// -----------------
	// Constructors
	// -----------------
	
	@PostConstruct
	public void init()
	{
		FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		antwort = new Antwort();
		Umfrage umfrage = new Umfrage();
		umfrage.setId(1);
		antwort.setUmfrage(umfrage);
	}

	// -----------------
	// Getter / Setter
	// -----------------
	
	public Antwort getAntwort() {
		return antwort;
	}

	public void setAntwort(Antwort antwort) {
		this.antwort = antwort;
	}
	
	// -----------------
	// business logic
	// -----------------
	
	/**
	 * JSF action method. Verschickt eine E-Mail an die Teilnehmer der Umfrage.
	 * 
	 * @return Zielseite. (null = reload der aktuellen Seite)
	 * @throws MessagingException
	 * @throws SQLException 
	 * @throws ReflectiveOperationException 
	 */
	public String sendMail() throws MessagingException, ReflectiveOperationException, SQLException
	{
		List<Kunde> kundenstamm = DBOperations.selectKunden();
		
		if(LOGGER.isDebugEnabled())
			LOGGER.debug("sendMail() aufgerufen");
			
		EMailService mailService = new EMailService();
		mailService.setHost("localhost");
		String link 	= "http://localhost:8080/kundenumfrage/customer_Mail.xhtml";
		String subject 	= "Online-Umfrage";
		String body 	= "Hallo,\n\nBitte nehmen Sie sich einen Moment Zeit und bewerten Sie uns!\n\nHier gehts zur Umfrage: "+ link +" \n\nVielen Dank!";
		
		for(int i = 0; i < kundenstamm.size(); i++) 
		{
			String to 		= kundenstamm.get(i).getEmail();
			String from 	= "admin@localhost";
			
			mailService.sendMail(to, from, subject, body);
		}
		
		return "/success.xhtml";
	}
	
	/**
	 * JSF action method. Speichert die Stimmabgaben eines Teilnehmers ab.
	 * 
	 * @return Zielseite. (null = reload der aktuellen Seite)
	 * @throws SQLException 
	 * @throws ReflectiveOperationException 
	 */
	public String saveAntworten() throws ReflectiveOperationException, SQLException 
	{
		try {
		DBOperations.insertAntwort(antwort);
		} catch(Exception e) {
			LOGGER.error("Fehler beim Speichern der Antwort.", e);
			throw e;
		}
		return "/customer_Done.xhtml";
	}
}
