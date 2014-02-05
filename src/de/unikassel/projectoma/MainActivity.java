package de.unikassel.projectoma;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Timestamp;
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
import de.unikassel.projectoma.model.Washer;
import de.unikassel.projectoma.reciever.DailyReciever;
import de.unikassel.projectoma.reciever.DoneReciever;
import de.unikassel.projectoma.R;
import de.unikassel.projectoma.fragment.FeedFragment;
import de.unikassel.projectoma.fragment.MedicineFragment;
import de.unikassel.projectoma.fragment.RequestFragment;
import de.unikassel.projectoma.fragment.ShoppingFragment;
import de.unikassel.projectoma.fragment.StockFragment;
import de.unikassel.projectoma.fragment.TestFragment;
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

	ImageHelper.setGrandmaActionInCar(null);
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

	ImageHelper.setGrandmaActionInCar(null);
    }

    public void btnTestClicked(View v) {

	TestFragment newFragment = TestFragment.newInstance();
	newFragment.show(getFragmentManager(), "testDialog");

	ImageHelper.setGrandmaActionInCar(null);


	//	Calendar calendar = Calendar.getInstance();
	//	/* DEMO-WUNSCH: Mittags-Medis */
	//	// 10 Sekunden nach JETZT
	//	calendar.setTimeInMillis(System.currentTimeMillis()+10000);
	//	Article wish = (Article)new Medicine().withTyp(Daytime.MIDDAY)
	//			.withStart(calendar).withDuration(new Timestamp(20000)).withName("TestWünsch");
	//	DailyReciever.setAlarm(app.grandma, this, wish);

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
	    if (performingRequest) {
		ImageHelper.setGrandmaActionDrink(null);
	    }
	    break;
	case wash_dishes:
	    performingRequest = app.getGrandma().washDishes();
	    if (performingRequest) {
		ImageHelper.setGrandmaRequestCleanDishes(null);
	    }
	    break;
	case wash_clothes:
	    performingRequest = app.getGrandma().washClothes();
	    if (performingRequest) {
		ImageHelper.setGrandmaActionWashing(null);
	    }
	    break;
	case sleep:
	    performingRequest = app.getGrandma().sleep();
	    if (performingRequest) {
		ImageHelper.setGrandmaActionSleep(null);
	    }
	    break;
	case clean_car:
	    performingRequest = app.getGrandma().clean();
	    if (performingRequest) {
		ImageHelper.setGrandmaActionCleanCar(null);
	    }
	    break;
	case clothe:
	    performingRequest = app.getGrandma().clothe();
	    if (performingRequest) {
		ImageHelper.setGrandmaRequestDress(null);
	    }
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
	    ImageHelper.setGrandmaActionMusic(null);

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
	if (performingRequest) {
	    if (food.isHeavy()) {
		ImageHelper.setGrandmaActionEatHeavy(null);
	    } else {
		ImageHelper.setGrandmaActionEatLight(null);
	    }
	}
    }

    /**
     * Process a medicine request
     * 
     * @param med Type of medicine to administer
     */
    public void processMedicine(Daytime med) {
	performingRequest = app.getGrandma().cure(new Medicine().withTyp(med));
	if (performingRequest) {
	    ImageHelper.setGrandmaActionMedicine(null);
	}
    }

    /**
     * Process a shopping request
     * 
     * @param selectedItems A list of selected food to buy
     */
    public void processShopping(ArrayList<Food> selectedItems) {
	app.getGrandma().buy(selectedItems);

	adapter.notifyDataSetChanged();

	ImageHelper.setGrandmaActionShopping(null);
	// GameOver-Toast
	Toast t = Toast.makeText(
		app.getApplicationContext(),
		app.getApplicationContext().getString(R.string.msg_shopping_positive),
		Toast.LENGTH_LONG
		);
	t.show();
    }

    /**
     * Testprocessing
     * 
     * @param mode Testmode 1 = simulation; 2 = wishes
     */
    public void processTest(int mode) {

    }
    //<!-- Process actions end-->


    @Override
    protected void onPause() {
	super.onPause();

	//Unregister listener for shake
	mSensorManager.unregisterListener(mSensorListener);

	//Unregister listener for shake
	app.getGrandma().removePropertyChangeListener(this);

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
	if (app.getGrandma() == null) {
	    firstStart();
	}

	// Register listener for grandma-Object
	app.grandma.update(this);
	app.grandma.addPropertyChangeListener(this);


	for (Article wish : app.grandma.getWishList()) {
	    listItems.add(wish.getName());
	    adapter.notifyDataSetChanged();
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
		ImageHelper.setGrandmaActionEatLight(null);
	    } else {
		ImageHelper.setGrandmaActionEatHeavy(null);
	    }
	}
	else if (newAction instanceof Dishes) {
	    ImageHelper.setGrandmaRequestCleanDishes(null);
	}
	else if (newAction instanceof Drink) {
	    ImageHelper.setGrandmaActionDrink(null);
	}
	else if (newAction instanceof Medicine) {
	    ImageHelper.setGrandmaActionMedicine(null);
	}
	else if (newAction instanceof Washer) {
	    ImageHelper.setGrandmaActionWashing(null);
	}
	else if (newAction instanceof Clothing) {
	    //Anziehen bild fehlt
	}
	else if (newAction instanceof House) {
	    ImageHelper.setGrandmaActionCleanCar(null);
	}
	else if (newAction instanceof Bed) {
	    ImageHelper.setGrandmaActionSleep(null);
	}
	else {
	    ImageHelper.setGrandmaActionInCar(null);
	}
    }

    public void onWishListChanged(List<Article> newList, List<Article> oldList) {
	listItems.clear();

	if(newList.size() > oldList.size())
	{
	    Article a = app.getGrandma().getWishList().get(app.getGrandma().getWishList().size() -1);

	    if (a.getArticleType() == RequestType.eat) {
		ImageHelper.setGrandmaRequestFood(null);
	    }

	    if (a.getArticleType() == RequestType.drink) {
		ImageHelper.setGrandmaRequestDrink(null);
	    }

	    if (a.getArticleType() == RequestType.wash_dishes) {
		ImageHelper.setGrandmaRequestCleanDishes(null);
	    }

	    if (a.getArticleType() == RequestType.clothe) {
		ImageHelper.setGrandmaRequestDress(null);
	    }

	    if (a.getArticleType() == RequestType.clean_car) {
		ImageHelper.setGrandmaRequestCleanCar(null);
	    }

	    if (a.getArticleType() == RequestType.sleep) {
		ImageHelper.setGrandmaRequestSleep(null);
	    }

	    if (a.getArticleType() == RequestType.medicine) {
		ImageHelper.setGrandmaRequestMedicine(null);
	    }

	    if (a.getArticleType() == RequestType.wash_clothes) {
		ImageHelper.setGrandmaRequestHelp(null);
	    }
	} 
	else 
	{
	    ImageHelper.setGrandmaActionInCar(null);
	}

	for(Article wish: newList) {
	    listItems.add(wish.getName());
	    adapter.notifyDataSetChanged();
	}
    }


}
