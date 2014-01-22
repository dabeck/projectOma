package de.unikassel.projectoma;

import java.util.ArrayList;

import com.google.gson.Gson;

import android.app.AlertDialog;
import android.app.ListActivity;
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
		listItems.add("RequestClicked : " + clickCounter++);
		adapter.notifyDataSetChanged();
	}
	
	public void btnStockClicked(View v) {
		listItems.add("StockClicked : " + clickCounter++);
		adapter.notifyDataSetChanged();
	}
	
	public void btnTestClicked(View v) {
		listItems.add("TestClicked : " + clickCounter++);
		adapter.notifyDataSetChanged();
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
		
		
		
		/* Erster Start?!? */
		/* TODO */
		
		
		
		/* Spielende?!? */
		// pruefe alle Deadlines, jeweils fuer Wuensche und Einkaufsliste
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
					"GAME OVER: Der Wunsch '" + wish.getName() + "' wurde nicht rechtzeitig erfüllt!",
					Toast.LENGTH_LONG
				);
			t.show();
			
			// loesche Oma
			Editor edit = PreferenceManager
			        .getDefaultSharedPreferences(this
					        .getApplicationContext()).edit();
			edit.putString("de.unikassel.projectoma.grandma", null);
			edit.commit();
			
			/* TODO: App-Restart */
		}
	}
}
