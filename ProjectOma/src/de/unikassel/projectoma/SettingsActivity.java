package de.unikassel.projectoma;

import de.unikassel.projectoma.model.LevelType;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

public class SettingsActivity extends PreferenceActivity {

	GrandmaApplication app;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		app = ((GrandmaApplication) getApplication());

		addPreferencesFromResource(R.layout.settings);

		EditTextPreference txtName = (EditTextPreference) findPreference("grandmas_name");

		//Name preference
		txtName.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
		{

			@Override
			public boolean onPreferenceClick(Preference preference)
			{
				EditTextPreference p = (EditTextPreference)preference;
				p.setText(app.getGrandma().getName());
				Log.i("ProjectOma", "Get grandmas name: " + app.getGrandma().getName());

				return true;
			}
		});

		txtName.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue)
			{
				app.getGrandma().setName((String)newValue);
				Log.i("ProjectOma", "Changed grandmas name to " + (String)newValue);

				return true;
			}
		});

		//Difficulty preference
		ListPreference difficulty = (ListPreference) findPreference("difficulty_level");

		difficulty.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
		{

			@Override
			public boolean onPreferenceClick(Preference preference)
			{
				ListPreference p = (ListPreference)preference;

				p.setValue(String.valueOf(app.getGrandma().getLevel().getValue()));

				Log.i("ProjectOma", "Get grandmas difficulty level: " + app.getGrandma().getLevel());

				return true;
			}
		});

		difficulty.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
		{
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue)
			{
				app.getGrandma().setLevel(LevelType.values()[Integer.valueOf((String) newValue)]);

				Log.i("ProjectOma", "Set grandmas difficulty level to " + LevelType.values()[Integer.valueOf((String) newValue)]);

				return true;
			}
		});

		//Reset button
		Preference btnReset = (Preference) findPreference("reset_game");

		btnReset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
		{
			@Override
			public boolean onPreferenceClick(Preference arg0)
			{
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
				app.resetGame();
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

	@Override
	protected void onPause() {
		super.onPause();
		app.grandma.save(PreferenceManager
				.getDefaultSharedPreferences(this
						.getApplicationContext()));
	}
}