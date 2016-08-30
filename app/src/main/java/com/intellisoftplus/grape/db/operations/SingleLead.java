package com.intellisoftplus.grape.db.operations;

import android.content.ContentValues;
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
public class SingleLead extends AsyncTask<Object,Void,LeadContract> {
    private LeadsDBHelper helper;
    private static String[] projection = {
            LeadEntry._ID,
            LeadEntry.COLUMN_NAMES, LeadEntry.COLUMN_PHONE,
            LeadEntry.COLUMN_EMAIL, LeadEntry.COLUMN_WEBSITE,
            LeadEntry.COLUMN_STATUS, LeadEntry.COLUMN_SOURCE,
            LeadEntry.COLUMN_INDUSTRY, LeadEntry.COLUMN_DESCRIPTION,
    };
    private String type;
    private String[] selectionArgs;
    private ContentValues values;
    private String selection = LeadEntry._ID + " = ?";
    public SingleLead(Context c, int id, String type, ContentValues values) {
        this.helper = new LeadsDBHelper(c);
        this.selectionArgs = new String[]{id + ""};
        this.type = type;
        this.values = values;
    }
    
    @Override
    protected LeadContract doInBackground(Object... objects) {
        LeadContract lead = null;
        switch (type) {
            case "read":
                lead = getLead();
                break;
            case "update":
                lead = updateLead(values);
                break;
            case "delete":
                lead = deleteLead();
                break;
        }

        return lead;
    }

    public LeadContract getLead(){
        SQLiteDatabase db = helper.getReadableDatabase();
        LeadContract lead = null;
        Cursor cursor = db.query(
                LeadEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            lead = new LeadContract(
                    cursor.getInt(0),cursor.getString(1),
                    cursor.getString(2),cursor.getString(3),
                    cursor.getString(4),cursor.getString(5),
                    cursor.getString(6),cursor.getString(7),
                    cursor.getString(8)
            );
        }
        cursor.close();
        db.close();
        return lead;
    }

    public LeadContract updateLead(ContentValues values){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.update(
                LeadEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
        db.close();

        return null;
    }

    public LeadContract deleteLead(){
        SQLiteDatabase db = helper.getReadableDatabase();
        db.delete(
                LeadEntry.TABLE_NAME,
                selection,
                selectionArgs
        );
        db.close();
        return null;
    }
}
