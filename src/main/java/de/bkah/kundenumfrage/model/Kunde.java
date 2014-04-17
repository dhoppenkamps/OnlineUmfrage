package de.bkah.kundenumfrage.model;

public class Kunde {
	
	// -----------------
	// Attributes
	// -----------------
	private long id;
	private String vorname;
	private String name;
	private String email;
	
	// -----------------
	// Getter / Setter
	// -----------------
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

	
}
