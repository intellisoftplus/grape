package com.intellisoftplus.grape.db.operations;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.intellisoftplus.grape.db.contracts.LeadContract;
import com.intellisoftplus.grape.db.contracts.LeadContract.LeadEntry;
import com.intellisoftplus.grape.db.helpers.LeadsDBHelper;

/**
 * Created by cndet on 29/08/2016.
 */
public class ReadSingleLead extends AsyncTask<Object,Void,LeadContract> {
    private LeadsDBHelper helper;
    private static String[] projection = {
            LeadEntry._ID,
            LeadEntry.COLUMN_NAMES, LeadEntry.COLUMN_PHONE,
            LeadEntry.COLUMN_EMAIL, LeadEntry.COLUMN_WEBSITE,
            LeadEntry.COLUMN_STATUS, LeadEntry.COLUMN_SOURCE,
            LeadEntry.COLUMN_INDUSTRY, LeadEntry.COLUMN_DESCRIPTION,
    };
    String selection = LeadEntry._ID+" = ?";
    private String[] selectionArgs;
    public ReadSingleLead(Context c, int id) {
        this.helper = new LeadsDBHelper(c);
        this.selectionArgs = new String[]{id+""};
    }
    @Override
    protected LeadContract doInBackground(Object... objects) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(
                LeadEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        LeadContract lead;
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            lead = new LeadContract(
                    cursor.getInt(0),cursor.getString(1),
                    cursor.getString(2),cursor.getString(3),
                    cursor.getString(4),cursor.getString(5),
                    cursor.getString(6),cursor.getString(7),
                    cursor.getString(8)
            );
        } else {
            lead = null;
        }
        cursor.close();
        db.close();

        return lead;
    }
}
