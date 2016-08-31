package com.intellisoftplus.grape.db.operations;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.EventLog;
import android.util.Log;

import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.intellisoftplus.grape.db.contracts.EventContract;
import com.intellisoftplus.grape.db.contracts.EventContract.EventEntry;
import com.intellisoftplus.grape.db.helpers.EventDBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cndet on 23/08/2016.
 *
 * Reads all events from Event DB and returns them as a list of HashMaps
 *
 */
public class ReadEvents extends AsyncTask<Object,Void,List<EventContract>> {
    private EventDBHelper helper;
    private static String[] projection = {
        EventEntry._ID,
        EventEntry.COLUMN_TITLE, EventEntry.COLUMN_DESCRIPTION,
        EventEntry.COLUMN_DTSTART, EventEntry.COLUMN_DTEND,
        EventEntry.COLUMN_LOCATION, EventEntry.COLUMN_ALLDAY
    };
    public ReadEvents(Context c) {
        this.helper = new EventDBHelper(c);
    }
    @Override
    protected List<EventContract> doInBackground(Object[] objects) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(
            EventEntry.TABLE_NAME, projection,
            null, null, null, null,
            EventEntry._ID + " DESC"
        );
        List<EventContract> events = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                events.add(new EventContract(
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
