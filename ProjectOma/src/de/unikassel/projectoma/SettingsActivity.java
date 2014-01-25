package de.unikassel.projectoma;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.View;

public class SettingsActivity extends PreferenceActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.layout.settings);
		
		Preference btnReset = (Preference) findPreference("reset_game");
		btnReset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			
			@Override
			public boolean onPreferenceClick(Preference arg0) {
				btnResetClicked();
				return true;
			}
		});

	}
	
	public void btnResetClicked() {
		new AlertDialog.Builder(this)
		        .setTitle(R.string.reset_warning)
		        .setMessage(R.string.reset_warning_msg)
		        .setCancelable(false)
		        .setPositiveButton(R.string.yes,
		                new DialogInterface.OnClickListener() {
			                
			                public void onClick(DialogInterface dialog, int id) {
				                // if this button is clicked, reset the
								// game
			                }
		                })
		        .setNegativeButton(R.string.no,
		                new DialogInterface.OnClickListener() {
			        
			        public void onClick(DialogInterface dialog, int id) {
				        // if this button is clicked, just close
				        // the dialog box and do nothing
				        dialog.cancel();
			        }
		                }).setIcon(android.R.drawable.ic_dialog_alert).show();
	}
	
	public void togglePause(View v) {

	}
}