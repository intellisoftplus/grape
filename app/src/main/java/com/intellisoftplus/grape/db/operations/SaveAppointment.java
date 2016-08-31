package com.intellisoftplus.grape.db.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.intellisoftplus.grape.db.contracts.AppointmentContract.AppointmentEntry;
import com.intellisoftplus.grape.db.helpers.AppointmentDBHelper;

/**
 * Created by cndet on 23/08/2016.
 *
 * Saves a single event to the EventDB
 *
 */
public class SaveAppointment extends AsyncTask<Object,Void,Long> {
    private AppointmentDBHelper helper;
    private String title;
    private String description;
    private String dtStart;
    private String dtEnd;
    private String location;
    private Boolean allDay;
    public SaveAppointment(Context context, String title,
                           String description, String dtStart,
                           String dtEnd, String location,
                           Boolean allDay){
        this.helper = new AppointmentDBHelper(context);
        this.title = title;
        this.description = description;
        this.dtStart = dtStart;
        this.dtEnd = dtEnd;
        this.location = location;
        this.allDay = allDay;
    }

    @Override
    protected Long doInBackground(Object... objects) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AppointmentEntry.COLUMN_TITLE, title);
        values.put(AppointmentEntry.COLUMN_DESCRIPTION, description);
        values.put(AppointmentEntry.COLUMN_DTSTART, dtStart);
        values.put(AppointmentEntry.COLUMN_DTEND, dtEnd);
        values.put(AppointmentEntry.COLUMN_LOCATION, location);
        values.put(AppointmentEntry.COLUMN_ALLDAY, allDay ? 1 : 0);

        long newRowId = db.insert(
                AppointmentEntry.TABLE_NAME,
                null,
                values);
        db.close();
        return newRowId;
    }
}
