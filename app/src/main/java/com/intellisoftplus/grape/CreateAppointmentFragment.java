package com.intellisoftplus.grape;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class CreateAppointmentFragment extends Fragment {

    private View view;
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
            CalendarDialog calendarDialog;
            switch (v.getId()){
                case R.id.dtstart:
                    calendarDialog = new CalendarDialog("Start", view);
                    break;
                case R.id.dtend:
                    calendarDialog = new CalendarDialog("End", view);
                    break;
                default:
                    break;
            }
        }

    };
    View.OnClickListener submissionHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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
            if(dtStart.getText().toString().equals("DD/MM/YYYY")){
                dtStart.setError("Please fill in the starting date.");
                return;
            }
            displayToast(getActivity(), dtStart.getText().toString(), Toast.LENGTH_LONG);
            if(dtEnd.getText().toString().equals("DD/MM/YYYY")){
                dtEnd.setError("Please fill in the ending date.");
                return;
            }
            if(location.getText().toString().equals("")){
                location.setError("Please fill in the location.");
                return;
            }
        }

    };

    private static void displayToast(Context context, CharSequence text, int duration){
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private class CalendarDialog {
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
                            // if this button is clicked, get date and time
                            TimePicker timePicker = (TimePicker)calView.findViewById(R.id.timePicker);
                            DatePicker datePicker = (DatePicker)calView.findViewById(R.id.datePicker);
                            String date = String.format("%d/%d/%d", datePicker.getYear(),
                                    datePicker.getMonth(), datePicker.getDayOfMonth());
                            String time;
                            try {
                                time = String.format("%d:%d", timePicker.getHour(),
                                        timePicker.getMinute());
                            } catch (NoSuchMethodError e) {
                                time = String.format("%d:%d", timePicker.getCurrentHour(),
                                        timePicker.getCurrentMinute());
                                e.printStackTrace();
                            }

                            int currentPicker = (title.equals("Start")) ? R.id.dtstartstr : R.id.dtendstr;
                            TextView e = (TextView) currentView.findViewById(currentPicker);
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
