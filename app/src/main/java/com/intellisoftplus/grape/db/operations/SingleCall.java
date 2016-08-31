package com.intellisoftplus.grape.db.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.intellisoftplus.grape.db.contracts.CallContract;
import com.intellisoftplus.grape.db.contracts.CallContract.CallEntry;
import com.intellisoftplus.grape.db.helpers.CallDBHelper;

/**
 * Created by cndet on 29/08/2016.
 */
public class SingleCall extends AsyncTask<Object,Void,CallContract> {
    private CallDBHelper helper;
    private static String[] projection = {
            CallEntry._ID,
            CallEntry.COLUMN_TITLE, CallEntry.COLUMN_DESCRIPTION,
            CallEntry.COLUMN_PURPOSE, CallEntry.COLUMN_ASSOCIATION,
            CallEntry.COLUMN_TIME, CallEntry.COLUMN_REMINDER
    };
    private String type;
    private String[] selectionArgs;
    private ContentValues values;
    private String selection = CallEntry._ID + " = ?";
    public SingleCall(Context c, long id, String type, ContentValues values) {
        this.helper = new CallDBHelper(c);
        this.selectionArgs = new String[]{id+""};
        this.type=type;
        this.values=values;
    }
    @Override
    protected CallContract doInBackground(Object... objects) {
        CallContract event = null;
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

    public CallContract getEvent(){
        SQLiteDatabase db = helper.getReadableDatabase();
        CallContract event = null;
        Cursor cursor = db.query(
                CallEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            event = new CallContract(
                    cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5),
                    cursor.getString(6)
            );
        }
        cursor.close();
        return event;
    }

    public CallContract updateEvent(ContentValues values){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.update(
                CallEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
        db.close();

        return null;
    }

    public CallContract deleteEvent(){
        SQLiteDatabase db = helper.getReadableDatabase();
        db.delete(
                CallEntry.TABLE_NAME,
                selection,
                selectionArgs
        );
        return null;
    }
}
