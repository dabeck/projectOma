package de.unikassel.projectoma.model;

public class Medicine extends Article {

	private Daytime type;

	public Daytime getTyp() {
		return type;
	}
	
	public void setTyp(Daytime type) {
		this.type = type;
	}
	
	public Medicine withTyp(Daytime type) {
		this.type = type;
		return this;
	}
	
}