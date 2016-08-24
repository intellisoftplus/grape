package com.intellisoftplus.grape.db.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.intellisoftplus.grape.db.contracts.EventContract.EventEntry;
import com.intellisoftplus.grape.db.helpers.EventDBHelper;

/**
 * Created by cndet on 23/08/2016.
 */
public class SaveEvent extends AsyncTask<Object,Void,Long> {
    private EventDBHelper helper;
    private String title;
    private String description;
    private String dtStart;
    private String dtEnd;
    private String location;
    private Boolean allDay;
    public SaveEvent(Context context, String title,
                     String description, String dtStart,
                     String dtEnd, String location,
                     Boolean allDay){
        this.helper = new EventDBHelper(context);
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
        values.put(EventEntry.COLUMN_TITLE, title);
        values.put(EventEntry.COLUMN_DESCRIPTION, description);
        values.put(EventEntry.COLUMN_DTSTART, dtStart);
        values.put(EventEntry.COLUMN_DTEND, dtEnd);
        values.put(EventEntry.COLUMN_LOCATION, location);
        values.put(EventEntry.COLUMN_ALLDAY, allDay ? 1 : 0);

        long newRowId = db.insert(
                EventEntry.TABLE_NAME,
                null,
                values);
        db.close();
        return newRowId;
    }
}
