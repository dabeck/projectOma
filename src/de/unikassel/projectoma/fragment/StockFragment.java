package de.unikassel.projectoma.fragment;

import de.unikassel.projectoma.GrandmaApplication;
import de.unikassel.projectoma.R;
import de.unikassel.projectoma.model.Food;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class StockFragment extends DialogFragment {

    String[] itemsToDisplay;
    
    public static StockFragment newInstance() {

	StockFragment f = new StockFragment();
	Bundle args = new Bundle();
	f.setArguments(args);

	return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
	GrandmaApplication app = (GrandmaApplication) getActivity().getApplication();
		
	int items = app.getGrandma().getPantry().size();
	itemsToDisplay = new String[items];
	int i = 0;
	
	for(Food f : app.getGrandma().getPantry()) {
	    itemsToDisplay[i] = f.getName();
	    i++;
	}
	
	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

	builder.setTitle(R.string.inventory);

	builder.setItems(itemsToDisplay, null);

	builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
	    public void onClick(DialogInterface dialog, int id) {

	    }
	});

	return builder.create();
    }
}
