package de.unikassel.projectoma.fragment;

import java.util.ArrayList;

import de.unikassel.projectoma.MainActivity;
import de.unikassel.projectoma.R;
import de.unikassel.projectoma.model.FoodType;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShoppingFragment extends DialogFragment {
    ArrayList<FoodType> selected = new ArrayList<FoodType>();

    public static ShoppingFragment newInstance() {

	ShoppingFragment f = new ShoppingFragment();
	Bundle args = new Bundle();
	f.setArguments(args);

	return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

	builder.setTitle(R.string.shopping_msg);

	builder.setMultiChoiceItems(R.array.foodTypes, null,
		new DialogInterface.OnMultiChoiceClickListener() {

	    @Override
	    public void onClick(DialogInterface dialog, int selection, boolean isChecked) {
		selected.add(FoodType.values()[selection]);

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
		    if(!selected.isEmpty())
		    {
			MainActivity container = (MainActivity) getActivity();

			container.processShopping(selected);						
			dismiss();
		    }
		}
	    });
	}
    }
}
