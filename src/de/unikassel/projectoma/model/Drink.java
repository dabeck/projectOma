package de.unikassel.projectoma.model;

public class Drink extends Article {

    private boolean isHot;

    public boolean isHot() {
	return isHot;
    }
    public void setHot(boolean isHot) {
	this.isHot = isHot;
    }
    public Drink withHot(boolean isHot) {
	this.isHot = isHot;
	return this;
    }
    
    
    
}
