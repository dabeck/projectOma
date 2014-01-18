package de.unikassel.projectoma.model;

import java.sql.Timestamp;

public class Article {

	private String name;
	
	private Timestamp start;
	private Timestamp duration;
	private double progress;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Article withName(String name) {
		this.name = name;
		return this;
	}
	
	public Timestamp getStart() {
		return start;
	}
	public void setStart(Timestamp start) {
		this.start = start;
	}
	public Article withStart(Timestamp start) {
		this.start = start;
		return this;
	}
	
	public Timestamp getDuration() {
		return duration;
	}
	public void setDuration(Timestamp duration) {
		this.duration = duration;
	}
	public Article withDuration(Timestamp duration) {
		this.duration = duration;
		return this;
	}
	
	public double getProgress() {
		return progress;
	}
	public void setProgress(double progress) {
		this.progress = progress;
	}
	public Article withProgress(double progress) {
		this.progress = progress;
		return this;
	}
	
}
