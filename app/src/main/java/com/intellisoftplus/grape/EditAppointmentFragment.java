package com.intellisoftplus.grape;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.intellisoftplus.grape.db.contracts.AppointmentContract.AppointmentEntry;
import com.intellisoftplus.grape.db.operations.SingleAppointment;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditAppointmentFragment extends Fragment {

    private long eventId;
    private View view;
    private Bundle extras;


    public EditAppointmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_create_appointment, container, false);

        EditText title = (EditText)view.findViewById(R.id.title);
        EditText description = (EditText)view.findViewById(R.id.description);
        TextView dtStart = (TextView)view.findViewById(R.id.dtstartstr);
        TextView dtEnd = (TextView)view.findViewById(R.id.dtendstr);
        EditText location = (EditText)view.findViewById(R.id.location);
        CheckBox allDay = (CheckBox)view.findViewById(R.id.all_day);
        TextView dtStartClickable = (TextView) view.findViewById(R.id.dtstart);
        TextView dtEndClickable = (TextView)view.findViewById(R.id.dtend);
        Button submit = (Button)view.findViewById(R.id.submit_appointment);
        dtStartClickable.setOnClickListener(clickHandler);
        dtEndClickable.setOnClickListener(clickHandler);
        submit.setOnClickListener(submissionHandler);
        this.extras = getArguments();
        this.eventId=extras.getLong("eventId");
        title.setText(extras.getString("title"));
        description.setText(extras.getString("description"));
        dtStart.setText(extras.getString("dtStart"));
        dtEnd.setText(extras.getString("dtEnd"));
        location.setText(extras.getString("location"));
        allDay.setChecked(extras.getBoolean("allDay"));
        return view;
    }

    View.OnClickListener submissionHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Validate form fields
            EditText title = (EditText)view.findViewById(R.id.title);
            EditText description = (EditText)view.findViewById(R.id.description);
            TextView dtStart = (TextView)view.findViewById(R.id.dtstartstr);
            TextView dtEnd = (TextView)view.findViewById(R.id.dtendstr);
            EditText location = (EditText)view.findViewById(R.id.location);
            CheckBox allDay = (CheckBox)view.findViewById(R.id.all_day);

            if(title.getText().toString().equals("")){
                title.setError("Please fill in the title.");
                return;
            }
            if(description.getText().toString().equals("")){
                description.setError("Please fill in the description.");
                return;
            }
            if(dtStart.getText().toString().equals(getString(R.string.date_format))){
                dtStart.setError("Please fill in the starting date.");
                return;
            }
            if(dtEnd.getText().toString().equals(getString(R.string.date_format))){
                dtEnd.setError("Please fill in the ending date.");
                return;
            }
            if(location.getText().toString().equals("")){
                location.setError("Please fill in the location.");
                return;
            }
            ContentValues values = new ContentValues();
            values.put(AppointmentEntry.COLUMN_TITLE, title.getText().toString());
            values.put(AppointmentEntry.COLUMN_DESCRIPTION, description.getText().toString());
            values.put(AppointmentEntry.COLUMN_DTSTART, dtStart.getText().toString());
            values.put(AppointmentEntry.COLUMN_DTEND, dtEnd.getText().toString());
            values.put(AppointmentEntry.COLUMN_LOCATION, location.getText().toString());
            values.put(AppointmentEntry.COLUMN_ALLDAY, allDay.isChecked() ? 1 : 0);
            // Save appointment to DB
            SingleAppointment task = new SingleAppointment(
                    getActivity(), eventId,
                    "update", values
            );
            task.execute();

            FragmentManager fManager = getFragmentManager();
            fManager.popBackStackImmediate();
        }
    };



    View.OnClickListener clickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CalendarDialog calendarDialog;
            switch (v.getId()){
                case R.id.dtstart:
                    calendarDialog = new CalendarDialog("Start", v);
                    break;
                case R.id.dtend:
                    calendarDialog = new CalendarDialog("End", v);
                    break;
                default:
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

                            int currentPicker = (title.equals("Start")) ? R.id.dtstartstr : R.id.dtendstr;
                            TextView e = (TextView) view.findViewById(currentPicker);
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
