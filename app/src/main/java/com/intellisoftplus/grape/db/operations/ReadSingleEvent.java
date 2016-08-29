package com.intellisoftplus.grape.db.operations;

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
public class ReadSingleEvent extends AsyncTask<Object,Void,EventContract> {
    private EventDBHelper helper;
    private static String[] projection = {
            EventEntry._ID,
            EventEntry.COLUMN_TITLE, EventEntry.COLUMN_DESCRIPTION,
            EventEntry.COLUMN_DTSTART, EventEntry.COLUMN_DTEND,
            EventEntry.COLUMN_LOCATION, EventEntry.COLUMN_ALLDAY
    };
    String selection = EventEntry._ID+" = ?";
    private String[] selectionArgs;
    public ReadSingleEvent(Context c, long id) {
        this.helper = new EventDBHelper(c);
        this.selectionArgs = new String[]{id+""};
    }
    @Override
    protected EventContract doInBackground(Object... objects) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(
                EventEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        EventContract event;
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            event = new EventContract(
                    cursor.getInt(0),cursor.getString(1),
                    cursor.getString(2),cursor.getString(3),
                    cursor.getString(4),cursor.getString(5),
                    cursor.getInt(6)
            );
        } else {
            event = null;
        }
        cursor.close();
        db.close();

        return event;
    }
}
