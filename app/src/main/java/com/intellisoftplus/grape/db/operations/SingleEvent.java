package com.intellisoftplus.grape.db.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.intellisoftplus.grape.db.contracts.EventContract;
import com.intellisoftplus.grape.db.contracts.EventContract.EventEntry;
import com.intellisoftplus.grape.db.helpers.EventDBHelper;

/**
 * Created by cndet on 29/08/2016.
 */
public class SingleEvent extends AsyncTask<Object,Void,EventContract> {
    private EventDBHelper helper;
    private static String[] projection = {
            EventEntry._ID,
            EventEntry.COLUMN_TITLE, EventEntry.COLUMN_DESCRIPTION,
            EventEntry.COLUMN_DTSTART, EventEntry.COLUMN_DTEND,
            EventEntry.COLUMN_LOCATION, EventEntry.COLUMN_ALLDAY
    };
    private String type;
    private String[] selectionArgs;
    private ContentValues values;
    private String selection = EventEntry._ID + " = ?";
    public SingleEvent(Context c, long id, String type, ContentValues values) {
        this.helper = new EventDBHelper(c);
        this.selectionArgs = new String[]{id+""};
        this.type=type;
        this.values=values;
    }
    @Override
    protected EventContract doInBackground(Object... objects) {
        EventContract event = null;
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

    public EventContract getEvent(){
        SQLiteDatabase db = helper.getReadableDatabase();
        EventContract event = null;
        Cursor cursor = db.query(
                EventEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            event = new EventContract(
                    cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5),
                    cursor.getInt(6)
            );
        }
        cursor.close();
        return event;
    }

    public EventContract updateEvent(ContentValues values){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.update(
                EventEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
        db.close();

        return null;
    }

    public EventContract deleteEvent(){
        SQLiteDatabase db = helper.getReadableDatabase();
        db.delete(
                EventEntry.TABLE_NAME,
                selection,
                selectionArgs
        );
        return null;
    }
}
