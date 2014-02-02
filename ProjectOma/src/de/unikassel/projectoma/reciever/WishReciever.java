package de.unikassel.projectoma.reciever;

import com.google.gson.Gson;

import de.unikassel.projectoma.model.Article;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WishReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		/* Hole Wunsch vom Intent. */
		Gson gson = new Gson();
		String json = intent.getStringExtra("WISH_JSON");
		Article wish = gson.fromJson(json, Article.class);
		
		
	}

}
