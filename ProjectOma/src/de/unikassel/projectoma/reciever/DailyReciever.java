package de.unikassel.projectoma.reciever;

import java.util.Calendar;
import java.util.Random;

import com.google.gson.Gson;

import de.unikassel.projectoma.model.Article;
import de.unikassel.projectoma.model.Daytime;
import de.unikassel.projectoma.model.Dishes;
import de.unikassel.projectoma.model.Food;
import de.unikassel.projectoma.model.FoodType;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DailyReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Article wish;
		Article followingWish = (Article)(new Dishes());
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		double currentHour = calendar.get(Calendar.HOUR_OF_DAY) + (1.0 / 60.0 * calendar.get(calendar.MINUTE));

		
		/* Generiere 3 Essenswuensche + jeweils Spuelen: */
		/*     morgens (08-10) */
		wish = (Article)Food.randomFood(Daytime.MORNING);
		calendar = setRandomCalendar(calendar, currentHour, 8, 2);
		setAlarm(context, calendar, wish);
		setCalendar(calendar, wish);
		setAlarm(context, calendar, followingWish);
		
		/*     mittags (12-14) */
		wish = (Article)Food.randomFood(Daytime.MIDDAY);
		calendar = setRandomCalendar(calendar, currentHour, 12, 2);
		setAlarm(context, calendar, wish);
		setCalendar(calendar, wish);
		setAlarm(context, calendar, followingWish);
		
		/*     abends  (18-20) */
		wish = (Article)Food.randomFood(Daytime.EVENING);
		calendar = setRandomCalendar(calendar, currentHour, 18, 2);
		setAlarm(context, calendar, wish);
		setCalendar(calendar, wish);
		setAlarm(context, calendar, followingWish);
	}
	
	private void setAlarm(Context context, Calendar calendar, Article wish) {
		AlarmManager alarmMgr = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
		Intent i = new Intent(context, WishReciever.class);
		
		Gson gson = new Gson();
		String json = gson.toJson(wish);
		i.putExtra("WISH_JSON", json);
		
		PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, i, 0);
		alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
	}
	
	private Calendar setRandomCalendar(Calendar calendar, double currentHour, int hour, int rangeHour) {
		Random rand = new Random();
		
		calendar.set(Calendar.HOUR_OF_DAY, hour + rand.nextInt(rangeHour));
		calendar.set(Calendar.MINUTE, rand.nextInt(60));
		
		double wishHour = calendar.get(Calendar.HOUR_OF_DAY) + (1.0 / 60.0 * calendar.get(calendar.MINUTE));
		
		// wenn Zeitpunkt in Vergangenheit, dann +24h
		if(currentHour >= wishHour) {
			calendar.add(Calendar.DATE, 1);
		}
		
		return calendar;
	}
	
	/* Addiere die Wunsch-Dauer auf den gegebenen Kalender. */
	private Calendar setCalendar(Calendar calendar, Article wish) {
		int hour = calendar.get(Calendar.HOUR_OF_DAY) + wish.getDuration().getHours();
		int min = calendar.get(Calendar.MINUTE) + wish.getDuration().getMinutes();
		
		// korrigiere wenn mehr als 60 Minuten
		hour += (min >= 60) ? 1 : 0;
		min -= (min >= 60) ? 60 : 0;
		
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, min);
		
		return calendar;
	}

}
