package de.unikassel.projectoma;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import de.unikassel.projectoma.model.FoodType;
import de.unikassel.projectoma.model.Grandma;
import de.unikassel.projectoma.model.House;
import de.unikassel.projectoma.model.LevelType;
import de.unikassel.projectoma.model.Medicine;
import de.unikassel.projectoma.model.RequestType;
import de.unikassel.projectoma.reciever.DailyReciever;
import de.unikassel.projectoma.R;
import de.unikassel.projectoma.fragment.FeedFragment;
import de.unikassel.projectoma.fragment.MedicineFragment;
import de.unikassel.projectoma.fragment.RequestFragment;
import de.unikassel.projectoma.fragment.ShoppingFragment;
import de.unikassel.projectoma.fragment.StockFragment;
import de.unikassel.projectoma.helper.ImageHelper;

public class MainActivity extends ListActivity implements PropertyChangeListener {

    // LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems = new ArrayList<String>();

    // DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE
    // LISTVIEW
    ArrayAdapter<String> adapter;

    // RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
    int clickCounter = 0;

    GrandmaApplication app;
    Boolean performingRequest = false;

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
		Log.i("ProjectOma", "Phone shaked!!");
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
	listItems.add("Test : " + clickCounter++);
	adapter.notifyDataSetChanged();

	Article wish = (Article)Food.randomFood(Daytime.EVENING);

	Calendar cal = Calendar.getInstance();
	cal.setTimeInMillis(System.currentTimeMillis() + 5000);
	
	wish.setStart(cal);
	app.grandma.addWish(wish);
	//		
	//		Calendar calendar = Calendar.getInstance();
	//		calendar.setTimeInMillis(System.currentTimeMillis() + 5000);

	//		DailyReciever.setAlarm(app.getApplicationContext(), calendar, wish);

	listItems.add(wish.getStart().toString() + wish.getName());
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
	else
	{
	    performingRequest = true;
	}

	switch(selection) {
	case cook:
	    ImageHelper.setGrandmaTypeCooking(new ImageHelper.Callback() {

		@Override
		public void onFinished() {
		    performingRequest = false;
		}
	    });
	    //Eating depends on cooking
	    break;
	case eat:
	    FeedFragment newFragment = FeedFragment.newInstance();
	    newFragment.show(getFragmentManager(), "foodDialog");

	    break;
	case drink:
	    ImageHelper.setGrandmaTypeDrink(new ImageHelper.Callback() {

		@Override
		public void onFinished() {
		    performingRequest = false;

		}
	    });
	    //Generates dirty dishes + reduces water stock
	    break;
	case wash_dishes:
	    ImageHelper.setGrandmaTypeDoCleanDishes(new ImageHelper.Callback() {

		@Override
		public void onFinished() {
		    performingRequest = false;

		}
	    });
	    //Generates clean dishes
	    break;
	case wash_clothes:
	    ImageHelper.setGrandmaTypeWashing(new ImageHelper.Callback() {

		@Override
		public void onFinished() {
		    performingRequest = false;

		}
	    });
	    //Fill up clothes stock
	    break;
	case sleep:
	    ImageHelper.setGrandmaTypeSleep(new ImageHelper.Callback() {

		@Override
		public void onFinished() {
		    performingRequest = false;

		}
	    });
	    //Resets stamina
	    break;
	case medicine:
	    MedicineFragment mediFragment = MedicineFragment.newInstance();
	    mediFragment.show(getFragmentManager(), "administerDialog");
	    //Resets stamina
	    break;
	case walk:
	    ImageHelper.setGrandmaTypeWalk(new ImageHelper.Callback() {

		@Override
		public void onFinished() {
		    performingRequest = false;

		}
	    });
	    //Reduces stamina
	    break;
	case clean_car:
	    ImageHelper.setGrandmaTypeCleanCar(new ImageHelper.Callback() {

		@Override
		public void onFinished() {
		    performingRequest = false;

		}
	    });
	    //. ...
	    break;
	case music:
	    ImageHelper.setGrandmaTypeMusic(new ImageHelper.Callback() {

		@Override
		public void onFinished() {
		    performingRequest = false;

		}
	    });
	    
	    //TODO: open youtube song!
	    //Resets stamina
	    break;
	case shopping:
	    ShoppingFragment shop = ShoppingFragment.newInstance();
	    shop.show(getFragmentManager(), "shopDialog");
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
	//TODO: satisfy foodWish with type  

	if(food.isHeavy()) {
	    ImageHelper.setGrandmaTypeEatHeavy(null);
	}
	else {
	    ImageHelper.setGrandmaTypeEatLight(null);
	}
	
	performingRequest = false;
    }

    /**
     * Process a medicine request
     * 
     * @param med Type of medicine to administer
     */
    public void processMedicine(Daytime med) {
	switch (med) {
	case MORNING:
	    //TODO: Administer medicine
	    ImageHelper.setGrandmaTypeMedicine(null);
	    break;
	case MIDDAY:
	    //TODO: Administer medicine
	    ImageHelper.setGrandmaTypeMedicine(null);
	    break;
	case EVENING:
	    //TODO: Administer medicine
	    ImageHelper.setGrandmaTypeMedicine(null);
	    break;
	default:
	    break;
	}

	performingRequest = false;
    }

    /**
     * Process a shopping request
     * 
     * @param selectedItems A list of selected food to buy
     */
    public void processShopping(ArrayList<FoodType> selectedItems) {
	for (FoodType foodType : selectedItems) {
	     listItems.add(foodType.toString());
	}
	
	adapter.notifyDataSetChanged();
	
	
	//TODO: fill up stocks with selected items
	ImageHelper.setGrandmaTypeGoShopping(null);
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
		    	.getString(R.string.default_name), LevelType.SIMPLE)
	    		.withContext(this.getApplicationContext());

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
	    app.grandma.getAlarms().put(intent, alarmIntent);
	    
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
	if (newAction instanceof Food) {
	    processRequest(RequestType.eat);
	}
	else if (newAction instanceof Dishes) {
	    processRequest(RequestType.wash_dishes);
	}
	else if (newAction instanceof Drink) {
	    processRequest(RequestType.drink);
	}
	else if (newAction instanceof Medicine) {
	    processRequest(RequestType.medicine);
	}
	else if (newAction instanceof Clothing) {
	    processRequest(RequestType.shopping);
	}
	else if (newAction instanceof House) {
	    processRequest(RequestType.clean_car);
	}
	else if (newAction instanceof Bed) {
	    processRequest(RequestType.sleep);
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
