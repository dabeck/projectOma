package de.unikassel.projectoma.model;

import java.sql.Timestamp;
import java.util.Calendar;

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
	
	
	private Timestamp getNow() {
		return new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
	}
	private Timestamp getDeadline() {
		return new Timestamp(
				start.getYear() + duration.getYear(),
				start.getMonth() + duration.getMonth(),
				start.getDay() + duration.getDay(),
				start.getHours() + duration.getHours(),
				start.getMinutes() + duration.getMinutes(),
				start.getSeconds() + duration.getSeconds(),
				start.getNanos() + duration.getNanos()
			);
	}
	public boolean checkStatus() {
		if(getNow().after(getDeadline()) && this.getProgress() < 1.0) {
			return true;
		} else {
			return false;
		}
	}
}
