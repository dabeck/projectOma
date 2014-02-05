package de.unikassel.projectoma.reciever;
import com.google.gson.Gson;

import de.unikassel.projectoma.GrandmaApplication;
import de.unikassel.projectoma.MainActivity;
import de.unikassel.projectoma.model.Article;
import de.unikassel.projectoma.model.Bed;
import de.unikassel.projectoma.model.Clothing;
import de.unikassel.projectoma.model.Dishes;
import de.unikassel.projectoma.model.Drink;
import de.unikassel.projectoma.model.Food;
import de.unikassel.projectoma.model.Grandma;
import de.unikassel.projectoma.model.House;
import de.unikassel.projectoma.model.Medicine;
import de.unikassel.projectoma.R;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class WishReciever extends BroadcastReceiver {

	GrandmaApplication app;
	
	@Override
	public void onReceive(Context context, Intent intent) {
	    	// Lade Oma.
	 	Grandma grandma = Grandma.load(PreferenceManager
	 		.getDefaultSharedPreferences(context.getApplicationContext()));
		
	 	grandma.update();
	 	
	 	// Entferne Alarm-Intent.
		//grandma.getAlarms().remove(intent);
	 	
		// Hole Wunsch vom Intent.
		Gson gson = new Gson();
		String json = intent.getStringExtra("WISH_JSON");
		Article wish = gson.fromJson(json, Article.class);
		
		// Haenge Wunsch an Liste an.
		grandma.addWish(wish);
		
		// Notifiziere den User ueber den erzeugten Wunsch.
		notify(context, wish);
		
		// Speichere Oma wieder.
		grandma.save(PreferenceManager
			.getDefaultSharedPreferences(context.getApplicationContext()));
	}

	private void notify(Context context, Article wish) {
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(context)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle(context.getString(R.string.notify_title))
		        .setContentText(context.getString(wishToNotifyText(wish)));
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
	
	private int wishToNotifyText(Article wish) {
	    	if (wish instanceof Food) {
	    	    return R.string.notify_desc_food;
		}
		else if (wish instanceof Dishes) {
		    return R.string.notify_desc_dishes;
		}
		else if (wish instanceof Drink) {
		    return R.string.notify_desc_drink;
		}
		else if (wish instanceof Medicine) {
		    return R.string.notify_desc_medi;
		}
		else if (wish instanceof Clothing) {
		    return R.string.notify_desc_cloth;
		}
		else if (wish instanceof House) {
		    return R.string.notify_desc_house;
		}
		else if (wish instanceof Bed) {
		    return R.string.notify_desc_sleep;
		}
		else {
		    return R.string.notify_title;
		}
	}
}
