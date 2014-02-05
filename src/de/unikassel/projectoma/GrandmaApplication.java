package de.unikassel.projectoma;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

    public void resetGame() {
	AlarmManager mgr = (AlarmManager)this.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
	Editor edit = PreferenceManager.getDefaultSharedPreferences(
		this.getApplicationContext()).edit();
	
	// Loesche geplante und jetzt obsolente Alarms
	//for(PendingIntent pi: this.grandma.getAlarms().values()) 
	   // mgr.cancel(pi);
	
	// Setze Oma null
	this.setGrandma(null);
	edit.putString("de.unikassel.projectoma.grandma", null);
	edit.commit();
	
	// App-Restart
	Intent mStartActivity = new Intent(this.getApplicationContext(), MainActivity.class);
	int mPendingIntentId = 123456;
	PendingIntent mPendingIntent = PendingIntent.getActivity(this.getApplicationContext(),
		mPendingIntentId,
		mStartActivity,
		PendingIntent.FLAG_CANCEL_CURRENT);
	mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
	System.exit(0);
    }
}
