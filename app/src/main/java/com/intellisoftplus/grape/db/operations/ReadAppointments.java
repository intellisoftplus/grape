package com.intellisoftplus.grape.db.operations;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.intellisoftplus.grape.db.contracts.AppointmentContract;
import com.intellisoftplus.grape.db.contracts.AppointmentContract.AppointmentEntry;
import com.intellisoftplus.grape.db.helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cndet on 23/08/2016.
 *
 * Reads all events from Event DB and returns them as a list of HashMaps
 *
 */
public class ReadAppointments extends AsyncTask<Object,Void,List<AppointmentContract>> {
    private DatabaseHelper helper;
    private static String[] projection = {
        AppointmentEntry._ID,
        AppointmentEntry.COLUMN_TITLE, AppointmentEntry.COLUMN_DESCRIPTION,
        AppointmentEntry.COLUMN_DTSTART, AppointmentEntry.COLUMN_DTEND,
        AppointmentEntry.COLUMN_LOCATION, AppointmentEntry.COLUMN_ALLDAY
    };
    public ReadAppointments(Context c) {
        this.helper = new DatabaseHelper(c);
    }
    @Override
    protected List<AppointmentContract> doInBackground(Object[] objects) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(
            AppointmentEntry.TABLE_NAME, projection,
            null, null, null, null,
            AppointmentEntry._ID + " DESC"
        );
        List<AppointmentContract> events = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                events.add(new AppointmentContract(
                        cursor.getInt(0),cursor.getString(1),
                        cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),
                        cursor.getInt(6)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return events;
    }
}
