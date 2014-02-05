package de.unikassel.projectoma.fragment;

import java.util.ArrayList;

import de.unikassel.projectoma.MainActivity;
import de.unikassel.projectoma.R;
import de.unikassel.projectoma.model.Food;
import de.unikassel.projectoma.model.FoodType;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShoppingFragment extends DialogFragment {
    ArrayList<Food> selected = new ArrayList<Food>();
    private String[] itemsToDisplay;

    public static ShoppingFragment newInstance() {

	ShoppingFragment f = new ShoppingFragment();
	Bundle args = new Bundle();
	f.setArguments(args);

	return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
	int foodCount = Food.FoodList.size();
	itemsToDisplay = new String[foodCount];
	
	for(int i = 0; i < foodCount; i++) {
	    itemsToDisplay[i] = Food.FoodList.get(FoodType.values()[i]).getName();
	}


	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

	builder.setTitle(R.string.shopping_msg);

	builder.setMultiChoiceItems(itemsToDisplay, null,
		new DialogInterface.OnMultiChoiceClickListener() {

	    @Override
	    public void onClick(DialogInterface dialog, int selection, boolean isChecked) {
		if(isChecked){
		    selected.add(Food.FoodList.get(FoodType.values()[selection]));
		} else {
		    selected.remove(Food.FoodList.get(FoodType.values()[selection]));
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
