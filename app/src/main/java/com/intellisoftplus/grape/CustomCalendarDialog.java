package com.intellisoftplus.grape;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Locale;

/**
 * Created by cndet on 31/08/2016.
 */
public class CustomCalendarDialog {
    /**
     *
     * @param title Title of the dialog
     * @param currentView The view that had been selected
     *
     * Creates the dialog from which a user can select the date and time of an appointment
     *
     */
    public CustomCalendarDialog(final Activity activity, final String title, final View currentView, final  int affectedViewId){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        final View calView = activity.getLayoutInflater().inflate(R.layout.date_time_picker,null);

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
                        TextView e = (TextView) currentView.findViewById(affectedViewId);
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