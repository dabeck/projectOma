package de.unikassel.projectoma.model;

public class Medizin extends Artikel {

	private MedizinType typ;

	public MedizinType getTyp() {
		return typ;
	}
	public void setTyp(MedizinType typ) {
		this.typ = typ;
	}
	public Medizin withTyp(MedizinType typ) {
		this.typ = typ;
		return this;
	}
	
}