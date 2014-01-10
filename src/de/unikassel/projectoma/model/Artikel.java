package de.unikassel.projectoma.model;

import java.sql.Timestamp;

public class Artikel {

	private String name;
	
	private Timestamp start;
	private Timestamp dauer;
	private double fortschritt;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Artikel withName(String name) {
		this.name = name;
		return this;
	}
	
	public Timestamp getStart() {
		return start;
	}
	public void setStart(Timestamp start) {
		this.start = start;
	}
	public Artikel withStart(Timestamp start) {
		this.start = start;
		return this;
	}
	
	public Timestamp getDauer() {
		return dauer;
	}
	public void setDauer(Timestamp dauer) {
		this.dauer = dauer;
	}
	public Artikel withDauer(Timestamp dauer) {
		this.dauer = dauer;
		return this;
	}
	
	public double getFortschritt() {
		return fortschritt;
	}
	public void setFortschritt(double fortschritt) {
		this.fortschritt = fortschritt;
	}
	public Artikel withFortschritt(double fortschritt) {
		this.fortschritt = fortschritt;
		return this;
	}
	
}
