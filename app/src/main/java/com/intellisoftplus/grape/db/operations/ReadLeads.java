package com.intellisoftplus.grape.db.operations;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.EventLog;
import android.util.Log;

import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.intellisoftplus.grape.db.contracts.LeadContract.LeadEntry;
import com.intellisoftplus.grape.db.helpers.LeadsDBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 8/24/2016.
 */
public class ReadLeads extends AsyncTask<Object,Void,List<HashMap<String,String>>> {

    private LeadsDBHelper helper;
    private static String[] projection = {
            LeadEntry.COLUMN_NAMES, LeadEntry.COLUMN_WEBSITE,
            LeadEntry.COLUMN_PHONE, LeadEntry.COLUMN_STATUS,
            LeadEntry.COLUMN_EMAIL, LeadEntry.COLUMN_SOURCE,
            LeadEntry.COLUMN_INDUSTRY, LeadEntry.COLUMN_DESCRIPTION,

    };
    public ReadLeads(Context c) {
        this.helper = new LeadsDBHelper(c);
    }
    @Override
    protected List<HashMap<String,String>> doInBackground(Object[] objects) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(
                LeadEntry.TABLE_NAME, projection,
                null, null, null, null,
                LeadEntry._ID + " DESC"
        );
        List<HashMap<String,String>> events = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("NAMES", cursor.getString(0));
                map.put("DESCRIPTION", cursor.getString(1));
                map.put("PHONE", cursor.getString(2));
                map.put("WEBSITE", cursor.getString(3));
                map.put("STATUS", cursor.getString(4));
                map.put("SOURCE", cursor.getString(5));
                map.put("INDUSTRY", cursor.getString(6));
                map.put("DESCRIPTION", cursor.getString(7));

                events.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return events;
    }
}
