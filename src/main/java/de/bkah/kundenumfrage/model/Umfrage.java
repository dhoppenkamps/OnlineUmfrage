package de.bkah.kundenumfrage.model;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.mail.MessagingException;

import de.bkah.kundenumfrage.email.EMailService;

/**
 * 
 * @author Dominik Hoppenkamps
 *
 */
@ManagedBean(name="umfrageBean")
@RequestScoped
public class Umfrage implements Serializable
{
	// -----------------
	// Attributes
	// -----------------
	
	private static final long serialVersionUID = 1L;
	
	private String msg;
	
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
	
	public String sendMail() throws MessagingException
	{
		// TODO Replace System.out with logging!
		
		System.out.println("Umfrage.java | sendMail() aufgerufen");
		EMailService mailService = new EMailService();
		String to 		= "test1@localhost";
		String from 	= "admin@localhost";
		String subject 	= "Testing Mail-Service...";
		String body 	= "If you receive this, Mail-Service is working";
		
		mailService.setHost("localhost");
		mailService.sendMail(to, from, subject, body);
		
		return null;
	}
}
