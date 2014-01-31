package de.unikassel.projectoma;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import de.unikassel.projectoma.model.Article;
import de.unikassel.projectoma.model.Grandma;
import de.unikassel.projectoma.model.LevelType;
import de.unikassel.projectoma.reciever.DailyReciever;
import de.unikassel.projectoma.R;
import de.unikassel.projectoma.helper.ImageHelper;

public class MainActivity extends ListActivity {
	
	// LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
	ArrayList<String> listItems = new ArrayList<String>();
	
	// DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE
	// LISTVIEW
	ArrayAdapter<String> adapter;
	
	// RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
	int clickCounter = 0;

	GrandmaApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.app = ((GrandmaApplication) getApplicationContext());
		setContentView(R.layout.activity_main);

		adapter = new ArrayAdapter<String>(this,
		        android.R.layout.simple_list_item_1, listItems);
		setListAdapter(adapter);
		
		ImageHelper.setContext(this);
		
		ImageHelper.setGrandmaTypeHelp();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is
		// present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_settings:
				startActivity(new Intent(this, SettingsActivity.class));
				return true;
			case R.id.about:
				new AlertDialog.Builder(this).setTitle(R.string.about)
				        .setMessage("Android is shit!!!111")
				        .setIcon(R.drawable.ic_launcher).show();
				
				return true;
		}
		return false;
	}

	public void btnRequestClicked(View v) {
		listItems.add("RequestClicked");
		adapter.notifyDataSetChanged();
	}
	
	public void btnStockClicked(View v) {
		listItems.add("StockClicked");
		adapter.notifyDataSetChanged();
		
		ImageHelper.setGrandmaTypeInCar();
	}
	
	public void btnTestClicked(View v) {
		listItems.add("Playing new GIF : " + clickCounter++);
		adapter.notifyDataSetChanged();
		
		
		switch(clickCounter%8) {
		case 1:
			ImageHelper.setGrandmaTypeCooking();
			break;
		case 2:
			ImageHelper.setGrandmaTypeHelp();
			break;
		case 3:
			ImageHelper.setGrandmaTypeHungry();
			break;
		case 4:
			ImageHelper.setGrandmaTypeIll();
			break;
		case 5:
			ImageHelper.setGrandmaTypeInCar();
			break;
		case 6:
			ImageHelper.setGrandmaTypeWashing();
			break;
		case 7:
			ImageHelper.setGrandmaTypeMusic();
			break;
		default:
			ImageHelper.setGrandmaTypeHelp();
			break;
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		this.app.grandma.save(PreferenceManager
		        .getDefaultSharedPreferences(this
		        .getApplicationContext()));
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		/* Lade Oma */
		this.app.grandma = Grandma.load(PreferenceManager
		        .getDefaultSharedPreferences(this.getApplicationContext()));
		
		if (this.app.grandma == null) {
			this.app.grandma = new Grandma(this.getApplicationContext()
			        .getString(R.string.default_name), LevelType.SIMPLE);
			
			// TODO: set firstStart bool
			
			/* Taeglich wiederholender 'Alarm', welcher Wuensche fuer die
			 * naechsten 24 Stunden erzeugt. (Nur beim ersten Start erzeugen!) */
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			AlarmManager alarmMgr = (AlarmManager) this.getApplicationContext()
					.getSystemService(this.getApplicationContext().ALARM_SERVICE);
			Intent intent = new Intent(this.getApplicationContext(), DailyReciever.class);
			PendingIntent alarmIntent = PendingIntent.getBroadcast(this
					.getApplicationContext(), 0, intent, 0);
			alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar
					.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
		}

		 /* Spielende?!? */
		 // pruefe alle Deadlines, jeweils fuer Wuensche und
		// Einkaufsliste
		for (Article wish : this.app.grandma.getWishes()) {
			checkDeadline(wish);
		}
		for (Article wish : this.app.grandma.getShoppingList()) {
			checkDeadline(wish);
		}
	}
	
	private void checkDeadline(Article wish) {
		if (!wish.checkStatus()) {
			/* TODO: Article casten und Toast "Wunsch XY" praezisieren... */
			
			// GameOver-Toast
			Toast t = Toast.makeText(
					this.app.getApplicationContext(),
			        "GAME OVER: Der Wunsch '" + wish.getName()
			                + "' wurde nicht rechtzeitig erfüllt!",
					Toast.LENGTH_LONG
				);
			t.show();
			
			Editor edit = PreferenceManager.getDefaultSharedPreferences(
			        this.getApplicationContext()).edit();
			edit.putString("de.unikassel.projectoma.grandma", null);
			edit.commit();

		}
	}
}
