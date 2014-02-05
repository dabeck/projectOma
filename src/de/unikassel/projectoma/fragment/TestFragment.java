package de.unikassel.projectoma.fragment;

import de.unikassel.projectoma.MainActivity;
import de.unikassel.projectoma.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class TestFragment extends DialogFragment {

    String[] itemsToDisplay;

    public static TestFragment newInstance() {

	TestFragment f = new TestFragment();
	Bundle args = new Bundle();
	f.setArguments(args);

	return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

	builder.setTitle("Testmethode wählen:");

	builder.setItems(R.array.testModes, null);

	builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
	    public void onClick(DialogInterface dialog, int id) {
		MainActivity container = (MainActivity) getActivity();
		container.processTest(id);						
		dismiss();
	    }
	});

	return builder.create();
    }
}
