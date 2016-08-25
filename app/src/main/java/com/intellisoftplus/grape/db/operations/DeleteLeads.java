package com.intellisoftplus.grape.db.operations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.intellisoftplus.grape.db.contracts.LeadContract.LeadEntry;
import com.intellisoftplus.grape.db.helpers.LeadsDBHelper;

/**
 * Created by cndet on 24/08/2016.
 *
 * Deletes all events in the EventDB
 *
 */
public class DeleteLeads extends AsyncTask<Object,Void,Void> {

    private LeadsDBHelper helper;


    public DeleteLeads(Context c) {
        this.helper = new LeadsDBHelper(c);
    }
    @Override
    protected Void doInBackground(Object... objects) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(LeadEntry.TABLE_NAME,null,null);
        db.execSQL("delete * from "+ LeadEntry.TABLE_NAME);
        db.execSQL("TRUNCATE table" + LeadEntry.TABLE_NAME);
        db.close();
        return null;
    }
}
