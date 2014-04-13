package de.bkah.kundenumfrage.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EMailService 
{
	
	// -----------------
	// Attributes
	// -----------------
	private String host;

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
	
	public void sendMail(final String to, final String from, final String subject, final String body)
	{
		// TODO Replace System.out with logging!
		System.out.println("EMailService.java | sendMail() aufgerufen");
		
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
        } catch (AddressException e) {
        	System.out.println("EMailService.java | "+ e);
        } catch (MessagingException e) {
        	System.out.println("EMailService.java | "+ e);
		} catch (Exception e){
			// TODO remove
			System.out.println("EMailService.java | "+ e);
		}
	}
}
