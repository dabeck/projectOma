package de.unikassel.projectoma.model;

public class Medicine extends Article {

	private MedicineType typ;

	public MedicineType getTyp() {
		return typ;
	}
	public void setTyp(MedicineType typ) {
		this.typ = typ;
	}
	public Medicine withTyp(MedicineType typ) {
		this.typ = typ;
		return this;
	}
	
}