package de.bkah.kundenumfrage.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.commons.collections4.MapUtils;

import de.bkah.kundenumfrage.persistence.DBOperations;

@ManagedBean(name="auswertungBean")
@RequestScoped
public class AuswertungBean implements Serializable {
	
	// -----------------
	// Attributes
	// -----------------
	
	private static final long serialVersionUID = 1610768504408581280L;
	
	private Umfrage umfrage;
	private List<Antwort> antworten;
	
	private static final Logger LOGGER = Logger.getLogger(AuswertungBean.class);
	
	private List<Integer> antwortListe1;
	private List<Integer> antwortListe2;
	private List<Integer> antwortListe3;
	private List<Integer> antwortListe4;
	private List<Integer> antwortListe5;
	
	
	// -----------------
	// Constructors
	// -----------------
	
	@PostConstruct
	public void init() {
		umfrage = new Umfrage();
		umfrage.setId(1);
		
		try {
			antworten = DBOperations.selectErgebnis(umfrage);
		} catch (ReflectiveOperationException | SQLException e) {
			LOGGER.error("Fehler beim Laden des Ergebnisses von Umfrage: "+ umfrage.getId(), e);
			
			// PostConstruct-Methoden können keine Checked-Exceptions werfen. Dies ist ein
			// Fehler in der JSF-Implementierung.
			// Als Workaround wird hier deshalb eine RuntimeException geworfen.
			throw new RuntimeException("Fehler beim Laden des Umfrage-Ergebnisses", e);
		}
		
		this.initLists();
		this.fillLists();
	}
	
	// -----------------
	// Getter / Setter
	// -----------------
	
	public Umfrage getUmfrage() {
		return umfrage;
	}
	
	public void setUmfrage(Umfrage umfrage) {
		this.umfrage = umfrage;
	}
	
	public List<Antwort> getAntworten() {
		return antworten;
	}
	
	public void setAntworten(List<Antwort> antworten) {
		this.antworten = antworten;
	}
	
	public List<Integer> getAntwortListe1() {
		return antwortListe1;
	}

	public void setAntwortListe1(List<Integer> antwortListe1) {
		this.antwortListe1 = antwortListe1;
	}

	public List<Integer> getAntwortListe2() {
		return antwortListe2;
	}

	public void setAntwortListe2(List<Integer> antwortListe2) {
		this.antwortListe2 = antwortListe2;
	}

	public List<Integer> getAntwortListe3() {
		return antwortListe3;
	}

	public void setAntwortListe3(List<Integer> antwortListe3) {
		this.antwortListe3 = antwortListe3;
	}

	public List<Integer> getAntwortListe4() {
		return antwortListe4;
	}

	public void setAntwortListe4(List<Integer> antwortListe4) {
		this.antwortListe4 = antwortListe4;
	}

	public List<Integer> getAntwortListe5() {
		return antwortListe5;
	}

	public void setAntwortListe5(List<Integer> antwortListe5) {
		this.antwortListe5 = antwortListe5;
	}
	
	// -----------------
	// business logic
	// -----------------

	private void fillLists() {
		Antwort antwort;
		for(int i = 0; i < antworten.size(); i++) {
			antwort = antworten.get(i);
			
			// Maps hochzählen für Antwort 1-5
			antwortListe1 = this.countAntwort(antwortListe1, antwort.getAntwort1());
			antwortListe2 = this.countAntwort(antwortListe2, antwort.getAntwort2());
			antwortListe3 = this.countAntwort(antwortListe3, antwort.getAntwort3());
			antwortListe4 = this.countAntwort(antwortListe4, antwort.getAntwort4());
			antwortListe5 = this.countAntwort(antwortListe5, antwort.getAntwort5());
		}
	}
	
	private List<Integer> countAntwort(List<Integer> list, String antwort) {
		List<Integer> newList = list;
		switch(antwort) {
		case "Ausgezeichnet": 
			newList.set(0, list.get(0)+1);
			break;
		case "Gut":
			newList.set(1, list.get(1)+1);
			break;
		case "Befriedigend":
			newList.set(2, list.get(2)+1);
			break;
		case "Ausreichend":
			newList.set(3, list.get(3)+1);
			break;
		case "Mangelhaft":
			newList.set(4, list.get(4)+1);
			break;
		default: break;
		}
		
		return newList;
	}
	
	private void initLists() {
		antwortListe1 =  initList();
		antwortListe2 =  initList();
		antwortListe3 =  initList();
		antwortListe4 =  initList();
		antwortListe5 =  initList();
	}
	
	private List<Integer> initList() {
		List<Integer> initList = new ArrayList<Integer>();
		for(int i = 0; i < 5; i++) {
			initList.add(0);
		}
		
		return initList;
	}
}
