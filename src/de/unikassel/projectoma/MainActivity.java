package de.unikassel.projectoma;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {
	
	// LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
	ArrayList<String> listItems = new ArrayList<String>();
	
	// DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE
	// LISTVIEW
	ArrayAdapter<String> adapter;
	
	// RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
	int clickCounter = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
	
	public void myClickMethod(View v) {
		listItems.add("Clicked : " + clickCounter++);
		adapter.notifyDataSetChanged();
	}
}
