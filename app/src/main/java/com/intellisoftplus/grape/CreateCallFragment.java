package com.intellisoftplus.grape;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.intellisoftplus.grape.db.operations.SaveCall;

import java.util.Locale;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateCallFragment extends Fragment {

    private View view;


    public CreateCallFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_create_call, container, false);
        Button addCall = (Button)view.findViewById(R.id.submit_new_call);
        ImageView calImage = (ImageView)view.findViewById(R.id.generateCallTimePicker);
        addCall.setOnClickListener(clickHandler);
        calImage.setOnClickListener(clickHandler);
        return view;
    }
    View.OnClickListener clickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.submit_new_call:
                    EditText title = (EditText) view.findViewById(R.id.new_call_title);
                    EditText description = (EditText) view.findViewById(R.id.new_call_description);
                    EditText purpose = (EditText) view.findViewById(R.id.new_call_purpose);
                    EditText association = (EditText) view.findViewById(R.id.new_call_association);
                    TextView time = (TextView) view.findViewById(R.id.new_call_time);

                    if (title.getText().toString().equals("")) {
                        title.setError("Please fill in the title");
                        return;
                    }
                    if (description.getText().toString().equals("")) {
                        description.setError("Please fill in the description");
                        return;
                    }
                    if (purpose.getText().toString().equals("")) {
                        purpose.setError("Please fill in the purpose");
                        return;
                    }
                    if (association.getText().toString().equals("")) {
                        association.setError("Please fill in the association");
                        return;
                    }
                    if (time.getText().toString().equals("")) {
                        time.setError("Please fill in the time");
                        return;
                    }

                    SaveCall task = new SaveCall(
                            getActivity(), title.getText().toString(),
                            description.getText().toString(), association.getText().toString(),
                            purpose.getText().toString(), time.getText().toString()
                    );
                    task.execute();
                    getFragmentManager()
                            .popBackStackImmediate();
                    break;
                case R.id.generateCallTimePicker:
                    new CalendarDialog("Time", view);
                    break;
            }
        }
    };

    private class CalendarDialog {
        /**
         *
         * @param title Title of the dialog
         * @param currentView The view that had been selected
         *
         * Creates the dialog from which a user can select the date and time of an appointment
         *
         */
        public CalendarDialog(final String title, final View currentView){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    getActivity());
            final View calView = getActivity().getLayoutInflater().inflate(R.layout.date_time_picker,null);

            // set title
            alertDialogBuilder.setTitle(title);

            // set dialog message
            alertDialogBuilder
                    .setView(calView)
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, get date and time and set them as text in the selected view
                            TimePicker timePicker = (TimePicker)calView.findViewById(R.id.timePicker);
                            DatePicker datePicker = (DatePicker)calView.findViewById(R.id.datePicker);
                            String date = String.format(Locale.ENGLISH,"%02d/%d/%d ", datePicker.getDayOfMonth() ,datePicker.getMonth()+1, datePicker.getYear());
                            String time;
                            int hours,minutes;
                            // Get hours and minutes accounting for method deprecation
                            try {
                                hours = timePicker.getCurrentHour();
                                minutes =timePicker.getCurrentMinute();
                            } catch (NoSuchMethodError e) {
                                hours = timePicker.getHour();
                                minutes =timePicker.getMinute();
                                e.printStackTrace();
                            }
                            time = String.format(Locale.ENGLISH, "%d:%02d",hours,minutes);
                            date = date + time;

                            TextView e = (TextView) currentView.findViewById(R.id.new_call_time);
                            e.setText(date);
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });
            alertDialogBuilder.create().show();
        }
    }
}
