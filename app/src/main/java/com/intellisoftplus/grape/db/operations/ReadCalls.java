package com.intellisoftplus.grape.db.operations;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.intellisoftplus.grape.db.contracts.CallContract;
import com.intellisoftplus.grape.db.contracts.CallContract.CallEntry;
import com.intellisoftplus.grape.db.helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 8/24/2016.
 */
public class ReadCalls extends AsyncTask<Object,Void,List<CallContract>> {

    private DatabaseHelper helper;
    private static String[] projection = {
            CallEntry._ID,
            CallEntry.COLUMN_TITLE, CallEntry.COLUMN_DESCRIPTION,
            CallEntry.COLUMN_ASSOCIATION, CallEntry.COLUMN_PURPOSE,
            CallEntry.COLUMN_TIME, CallEntry.COLUMN_REMINDER
    };
    public ReadCalls(Context c) {
        this.helper = new DatabaseHelper(c);
    }
    @Override
    protected List<CallContract> doInBackground(Object[] objects) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(
                CallEntry.TABLE_NAME, projection,
                null, null, null, null,
                CallEntry._ID + " DESC"
        );
        List<CallContract> calls = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                calls.add(new CallContract(
                        cursor.getInt(0),cursor.getString(1),
                        cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),
                        cursor.getString(6)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return calls;
    }
}
