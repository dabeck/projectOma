package de.unikassel.projectoma.fragment;

import de.unikassel.projectoma.MainActivity;
import de.unikassel.projectoma.R;
import de.unikassel.projectoma.model.Daytime;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MedicineFragment extends DialogFragment {

    private Daytime selected;

    public static MedicineFragment newInstance() {

	MedicineFragment f = new MedicineFragment();
	Bundle args = new Bundle();
	f.setArguments(args);

	return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

	builder.setTitle(R.string.medicine_msg);

	builder.setSingleChoiceItems(R.array.medicineTypes, -1, 
		new DialogInterface.OnClickListener() {

	    @Override
	    public void onClick(DialogInterface dialog, int selection) {
		selected = Daytime.values()[selection];
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
		    if(selected != null)
		    {
			MainActivity container = (MainActivity) getActivity();

			container.processMedicine(selected);						
			dismiss();
		    }
		}
	    });
	}
    }
}
