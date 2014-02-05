package de.unikassel.projectoma.fragment;

import de.unikassel.projectoma.GrandmaApplication;
import de.unikassel.projectoma.MainActivity;
import de.unikassel.projectoma.R;
import de.unikassel.projectoma.model.Food;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FeedFragment extends DialogFragment {

    private Food selected;
    private String[] itemsToDisplay;
    private Boolean satisfiable;

    public static FeedFragment newInstance() {

	FeedFragment f = new FeedFragment();
	Bundle args = new Bundle();
	f.setArguments(args);

	return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
	final GrandmaApplication app = (GrandmaApplication) getActivity().getApplication();

	int items = app.getGrandma().getPantry().size();

	if(items <= 0)
	{
	    itemsToDisplay = new String[1];
	    itemsToDisplay[0] = app.getApplicationContext().getString(R.string.msg_stock_empty);
	    satisfiable = false;
	}
	else
	{
	    satisfiable = true;

	    itemsToDisplay = new String[items];

	    int i = 0;
	    for(Food f : app.getGrandma().getPantry()) {
		itemsToDisplay[i] = f.getName();
		i++;
	    }
	}

	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

	builder.setTitle(R.string.food_msg);

	builder.setSingleChoiceItems(itemsToDisplay, -1, 
		new DialogInterface.OnClickListener() {

	    @Override
	    public void onClick(DialogInterface dialog, int selection) {
		if(satisfiable)
		{
		    selected = app.getGrandma().getPantry().get(selection);
		}
	    }
	});

	builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
	    public void onClick(DialogInterface dialog, int id) {

	    }
	})
	.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	    public void onClick(DialogInterface dialog, int id) {

	    }
	});

	return builder.create();
    }

    @Override
    public void onStart()
    {
	super.onStart();
	AlertDialog d = (AlertDialog)getDialog();
	if(d != null)
	{
	    Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
	    positiveButton.setOnClickListener(new View.OnClickListener()
	    {
		@Override
		public void onClick(View v)
		{
		    if(selected != null && satisfiable)
		    {
			MainActivity container = (MainActivity) getActivity();

			container.processFeeding(selected);						
			dismiss();
		    }
		}
	    });
	}
    }
}
