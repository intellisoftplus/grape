package com.intellisoftplus.grape.db.helpers;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.intellisoftplus.grape.db.contracts.LeadContract.LeadEntry;


/**
 * Created by User on 8/24/2016.
 */
public class LeadsDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Leads.db";
    private static final String DATA_TYPE_TEXT = " TEXT, ";

    private static final String CREATE_EVENTS_SQL =
            "CREATE TABLE "+LeadEntry.TABLE_NAME+" ("+
                    LeadEntry._ID+" INTEGER PRIMARY KEY, "+
                    LeadEntry.COLUMN_NAMES+DATA_TYPE_TEXT+LeadEntry.COLUMN_PHONE+
                    DATA_TYPE_TEXT+LeadEntry.COLUMN_EMAIL+DATA_TYPE_TEXT+
                    LeadEntry.COLUMN_WEBSITE+DATA_TYPE_TEXT+LeadEntry.COLUMN_STATUS
                    + DATA_TYPE_TEXT+LeadEntry.COLUMN_SOURCE+LeadEntry.COLUMN_INDUSTRY+LeadEntry.COLUMN_DESCRIPTION+" INTEGER )";
    private static final String DELETE_EVENTS_SQL =
            "DROP TABLE IF EXISTS "+LeadEntry.TABLE_NAME;

    public LeadsDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EVENTS_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DELETE_EVENTS_SQL);
        onCreate(db);
    }


}
