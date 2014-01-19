package de.unikassel.projectoma;

import android.app.Application;
import android.os.Bundle;
import de.unikassel.projectoma.model.Grandma;
import de.unikassel.projectoma.model.LevelType;


public class GrandmaApplication extends Application {
	
	public Grandma grandma;
	
	protected void onCreate(Bundle savedInstanceState) {
		this.grandma = new Grandma(this.getApplicationContext().getString(
		        R.string.default_name), LevelType.SIMPLE);
	}

	public Grandma getGrandma() {
	    return grandma;
    }

	public void setGrandma(Grandma grandma) {
	    this.grandma = grandma;
    }
}
