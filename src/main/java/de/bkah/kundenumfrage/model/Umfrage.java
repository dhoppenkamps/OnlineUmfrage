package de.bkah.kundenumfrage.model;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="umfrageBean")
@SessionScoped
public class Umfrage implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	private String msg;
	
	@PostConstruct
	public void init()
	{
		msg = "Hello World!";
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
