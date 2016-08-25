package com.intellisoftplus.grape.db.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.intellisoftplus.grape.db.contracts.EventContract.EventEntry;

/**
 * Created by cndet on 23/08/2016.
 *
 * Handles event DB basic query logic
 *
 */
public class EventDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Events.db";
    private static final String DATA_TYPE_TEXT = " TEXT, ";

    private static final String CREATE_EVENTS_SQL =
            "CREATE TABLE "+EventEntry.TABLE_NAME+" ("+
            EventEntry._ID+" INTEGER PRIMARY KEY, "+
            EventEntry.COLUMN_TITLE+DATA_TYPE_TEXT+EventEntry.COLUMN_DESCRIPTION+
            DATA_TYPE_TEXT+EventEntry.COLUMN_DTSTART+DATA_TYPE_TEXT+
            EventEntry.COLUMN_DTEND+DATA_TYPE_TEXT+EventEntry.COLUMN_LOCATION
            + DATA_TYPE_TEXT+EventEntry.COLUMN_ALLDAY+" INTEGER )";
    private static final String DELETE_EVENTS_SQL =
            "DROP TABLE IF EXISTS "+EventEntry.TABLE_NAME;

    public EventDBHelper(Context context){
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
