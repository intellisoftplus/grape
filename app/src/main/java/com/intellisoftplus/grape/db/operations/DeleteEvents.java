package com.intellisoftplus.grape.db.operations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.intellisoftplus.grape.db.contracts.EventContract.EventEntry;
import com.intellisoftplus.grape.db.helpers.EventDBHelper;

/**
 * Created by cndet on 24/08/2016.
 */
public class DeleteEvents extends AsyncTask<Object,Void,Void> {

    private EventDBHelper helper;


    public DeleteEvents(Context c) {
        this.helper = new EventDBHelper(c);
    }
    @Override
    protected Void doInBackground(Object... objects) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(EventEntry.TABLE_NAME,null,null);
        db.execSQL("delete * from "+ EventEntry.TABLE_NAME);
        db.execSQL("TRUNCATE table" + EventEntry.TABLE_NAME);
        db.close();
        return null;
    }
}
