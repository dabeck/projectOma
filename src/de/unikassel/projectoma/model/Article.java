package de.unikassel.projectoma.model;

import java.sql.Timestamp;
import java.util.Calendar;

public class Article {

	private String name;
	
	private Calendar start;
	private Timestamp duration;
	private double progress;
	protected RequestType ArticleType; 
	
	
	
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
	
	public RequestType getArticleType() {
		return ArticleType;
	}
	
	public Calendar getStart() {
		return start;
	}
	public void setStart(Calendar start) {
		this.start = start;
	}
	public Article withStart(Calendar start) {
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
	
	
	/* replace this checkDeadline-Methods with new FailReceiver (better solution)
	 * private Timestamp getNow() {
		return new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
	}
	private Timestamp getDeadline() {
		return new Timestamp(
				start.get(Calendar.YEAR) + duration.getYear(),
				start.get(Calendar.MONTH) + duration.getMonth(),
				start.get(Calendar.DAY_OF_MONTH) + duration.getDay(),
				start.get(Calendar.HOUR_OF_DAY) + duration.getHours(),
				start.get(Calendar.MINUTE) + duration.getMinutes(),
				start.get(Calendar.SECOND) + duration.getSeconds(),
				0 + duration.getNanos()
			);
	}
	public boolean checkStatus() {
		if(getNow().after(getDeadline())) {
		    return true;
		} else {
		    return false;
		}
	}*/
}
