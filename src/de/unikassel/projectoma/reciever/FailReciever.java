package de.unikassel.projectoma.reciever;

import com.google.gson.Gson;

import de.unikassel.projectoma.MainActivity;
import de.unikassel.projectoma.R;
import de.unikassel.projectoma.model.Article;
import de.unikassel.projectoma.model.Bed;
import de.unikassel.projectoma.model.Dishes;
import de.unikassel.projectoma.model.Food;
import de.unikassel.projectoma.model.Grandma;
import de.unikassel.projectoma.model.House;
import de.unikassel.projectoma.model.Medicine;
import de.unikassel.projectoma.model.RequestType;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class FailReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
    	// Lade Oma.
 	Grandma grandma = Grandma.load(PreferenceManager
 		.getDefaultSharedPreferences(context.getApplicationContext()));
	
 	grandma.update();
 	
	// Hole Wunsch vom Intent.
	Gson gson = new Gson();
	String json = intent.getStringExtra("WISH_JSON");
	Article article = gson.fromJson(json, Article.class);
	Article wish = article;
	
	if(article.getArticleType() == RequestType.medicine)
	{
	    wish = gson.fromJson(json, Medicine.class);
	}
	
	if(article.getArticleType() == RequestType.eat)
	{
	    wish = gson.fromJson(json, Food.class);
	}
	
	if(article.getArticleType() == RequestType.sleep)
	{
	    wish = gson.fromJson(json, Bed.class);
	}
	
	if(article.getArticleType() == RequestType.clean_car)
	{
	    wish = gson.fromJson(json, House.class);
	}
	
	if(article.getArticleType() == RequestType.wash_dishes)
	{
	    wish = gson.fromJson(json, Dishes.class);
	}
	
	// suche entsprechenden Wunsch
	// wenn nicht mehr in wishList, scheinbar schon erfuellt
	for (Article wishItem: grandma.getWishList()) {
	    if (wishItem == wish) {
        	// Notifiziere den User ueber den erzeugten Wunsch.
        	notify(context, wish);
        	
        	//reset
        	resetGame(context);
	    }
	}
    }

	private void notify(Context context, Article wish) {
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(context)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle(context.getString(R.string.gameover))
		        .setContentText(context.getString(R.string.gameover_desc));
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(context, MainActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MainActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(0, mBuilder.build());
	}
	
    	    public void resetGame(Context context) {
    		AlarmManager mgr = (AlarmManager)context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
    		Editor edit = PreferenceManager.getDefaultSharedPreferences(
    		context.getApplicationContext()).edit();
    		
    		// Setze Oma null
    		edit.putString("de.unikassel.projectoma.grandma", null);
    		edit.commit();
    		
    		// App-Restart
    		Intent mStartActivity = new Intent(context.getApplicationContext(), MainActivity.class);
    		int mPendingIntentId = 123456;
    		PendingIntent mPendingIntent = PendingIntent.getActivity(context.getApplicationContext(),
    			mPendingIntentId,
    			mStartActivity,
    			PendingIntent.FLAG_CANCEL_CURRENT);
    		mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
    		System.exit(0);
    	    }

}
