package de.unikassel.projectoma;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.gson.Gson;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import de.unikassel.projectoma.model.Bed;
import de.unikassel.projectoma.model.Clothing;
import de.unikassel.projectoma.model.Daytime;
import de.unikassel.projectoma.model.Dishes;
import de.unikassel.projectoma.model.Drink;
import de.unikassel.projectoma.model.Food;
import de.unikassel.projectoma.model.Grandma;
import de.unikassel.projectoma.model.House;
import de.unikassel.projectoma.model.LevelType;
import de.unikassel.projectoma.model.Medicine;
import de.unikassel.projectoma.model.RequestType;
import de.unikassel.projectoma.reciever.DailyReciever;
import de.unikassel.projectoma.reciever.DoneReciever;
import de.unikassel.projectoma.R;
import de.unikassel.projectoma.fragment.FeedFragment;
import de.unikassel.projectoma.fragment.MedicineFragment;
import de.unikassel.projectoma.fragment.RequestFragment;
import de.unikassel.projectoma.fragment.ShoppingFragment;
import de.unikassel.projectoma.fragment.StockFragment;
import de.unikassel.projectoma.helper.ImageHelper;

public class MainActivity extends ListActivity implements PropertyChangeListener {

    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    GrandmaApplication app;
    Boolean performingRequest = false;

    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

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

	ImageHelper.setGrandmaTypeInCar(null);
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

    //<-- Shake sensor listening -->

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
		Log.i("ProjectOma", "Phone shaked!!");
		processRequest(RequestType.wash_clothes);
	    }
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}
    };

    //<!-- Shake sensor end -->

    //<-- Button-actions start here -->

    public void btnRequestClicked(View v) {

	RequestFragment newFragment = RequestFragment.newInstance();
	newFragment.show(getFragmentManager(), "requestDialog");
    }


    public void btnStockClicked(View v) {
	StockFragment newFragment = StockFragment.newInstance();
	newFragment.show(getFragmentManager(), "stockDialog");

	ImageHelper.setGrandmaTypeInCar(null);
    }

    public void btnTestClicked(View v) {
	Article wish = (Article)Food.randomFood(Daytime.EVENING);

	Calendar cal = Calendar.getInstance();
	cal.setTimeInMillis(System.currentTimeMillis() + 5000);

	wish.setStart(cal);

	DailyReciever.setAlarm(app.getGrandma(), app.getApplicationContext(), wish);

	listItems.add(wish.getStart().getTime().toLocaleString() +
		" " + wish.getName() +
		" (" + wish.getDuration().getMinutes() + "min)");

	adapter.notifyDataSetChanged();
    }

    //<!-- Button actions end -->

    //<-- Process actions start here -->

    /**
     * Process a general request with a given requestType
     * 
     * @param selection the type of request you want to satisfy
     */
    public void processRequest(RequestType selection) {
	Log.i("ProjectOma", "Trying to process request " + selection.toString());

	if(performingRequest)
	{
	    Log.i("ProjectOma", "Already performing a request");
	    return;
	}

	switch(selection) {
	case drink:
	    performingRequest = app.getGrandma().drink(new Drink().withHot(false));
	    break;
	case wash_dishes:
	    performingRequest = app.getGrandma().washDishes();
	    break;
	case wash_clothes:
	    performingRequest = app.getGrandma().washClothes();
	    break;
	case sleep:
	    performingRequest = app.getGrandma().sleep();
	    break;
	case clean_car:
	    performingRequest = app.getGrandma().clean();
	    break;
	case eat:
	    FeedFragment newFragment = FeedFragment.newInstance();
	    newFragment.show(getFragmentManager(), "foodDialog");
	    break;
	case medicine:
	    MedicineFragment mediFragment = MedicineFragment.newInstance();
	    mediFragment.show(getFragmentManager(), "administerDialog");
	    //Resets stamina
	    break;
	case shopping:
	    ShoppingFragment shop = ShoppingFragment.newInstance();
	    shop.show(getFragmentManager(), "shopDialog");
	    break;
	case music:
	    ImageHelper.setGrandmaTypeMusic(null);

	    //TODO: open youtube song!
	    //Resets stamina
	    break;
	default:
	    break;
	}
    }

    /**
     * Process a feeding request with a given foodType
     * 
     * @param food the type of food you want to serve
     */
    public void processFeeding(Food food) {
	Log.i("ProjectOma", "Trying to process feedrequest " + food.getName());
	performingRequest = app.getGrandma().eat(food);
    }

    /**
     * Process a medicine request
     * 
     * @param med Type of medicine to administer
     */
    public void processMedicine(Daytime med) {
	performingRequest = app.getGrandma().cure(new Medicine().withTyp(med));
    }

    /**
     * Process a shopping request
     * 
     * @param selectedItems A list of selected food to buy
     */
    public void processShopping(ArrayList<Food> selectedItems) {
	app.getGrandma().buy(selectedItems);

	adapter.notifyDataSetChanged();

	ImageHelper.setGrandmaTypeGoShopping(null);
	// GameOver-Toast
	Toast t = Toast.makeText(
		app.getApplicationContext(),
		app.getApplicationContext().getString(R.string.msg_shopping_positive),
		Toast.LENGTH_LONG
		);
	t.show();
    }
    //<!-- Process actions end-->


    @Override
    protected void onPause() {
	super.onPause();

	//Unregister listener for shake
	mSensorManager.unregisterListener(mSensorListener);

	//Unregister listener for shake
	app.grandma.removePropertyChangeListener(this);

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

	/* Erster Start?!? */
	if (app.grandma == null) {
	    firstStart();
	}

	// Register listener for grandma-Object
	app.grandma.update();
	app.grandma.addPropertyChangeListener(this);

	for (Article wish : app.grandma.getWishList()) {
	    checkDeadline(wish);
	}

	for (Article wish : app.grandma.getShoppingList()) {
	    checkDeadline(wish);
	}

	for (Article wish : app.grandma.getWishList()) {
	    listItems.add(wish.getName());
	    adapter.notifyDataSetChanged();
	}
    }

    private void checkDeadline(Article wish)
    {
	if (!wish.checkStatus())
	{
	    /* TODO: Article casten und Toast "Wunsch XY" praezisieren... */

	    // GameOver-Toast
	    Toast t = Toast.makeText(
		    app.getApplicationContext(),
		    app.getApplicationContext().getString(R.string.gameover) +
		    ": " + 
		    app.getApplicationContext().getString(R.string.gameover_desc),
		    Toast.LENGTH_LONG
		    );
	    t.show();

	    app.resetGame();
	}
    }

    private void firstStart() {
	app.grandma = new Grandma(this.getApplicationContext()
		.getString(R.string.default_name), LevelType.SIMPLE);

	app.grandma.save(PreferenceManager
		.getDefaultSharedPreferences(this.getApplicationContext()));

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

	// Alarm-Intent merken, um bei Spielende/Reset entfernen zu koennen.
	//app.grandma.getAlarms().put(intent, alarmIntent);

	//Willkommensnachricht bei erstem Start
	Toast t = Toast.makeText(
		app.getApplicationContext(),
		app.getApplicationContext().getString(R.string.welcome_desc),
		Toast.LENGTH_LONG
		);
	t.show();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void propertyChange(PropertyChangeEvent e) {
	if (e.getPropertyName().equals("name")) {
	    // TODO: grandma.name has changed
	} else if (e.getPropertyName().equals("level")) {
	    // TODO: grandma.level has changed
	} else if (e.getPropertyName().equals("currentAction")) {
	    onCurrentActionChanged((Article)e.getNewValue(), (Article)e.getOldValue());
	} else if (e.getPropertyName().equals("wishes")) {
	    onWishListChanged((List<Article>)e.getNewValue(), (List<Article>)e.getOldValue());
	}
    }

    private void onCurrentActionChanged(Article newAction, Article oldAction) {
	if (newAction != null) {
	    // setze DoneReciever auf JETZT + wish.duration
	    AlarmManager alarmMgr = (AlarmManager) this.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
	    Intent i = new Intent(this.getApplicationContext(), DoneReciever.class);

	    Gson gson = new Gson();
	    String json = gson.toJson(newAction);
	    i.putExtra("WISH_JSON", json);

	    PendingIntent alarmIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, i, 0);
	    alarmMgr.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + newAction.getDuration().getTime(), alarmIntent);

	    //this.getAlarms().put(i, alarmIntent);
	} else {
	    performingRequest = false;

	    Toast t = Toast.makeText(
		    app.getApplicationContext(),
		    app.getApplicationContext().getString(R.string.msg_request_notpossible),
		    Toast.LENGTH_LONG
		    );
	    t.show();
	}

	if (newAction instanceof Food) {
	    if(((Food) newAction).isHealty()) {
		ImageHelper.setGrandmaTypeEatLight(null);
	    } else {
		ImageHelper.setGrandmaTypeEatHeavy(null);
	    }
	}
	else if (newAction instanceof Dishes) {
	    ImageHelper.setGrandmaTypeCleanDishes(null);
	}
	else if (newAction instanceof Drink) {
	    ImageHelper.setGrandmaTypeDrink(null);
	}
	else if (newAction instanceof Medicine) {
	    ImageHelper.setGrandmaTypeMedicine(null);
	}
	else if (newAction instanceof Clothing) {
	    ImageHelper.setGrandmaTypeWashing(null);
	}
	else if (newAction instanceof House) {
	    ImageHelper.setGrandmaTypeCleanCar(null);
	}
	else if (newAction instanceof Bed) {
	    ImageHelper.setGrandmaTypeSleep(null);
	}
	else {
	    ImageHelper.setGrandmaTypeInCar(null);
	}
    }

    public void onWishListChanged(List<Article> newList, List<Article> oldList) {
	listItems.clear();
	for(Article wish: newList) {
	    listItems.add(wish.getName());
	    adapter.notifyDataSetChanged();
	}
    }


}
