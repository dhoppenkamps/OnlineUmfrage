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
	
	private Map<Integer,Integer> antworten1;
	private Map<Integer,Integer> antworten2;
	private Map<Integer,Integer> antworten3;
	private Map<Integer,Integer> antworten4;
	private Map<Integer,Integer> antworten5;
	
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
		FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		System.out.println("init() aufgerufen");
		umfrage = new Umfrage();
		umfrage.setId(1);
		
		try {
			antworten = DBOperations.selectErgebnis(umfrage);
		} catch (ReflectiveOperationException | SQLException e) {
			LOGGER.error("Fehler beim Laden des Ergebnisses von Umfrage: "+ umfrage.getId(), e);
			
			// PostConstruct-Methoden k�nnen keine Checked-Exceptions werfen. Dies ist ein
			// Fehler in der JSF-Implementierung.
			// Als Workaround wird hier deshalb eine RuntimeException geworfen.
			throw new RuntimeException("Fehler beim Laden des Umfrage-Ergebnisses", e);
		}
		
//		this.initMaps();
//		this.fillMaps();
//		this.convertMapsToList();
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

	/*
	private void convertMapsToList(){
		antwortListe1 =  new ArrayList<Integer>(antworten1.values());
		antwortListe2 =  new ArrayList<Integer>(antworten2.values());
		antwortListe3 =  new ArrayList<Integer>(antworten3.values());
		antwortListe4 =  new ArrayList<Integer>(antworten4.values());
		antwortListe5 =  new ArrayList<Integer>(antworten5.values());
	}
	
	private void fillMaps() {
		Antwort antwort;
		for(int i = 0; i < antworten.size(); i++) {
			antwort = antworten.get(i);
			
			// Maps hochz�hlen f�r Antwort 1-5
			System.out.println("Map 1: "+ antworten1.toString());
			this.countAntwort(antworten1, antwort.getAntwort1());
			this.countAntwort(antworten2, antwort.getAntwort2());
			this.countAntwort(antworten3, antwort.getAntwort3());
			this.countAntwort(antworten4, antwort.getAntwort4());
			this.countAntwort(antworten5, antwort.getAntwort5());
		}
	}
	
	private Map<Integer,Integer> countAntwort(Map<Integer,Integer> map, String antwort) {
		switch(antwort) {
		case "Ausgezeichnet": 
			map.put(Integer.valueOf(1), map.get(Integer.valueOf(1))+1);
			break;
		case "Gut":
			map.put(Integer.valueOf(2), map.get(Integer.valueOf(2))+1);
			break;
		case "Befriedigend":
			map.put(Integer.valueOf(3), map.get(Integer.valueOf(3))+1);
			break;
		case "Ausreichend":
			map.put(Integer.valueOf(4), map.get(Integer.valueOf(4))+1);
			break;
		case "Mangelhaft":
			map.put(Integer.valueOf(5), map.get(Integer.valueOf(5))+1);
			break;
		default: break;
		}
		
		return map;
	}
	
	private void initMaps() {
		antworten1 = MapUtils.putAll(new HashMap<Integer,Integer>(), new int[][] {
		     {Integer.valueOf(1), Integer.valueOf(0)},
		     {Integer.valueOf(2), Integer.valueOf(0)},
		     {Integer.valueOf(3), Integer.valueOf(0)},
		     {Integer.valueOf(4), Integer.valueOf(0)},
		     {Integer.valueOf(5), Integer.valueOf(0)}
		}); 
		antworten2 = MapUtils.putAll(new HashMap<Integer,Integer>(), new int[][] {
			{Integer.valueOf(1), Integer.valueOf(0)},
		     {Integer.valueOf(2), Integer.valueOf(0)},
		     {Integer.valueOf(3), Integer.valueOf(0)},
		     {Integer.valueOf(4), Integer.valueOf(0)},
		     {Integer.valueOf(5), Integer.valueOf(0)}
		}); 
		antworten3 = MapUtils.putAll(new HashMap<Integer,Integer>(), new int[][] {
			{Integer.valueOf(1), Integer.valueOf(0)},
		     {Integer.valueOf(2), Integer.valueOf(0)},
		     {Integer.valueOf(3), Integer.valueOf(0)},
		     {Integer.valueOf(4), Integer.valueOf(0)},
		     {Integer.valueOf(5), Integer.valueOf(0)}
		}); 
		antworten4 = MapUtils.putAll(new HashMap<Integer,Integer>(), new int[][] {
			{Integer.valueOf(1), Integer.valueOf(0)},
		     {Integer.valueOf(2), Integer.valueOf(0)},
		     {Integer.valueOf(3), Integer.valueOf(0)},
		     {Integer.valueOf(4), Integer.valueOf(0)},
		     {Integer.valueOf(5), Integer.valueOf(0)}
		}); 
		antworten5 = MapUtils.putAll(new HashMap<Integer,Integer>(), new int[][] {
			{Integer.valueOf(1), Integer.valueOf(0)},
		     {Integer.valueOf(2), Integer.valueOf(0)},
		     {Integer.valueOf(3), Integer.valueOf(0)},
		     {Integer.valueOf(4), Integer.valueOf(0)},
		     {Integer.valueOf(5), Integer.valueOf(0)}
		}); 
	}
	*/
}
