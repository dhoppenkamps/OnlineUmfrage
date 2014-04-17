package de.bkah.kundenumfrage.model;

public class Antwort {
	
	// -----------------
	// Attributes
	// -----------------
	
	private long id;
	private String antwort1;
	private String antwort2;
	private String antwort3;
	private String antwort4;
	private String antwort5;
	private Umfrage umfrage;
	
	// -----------------
	// Getters / Setters
	// -----------------
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAntwort1() {
		return antwort1;
	}
	public void setAntwort1(String antwort1) {
		this.antwort1 = antwort1;
	}
	public String getAntwort2() {
		return antwort2;
	}
	public void setAntwort2(String antwort2) {
		this.antwort2 = antwort2;
	}
	public String getAntwort3() {
		return antwort3;
	}
	public void setAntwort3(String antwort3) {
		this.antwort3 = antwort3;
	}
	public String getAntwort4() {
		return antwort4;
	}
	public void setAntwort4(String antwort4) {
		this.antwort4 = antwort4;
	}
	public String getAntwort5() {
		return antwort5;
	}
	public void setAntwort5(String antwort5) {
		this.antwort5 = antwort5;
	}
	public Umfrage getUmfrage() {
		return umfrage;
	}
	public void setUmfrage(Umfrage umfrage) {
		this.umfrage = umfrage;
	}

}
