package de.bkah.kundenumfrage.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

/**
 * Diese Klasse implementiert einen E-Mail-Service, der Mails über einen SMTP-Server verschickt. 
 * 
 * Der SMTP Host muss zwingend übergeben werden, sonst ist die Klasse nicht funktionsfähig.
 * 
 * @author Dominik Hoppenkamps
 *
 */
public class EMailService 
{
	
	// -----------------
	// Attributes
	// -----------------
	
	private String host;
	
	private static final Logger LOGGER = Logger.getLogger(EMailService.class);

	// -----------------
	// Constructors
	// -----------------
	
	public EMailService()
	{
		// Default Constructor
	}
	
	public EMailService(String host)
	{
		this.host = host;
	}
	
	// -----------------
	// Getter / Setter
	// -----------------
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	// -----------------
	// business logic
	// -----------------
	
	public void sendMail(final String to, final String from, final String subject, final String body) throws MessagingException
	{
		if(LOGGER.isDebugEnabled())
			LOGGER.debug("EMailService.java | sendMail() aufgerufen");
		
		Properties props = new Properties();
		// Setup mail server
		props.setProperty("mail.smtp.host", host);
		// props.setProperty("mail.debug", "true");
		
        Session session = Session.getDefaultInstance(props);

        // TODO validate params

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(body);
            Transport.send(msg);
        } catch(MessagingException e) {
        	LOGGER.error("Fehler beim Versenden der EMail.", e);
        	throw e;
        } catch(Exception e) {
        	LOGGER.error("Fehler beim Versenden der EMail.", e);
        	throw e;
        }
	}
}
