package com.intellisoftplus.grape;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.intellisoftplus.grape.db.operations.SaveAppointment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Fragment to create appointments.
 */

public class CreateAppointmentFragment extends Fragment {

    private View view;
    private CreateAppointmentFragment currentClass = this;
    public static CreateAppointmentFragment newInstance() {
        return new CreateAppointmentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_create_appointment, container, false);;
        TextView dtStart = (TextView) view.findViewById(R.id.dtstart);
        TextView dtEnd = (TextView)view.findViewById(R.id.dtend);
        Button submit = (Button)view.findViewById(R.id.submit_appointment);
        dtStart.setOnClickListener(clickHandler);
        dtEnd.setOnClickListener(clickHandler);
        submit.setOnClickListener(submissionHandler);
        // Inflate the layout for this fragment
        return view;
    }

    View.OnClickListener clickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dtstart:
                    new CustomCalendarDialog(getActivity(), "Start", view, R.id.dtstartstr);
                    break;
                case R.id.dtend:
                    new CustomCalendarDialog(getActivity(), "End", view, R.id.dtendstr);
                    break;
                default:
                    break;
            }
        }

    };
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
            Boolean allDayVal = allDay.isChecked();

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
            // Save appointment to DB
            SaveAppointment task = new SaveAppointment(
                getActivity(),title.getText().toString(),
                description.getText().toString(),dtStart.getText().toString(),
                dtEnd.getText().toString(),location.getText().toString(),
                allDayVal
            );
            long eventId = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
            try {
                eventId = task.execute().get();
                try{
                    long alarmTime = sdf.parse(dtStart.getText().toString()).getTime()-3600000;
                    Intent i = new Intent(getActivity(), SingleAppointmentActivity.class);
                    i.putExtra("eventId", eventId);
                    NotificationAlarm notificationAlarm = new NotificationAlarm(
                            getActivity(), alarmTime,
                            title.getText().toString(), description.getText().toString(),
                            "appointment", eventId,
                            SingleAppointmentActivity.class
                    );
                    notificationAlarm.init();
                } catch (ParseException e){
                    e.printStackTrace();
                }
            } catch (InterruptedException|ExecutionException e){
                e.printStackTrace();
            }
            // Hide keyboard after saving appointment
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            // Return to list of appointments
            FragmentManager fManager = getFragmentManager();
            fManager.popBackStackImmediate();
        }
    };
}
