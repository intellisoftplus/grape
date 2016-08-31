package com.intellisoftplus.grape.db.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.intellisoftplus.grape.db.contracts.CallContract.CallEntry;
import com.intellisoftplus.grape.db.helpers.CallDBHelper;

/**
 * Created by cndet on 23/08/2016.
 *
 * Saves a single event to the EventDB
 *
 */
public class SaveCall extends AsyncTask<Object,Void,Long> {
    private CallDBHelper helper;
    private String title, description, association, purpose, time;

    public SaveCall(Context context, String title,
                    String description, String association,
                    String purpose, String time){
        this.helper = new CallDBHelper(context);
        this.title = title;
        this.description=description;
        this.association =association;
        this.purpose =purpose;
        this.time =time;
    }

    @Override
    protected Long doInBackground(Object... objects) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CallEntry.COLUMN_TITLE, title);
        values.put(CallEntry.COLUMN_DESCRIPTION, description);
        values.put(CallEntry.COLUMN_ASSOCIATION, association);
        values.put(CallEntry.COLUMN_PURPOSE, purpose);
        values.put(CallEntry.COLUMN_TIME, time);

        long newRowId = db.insert(
                CallEntry.TABLE_NAME,
                null,
                values);
        db.close();
        return newRowId;
    }
}
