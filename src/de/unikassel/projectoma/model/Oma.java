package de.unikassel.projectoma.model;

import java.util.List;

import com.google.gson.Gson;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Oma {

	/* Name der Oma. */
	private String name;
	/* Schwierigkeitsgrad des Spiels. */
	private LevelType level;
	/* Wunsch, welcher aktuell abgearbeitet wird. */
	private Artikel taetigkeit;
	
	
	private List<Artikel> wuensche;
	private List<Artikel> einkaufsliste;
	
	private List<Essen> speisekammer;
	private List<Artikel> vorratskammer;
	private List<Kleidung> kleiderschrank;
	
	
	public void save(SharedPreferences sp) {
		Editor edit = sp.edit();
		Gson gson = new Gson();
		String json = gson.toJson(this);
		edit.putString("de.unikassel.projectoma.oma", json);
		edit.commit();
	}
	public static Oma load(SharedPreferences sp) {
		Gson gson = new Gson();
		String json = sp.getString("de.unikassel.projectoma.oma", "");
		Oma o = gson.fromJson(json, Oma.class);
		return o;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Oma withName(String name) {
		this.name = name;
		return this;
	}
	
	public LevelType getLevel() {
		return level;
	}
	public void setLevel(LevelType level) {
		this.level = level;
	}
	public Oma withLevel(LevelType level) {
		this.level = level;
		return this;
	}
	
	public Artikel getTaetigkeit() {
		return taetigkeit;
	}
	public void setTaetigkeit(Artikel taetigkeit) {
		this.taetigkeit = taetigkeit;
	}
	public Oma withTaetigkeit(Artikel taetigkeit) {
		this.taetigkeit = taetigkeit;
		return this;
	}
	
	public List<Artikel> getWuensche() {
		return wuensche;
	}
	public void setWuensche(List<Artikel> wuensche) {
		this.wuensche = wuensche;
	}
	public Oma withWuensche(List<Artikel> wuensche) {
		this.setWuensche(wuensche);
		return this;
	}
	
	public List<Artikel> getEinkaufsliste() {
		return einkaufsliste;
	}
	public void setEinkaufsliste(List<Artikel> einkaufsliste) {
		this.einkaufsliste = einkaufsliste;
	}
	public Oma withEinkaufsliste(List<Artikel> einkaufsliste) {
		this.einkaufsliste = einkaufsliste;
		return this;
	}
	
	public List<Essen> getSpeisekammer() {
		return speisekammer;
	}
	public void setSpeisekammer(List<Essen> speisekammer) {
		this.speisekammer = speisekammer;
	}
	public Oma withSpeisekammer(List<Essen> speisekammer) {
		this.speisekammer = speisekammer;
		return this;
	}
	
	public List<Artikel> getVorratskammer() {
		return vorratskammer;
	}
	public void setVorratskammer(List<Artikel> vorratskammer) {
		this.vorratskammer = vorratskammer;
	}
	public Oma withVorratskammer(List<Artikel> vorratskammer) {
		this.vorratskammer = vorratskammer;
		return this;
	}
	
	public List<Kleidung> getKleiderschrank() {
		return kleiderschrank;
	}
	public void setKleiderschrank(List<Kleidung> kleiderschrank) {
		this.kleiderschrank = kleiderschrank;
	}
	public Oma withKleiderschrank(List<Kleidung> kleiderschrank) {
		this.kleiderschrank = kleiderschrank;
		return this;
	}
	
	
	
	public Oma() {
		this.name = "Oma";
		this.level = LevelType.PFLEGELEICHT;
	}
	public Oma(String name, LevelType level) {
		this.name = name;
		this.level = level;
	}
	
	
	
	public void essen(Essen e) {
		System.out.println("TODO: Oma.essen(Essen e);");
	}
	public void trinken(Getraenk g) {
		System.out.println("TODO: Oma.trinken(Getraenk g);");
	}
	public void kurieren(Medizin m) {
		System.out.println("TODO: Oma.kurieren(Medizin m);");
	}
	public void einkaufen(Artikel a) {
		System.out.println("TODO: Oma.einkaufen(Artikel a);");
	}
	
	
	
	public void putzen() {
		System.out.println("TODO: Oma.putzen();");
	}
	public void waschen() {
		System.out.println("TODO: Oma.waschen();");
	}
	public void spuelen() {
		System.out.println("TODO: Oma.spuelen();");
	}
	public void schlafen() {
		System.out.println("TODO: Oma.schlafen();");
	}


	
	/*
	public void fernsehn() {
		System.out.println("TODO: Oma.fernsehn();");
	}
	//public void gesundEssen(Essen e) {
	//	System.out.println("TODO: Oma.gesundEssen(Essen e);");
	//}
	public void spazieren() {
		System.out.println("TODO: Oma.spazieren();");
	}
	public void musikHoeren() {
		System.out.println("TODO: Oma.musikHoeren();");
	}
	public void spielen(Raetsel r) {
		// evtl. Sudoku-Raetsel?
		System.out.println("TODO: Oma.spielen(Raetsel r);");
	}
	*/
	
}
