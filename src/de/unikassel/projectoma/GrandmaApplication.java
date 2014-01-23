package de.unikassel.projectoma;

import android.app.Application;
import android.os.Bundle;
import de.unikassel.projectoma.model.Grandma;


public class GrandmaApplication extends Application {
	
	public Grandma grandma;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate();
	}

	public Grandma getGrandma() {
	    return grandma;
    }

	public void setGrandma(Grandma grandma) {
	    this.grandma = grandma;
    }
}
