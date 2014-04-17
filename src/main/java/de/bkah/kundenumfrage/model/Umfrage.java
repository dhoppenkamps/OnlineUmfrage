package de.bkah.kundenumfrage.model;

import java.sql.Date;

public class Umfrage {
	
	// -----------------
	// Attributes
	// -----------------
	
	private long id;
	private String titel;
	private String frage1;
	private String frage2;
	private String frage3;
	private String frage4;
	private String frage5;
	private Date Beginn;
	private Date Ende;
	private int stimmen;
	
	// -----------------
	// Getters / Setters
	// -----------------
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitel() {
		return titel;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}
	public String getFrage1() {
		return frage1;
	}
	public void setFrage1(String frage1) {
		this.frage1 = frage1;
	}
	public String getFrage2() {
		return frage2;
	}
	public void setFrage2(String frage2) {
		this.frage2 = frage2;
	}
	public String getFrage3() {
		return frage3;
	}
	public void setFrage3(String frage3) {
		this.frage3 = frage3;
	}
	public String getFrage4() {
		return frage4;
	}
	public void setFrage4(String frage4) {
		this.frage4 = frage4;
	}
	public String getFrage5() {
		return frage5;
	}
	public void setFrage5(String frage5) {
		this.frage5 = frage5;
	}
	public Date getBeginn() {
		return Beginn;
	}
	public void setBeginn(Date beginn) {
		Beginn = beginn;
	}
	public Date getEnde() {
		return Ende;
	}
	public void setEnde(Date ende) {
		Ende = ende;
	}
	public int getStimmen() {
		return stimmen;
	}
	public void setStimmen(int stimmen) {
		this.stimmen = stimmen;
	}
}
