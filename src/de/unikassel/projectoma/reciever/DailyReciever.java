package de.unikassel.projectoma.reciever;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;

import com.google.gson.Gson;

import de.unikassel.projectoma.model.Article;
import de.unikassel.projectoma.model.Bed;
import de.unikassel.projectoma.model.Clothing;
import de.unikassel.projectoma.model.Daytime;
import de.unikassel.projectoma.model.Dishes;
import de.unikassel.projectoma.model.Drink;
import de.unikassel.projectoma.model.Food;
import de.unikassel.projectoma.model.Grandma;
import de.unikassel.projectoma.model.House;
import de.unikassel.projectoma.model.Medicine;
import de.unikassel.projectoma.model.Washer;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

public class DailyReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	    	// Lade Oma.
	    	Grandma grandma = Grandma.load(PreferenceManager
	    		.getDefaultSharedPreferences(context.getApplicationContext()));
	    	
	    	
	    	
		Article wish;
		Article followingWish = (Article)(new Dishes());
		Food meal;
		
		Random rand = new Random();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		double currentHour = calendar.get(Calendar.HOUR_OF_DAY) + (1.0 / 60.0 * calendar.get(Calendar.MINUTE));
		
		
		
		/* ESSEN: 3x + jeweils 'Spuelen'-Wunsch */
		//     morgens (08-10)
		wish = (Article)Food.randomFood(Daytime.MORNING)
				.withStart(setRandomCalendar(calendar, currentHour, 8, 2));
		setAlarm(grandma, context, wish);
		followingWish.setStart(getFollowingStart(wish));
		setAlarm(grandma, context, followingWish);
		
		//     mittags (12-14)
		meal = (Food) Food.randomFood(Daytime.MIDDAY)
				.withStart(setRandomCalendar(calendar, currentHour, 12, 2));
		wish = (Article)meal;
		setAlarm(grandma, context, wish);
		followingWish.setStart(getFollowingStart(wish));
		setAlarm(grandma, context, followingWish);
		
		//     abends  (18-20)
		wish = (Article)Food.randomFood(Daytime.EVENING)
				.withStart(setRandomCalendar(calendar, currentHour, 18, 2));
		setAlarm(grandma, context, wish);
		followingWish.setStart(getFollowingStart(wish));
		setAlarm(grandma, context, followingWish);
		
		
		
		/* TRINKEN: mind. 3x */
		for (int i = 0; i != 3; i++) {
		    wish = (Article)(new Drink()
		    	.withHot(false)
		    	.withName("Wasser")
		    	.withDuration(new Timestamp(0, 0, 0, 0,
		    		rand.nextInt(2),
				rand.nextInt(60), 0))
			.withStart(setRandomCalendar(calendar, currentHour, 8, 12))
		    );
		    setAlarm(grandma, context, wish);
		}
		
		
		
		/* MEDIs: mind. morgens und abends + evtl. mittags (bei ESSEN.isHeavy) */
		// morgens
		wish = (Article)(new Medicine()
			.withTyp(Daytime.MORNING)
			.withName("Morgenmedizin")
			.withDuration(new Timestamp(0, 0, 0, 0,
					rand.nextInt(4),
					rand.nextInt(60), 0))
			.withStart(setRandomCalendar(calendar, currentHour, 8, 3))
		);
		setAlarm(grandma, context, wish);
		
		// + abends
		wish = (Article)(new Medicine()
			.withTyp(Daytime.MORNING)
			.withName("Abendmedizin")
			.withDuration(new Timestamp(0, 0, 0, 0,
					rand.nextInt(4),
					rand.nextInt(60), 0))
			.withStart(setRandomCalendar(calendar, currentHour, 17, 3))
		);
		setAlarm(grandma, context, wish);
	
		// + evtl. Mittags
		if (meal.isHeavy()) {
			wish = (Article)(new Medicine()
				.withTyp(Daytime.MORNING)
				.withName("Mittagsmedizin")
				.withDuration(new Timestamp(0, 0, 0, 0,
						rand.nextInt(4),
						rand.nextInt(60), 0))
				.withStart(setRandomCalendar(calendar, currentHour, 12, 3))
			);
			setAlarm(grandma, context, wish);
		}
		
		
		
		/* SCHLAFEN: abends, bis morgens */
		wish = (Article)(new Bed()
        		.withName("Bett")
        		.withDuration(new Timestamp(0, 0, 0,
        				rand.nextInt(9),
        				rand.nextInt(60),
        				rand.nextInt(60), 0))
        		.withStart(setRandomCalendar(calendar, currentHour, 22, 1))
        	);
        	setAlarm(grandma, context, wish);
		
		
		
		/* EINKLEIDEN: 1x morgens */
        	wish = (Article)(new Clothing()
        		.withName("Kleidung")
        		.withDuration(new Timestamp(0, 0, 0, 0,
        				rand.nextInt(9),
        				rand.nextInt(60), 0))
        		.withStart(setRandomCalendar(calendar, currentHour, 7, 1))
        	);
        	setAlarm(grandma, context, wish);
        	
        	
        	
		/* WASCHEN: wenn keine saubere Kleidung */
        	if (grandma.getWarderobeCount() <= 1) {
        	    wish = (Article)(new Washer()
                		.withName("Waschmaschine")
                		.withDuration(new Timestamp(0, 0, 0, 0,
                				1,
                				rand.nextInt(60), 0))
                		.withStart(setRandomCalendar(calendar, currentHour, 10, 7))
        		    );
        	    setAlarm(grandma, context, wish);
        	}
		
		
		
		/* PUTZEN: (1/7)x ... also woechentlich */
        	if (rand.nextInt(7) == 0) {
        	    wish = (Article)(new House()
                		.withName("Hausputz")
                		.withDuration(new Timestamp(0, 0, 0, 0,
                				rand.nextInt(1),
                				rand.nextInt(60), 0))
                		.withStart(setRandomCalendar(calendar, currentHour, 10, 7))
        		    );
        	    setAlarm(grandma, context, wish);
        	}
		
		
		
		/* ZUSATZ: abhaengig von Level */
		// pflegeleicht     +3 Wuensche
		// normal           +6 Wuensche
		// nervig           +9 Wuensche
		int extraCount = 3 * grandma.getLevel().getValue();
		for (int i = 0; i != extraCount; i++) {
			wish = generateRandomWish()
					.withStart(setRandomCalendar(calendar, currentHour, 8, 12));
			setAlarm(grandma, context, wish);
		}
		
		
		
		// Speichere Oma wieder.
		grandma.save(PreferenceManager
			.getDefaultSharedPreferences(context.getApplicationContext()));

	}
	
	private void setAlarm(Grandma grandma, Context context, Article wish) {
		AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, WishReciever.class);
		
		Gson gson = new Gson();
		String json = gson.toJson(wish);
		i.putExtra("WISH_JSON", json);
		
		PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, i, 0);
		alarmMgr.set(AlarmManager.RTC_WAKEUP, wish.getStart().getTimeInMillis(), alarmIntent);
		
		grandma.getAlarms().put(i, alarmIntent);
	}
	
	private Calendar setRandomCalendar(Calendar calendar, double currentHour, int hour, int rangeHour) {
		Random rand = new Random();
		
		calendar.set(Calendar.HOUR_OF_DAY, hour + rand.nextInt(rangeHour));
		calendar.set(Calendar.MINUTE, rand.nextInt(60));
		
		double wishHour = calendar.get(Calendar.HOUR_OF_DAY) + (1.0 / 60.0 * calendar.get(Calendar.MINUTE));
		
		// wenn Zeitpunkt in Vergangenheit, dann +24h
		if(currentHour >= wishHour) {
			calendar.add(Calendar.DATE, 1);
		}
		
		return calendar;
	}
	
	/* Addiere die Wunsch-Dauer auf den gegebenen Kalender. */
	private Calendar getFollowingStart(Article wish) {
		Calendar calendar = wish.getStart();
		
		int hour = calendar.get(Calendar.HOUR_OF_DAY) + wish.getDuration().getHours();
		int min = calendar.get(Calendar.MINUTE) + wish.getDuration().getMinutes();
		
		// korrigiere wenn mehr als 60 Minuten
		hour += (min >= 60) ? 1 : 0;
		min -= (min >= 60) ? 60 : 0;
		
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, min);

		return calendar;
	}
	
	private Article generateRandomWish() {
	    Random rand = new Random();
		
	    switch(rand.nextInt(4)) {
			case 0:
			    // medis
			    return (Article)(new Medicine()
			    	.withTyp(Daytime.values()[rand.nextInt(3)])
			    	.withName("Medizin")
			    	.withDuration(new Timestamp(0, 0, 0, 0,
			    		rand.nextInt(4),
					rand.nextInt(60), 0))
			    );
			case 1:
			    // einkleiden
			    return (Article)(new Clothing()
			    	.withName("Kleidung")
			    	.withDuration(new Timestamp(0, 0, 0, 0,
			    		rand.nextInt(9),
					rand.nextInt(60), 0))
			    );
			default:
			    // trinken
			    return (Article)(new Drink()
			    	.withHot(false)
			    	.withName("Wasser")
			    	.withDuration(new Timestamp(0, 0, 0, 0,
			    		rand.nextInt(2),
					rand.nextInt(60), 0))
			    );
	    }
	}

}
