package com.intellisoftplus.grape.db.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.intellisoftplus.grape.db.contracts.AppointmentContract;
import com.intellisoftplus.grape.db.contracts.AppointmentContract.AppointmentEntry;
import com.intellisoftplus.grape.db.helpers.AppointmentDBHelper;

/**
 * Created by cndet on 29/08/2016.
 */
public class SingleAppointment extends AsyncTask<Object,Void,AppointmentContract> {
    private AppointmentDBHelper helper;
    private static String[] projection = {
            AppointmentEntry._ID,
            AppointmentEntry.COLUMN_TITLE, AppointmentEntry.COLUMN_DESCRIPTION,
            AppointmentEntry.COLUMN_DTSTART, AppointmentEntry.COLUMN_DTEND,
            AppointmentEntry.COLUMN_LOCATION, AppointmentEntry.COLUMN_ALLDAY
    };
    private String type;
    private String[] selectionArgs;
    private ContentValues values;
    private String selection = AppointmentEntry._ID + " = ?";
    public SingleAppointment(Context c, long id, String type, ContentValues values) {
        this.helper = new AppointmentDBHelper(c);
        this.selectionArgs = new String[]{id+""};
        this.type=type;
        this.values=values;
    }
    @Override
    protected AppointmentContract doInBackground(Object... objects) {
        AppointmentContract event = null;
        switch (type) {
            case "read":
                event = getEvent();
                break;
            case "update":
                event = updateEvent(values);
                break;
            case "delete":
                event = deleteEvent();
                break;
        }

        return event;
    }

    public AppointmentContract getEvent(){
        SQLiteDatabase db = helper.getReadableDatabase();
        AppointmentContract event = null;
        Cursor cursor = db.query(
                AppointmentEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            event = new AppointmentContract(
                    cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5),
                    cursor.getInt(6)
            );
        }
        cursor.close();
        return event;
    }

    public AppointmentContract updateEvent(ContentValues values){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.update(
                AppointmentEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
        db.close();

        return null;
    }

    public AppointmentContract deleteEvent(){
        SQLiteDatabase db = helper.getReadableDatabase();
        db.delete(
                AppointmentEntry.TABLE_NAME,
                selection,
                selectionArgs
        );
        return null;
    }
}
