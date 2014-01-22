package de.unikassel.projectoma.model;

public class Medicine extends Article {

	private Daytime typ;

	public Daytime getTyp() {
		return typ;
	}
	public void setTyp(Daytime typ) {
		this.typ = typ;
	}
	public Medicine withTyp(Daytime typ) {
		this.typ = typ;
		return this;
	}
	
}