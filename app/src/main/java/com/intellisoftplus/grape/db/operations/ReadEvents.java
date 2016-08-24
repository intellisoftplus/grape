package com.intellisoftplus.grape.db.operations;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.EventLog;
import android.util.Log;

import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
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
public class ReadEvents extends AsyncTask<Object,Void,List<HashMap<String,String>>> {
    private EventDBHelper helper;
    private static String[] projection = {
        EventEntry.COLUMN_TITLE, EventEntry.COLUMN_DESCRIPTION,
        EventEntry.COLUMN_DTSTART, EventEntry.COLUMN_DTEND,
        EventEntry.COLUMN_LOCATION, EventEntry.COLUMN_ALLDAY
    };
    public ReadEvents(Context c) {
        this.helper = new EventDBHelper(c);
    }
    @Override
    protected List<HashMap<String,String>> doInBackground(Object[] objects) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(
            EventEntry.TABLE_NAME, projection,
            null, null, null, null,
            EventEntry._ID + " DESC"
        );
        List<HashMap<String,String>> events = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> map = new HashMap<>();
                map.put("TITLE", cursor.getString(0));
                map.put("DESCRIPTION", cursor.getString(1));
                map.put("DTSTART", cursor.getString(2));
                map.put("DTEND", cursor.getString(3));
                map.put("LOCATION", cursor.getString(4));
                map.put("ALLDAY", String.valueOf(cursor.getInt(5)==1));
                events.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return events;
    }
}
