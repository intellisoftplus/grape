package com.intellisoftplus.grape.db.operations;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.EventLog;
import android.util.Log;

import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.intellisoftplus.grape.db.contracts.LeadContract;
import com.intellisoftplus.grape.db.contracts.LeadContract.LeadEntry;
import com.intellisoftplus.grape.db.helpers.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 8/24/2016.
 */
public class ReadLeads extends AsyncTask<Object,Void,List<LeadContract>> {

    private DatabaseHelper helper;
    private static String[] projection = {
            LeadEntry._ID,
            LeadEntry.COLUMN_NAMES, LeadEntry.COLUMN_PHONE,
            LeadEntry.COLUMN_EMAIL, LeadEntry.COLUMN_WEBSITE,
            LeadEntry.COLUMN_STATUS, LeadEntry.COLUMN_SOURCE,
            LeadEntry.COLUMN_INDUSTRY, LeadEntry.COLUMN_DESCRIPTION,

    };
    public ReadLeads(Context c) {
        this.helper = new DatabaseHelper(c);
    }
    @Override
    protected List<LeadContract> doInBackground(Object[] objects) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(
                LeadEntry.TABLE_NAME, projection,
                null, null, null, null,
                LeadEntry._ID + " DESC"
        );
        List<LeadContract> events = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                events.add(new LeadContract(
                        cursor.getInt(0),cursor.getString(1),
                        cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),
                        cursor.getString(6),cursor.getString(7),
                        cursor.getString(8)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return events;
    }
}
