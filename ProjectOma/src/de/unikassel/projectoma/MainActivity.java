package de.unikassel.projectoma;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import de.unikassel.projectoma.model.Article;
import de.unikassel.projectoma.model.Grandma;
import de.unikassel.projectoma.model.LevelType;
import de.unikassel.projectoma.model.RequestType;
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

	//<-- Shake sensor listening -->

	private SensorManager mSensorManager;
	private float mAccel;
	private float mAccelCurrent;
	private float mAccelLast;

	private final SensorEventListener mSensorListener = new SensorEventListener() {

		public void onSensorChanged(SensorEvent se) {
			float x = se.values[0];
			float y = se.values[1];
			float z = se.values[2];
			mAccelLast = mAccelCurrent;
			mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
			float delta = mAccelCurrent - mAccelLast;
			mAccel = mAccel * 0.9f + delta; // perform low-cut filter
			
			if(mAccel > 12)
			{
				listItems.add("Phone shaked!!");
				adapter.notifyDataSetChanged();
				processRequest(RequestType.wash_clothes);
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};

	//<!-- Shake sensor end -->

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		app = ((GrandmaApplication) getApplication());
		setContentView(R.layout.activity_main);
		
	    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	    mAccel = 0.00f;
	    mAccelCurrent = SensorManager.GRAVITY_EARTH;
	    mAccelLast = SensorManager.GRAVITY_EARTH;

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listItems);
		setListAdapter(adapter);

		ImageHelper.setContext(this);

		ImageHelper.setGrandmaTypeInCar();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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

	//<-- Button-actions start here -->

	public void btnRequestClicked(View v) {

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag("dialog");

		if (prev != null)
		{
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		// Create and show the dialog.
		RequestFragment newFragment = RequestFragment.newInstance();
		newFragment.show(ft, "dialog");

		listItems.add("RequestClicked");
		adapter.notifyDataSetChanged();
	}


	public void btnStockClicked(View v) {
		listItems.add("StockClicked");
		adapter.notifyDataSetChanged();

		ImageHelper.setGrandmaTypeInCar();
	}

	public void btnTestClicked(View v) {
		listItems.add("Test : " + clickCounter++);
		adapter.notifyDataSetChanged();
	}

	//<!-- Button actions end -->

	//<-- Process actions start here -->

	public void processRequest(RequestType selection)
	{
		Log.i("ProjectOma", "Trying to process request " + selection.toString());

		switch(selection) {
		case cook:
			ImageHelper.setGrandmaTypeCooking();
			//Eating depends on cooking
			break;
		case eat:
			ImageHelper.setGrandmaTypeEat();
			//Generates dirty dishes + reduces food stock
			break;
		case drink:
			ImageHelper.setGrandmaTypeDrink();
			//Generates dirty dishes + reduces water stock
			break;
		case wash_dishes:
			ImageHelper.setGrandmaTypeDoCleanDishes();
			//Generates clean dishes
			break;
		case wash_clothes:
			ImageHelper.setGrandmaTypeWashing();
			//Fill up clothes stock
			break;
		case sleep:
			ImageHelper.setGrandmaTypeSleep();
			//Resets stamina
			break;
		case medicine:
			ImageHelper.setGrandmaTypeMedicine();
			//Resets stamina
			break;
		case walk:
			ImageHelper.setGrandmaTypeWalk();
			//Reduces stamina
			break;
		case clean_car:
			ImageHelper.setGrandmaTypeCleanCar();
			//. ...
			break;
		case music:
			ImageHelper.setGrandmaTypeMusic();
			//Resets stamina
			break;
		case shopping:
			ImageHelper.setGrandmaTypeGoShopping();
			//Fill up stocks
			break;
		default:
			break;
		}
	}

	//<!-- Process actions end-->


	@Override
	protected void onPause() {
		super.onPause();
		
		//Unregister listener for shake
	    mSensorManager.unregisterListener(mSensorListener);

		app.grandma.save(PreferenceManager
				.getDefaultSharedPreferences(this
						.getApplicationContext()));
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		//Register listener for shake
		mSensorManager.registerListener(
				mSensorListener, 
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL
				);

		/* Lade Oma */
		app.grandma = Grandma.load(PreferenceManager
				.getDefaultSharedPreferences(this.getApplicationContext()));

		if (app.grandma == null) {
			app.grandma = new Grandma(this.getApplicationContext()
					.getString(R.string.default_name), LevelType.SIMPLE);

			/* Taeglich wiederholender 'Alarm', welcher Wuensche fuer die
			 * naechsten 24 Stunden erzeugt. (Nur beim ersten Start erzeugen!) */
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());

			@SuppressWarnings("static-access")
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
		for (Article wish : app.grandma.getWishes()) {
			checkDeadline(wish);
		}
		for (Article wish : app.grandma.getShoppingList()) {
			checkDeadline(wish);
		}
	}

	private void checkDeadline(Article wish) {
		if (!wish.checkStatus()) {
			/* TODO: Article casten und Toast "Wunsch XY" praezisieren... */

			// GameOver-Toast
			Toast t = Toast.makeText(
					app.getApplicationContext(),
					"GAME OVER: Der Wunsch '" + wish.getName()
					+ "' wurde nicht rechtzeitig erf�llt!",
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
