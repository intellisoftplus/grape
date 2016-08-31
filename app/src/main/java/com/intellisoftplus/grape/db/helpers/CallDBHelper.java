package com.intellisoftplus.grape.db.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.intellisoftplus.grape.db.contracts.CallContract.CallEntry;

/**
 * Created by cndet on 23/08/2016.
 *
 * Handles event DB basic query logic
 *
 */
public class CallDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Calls.db";
    private static final String DATA_TYPE_TEXT = " TEXT, ";

    private static final String CREATE_EVENTS_SQL =
            "CREATE TABLE "+ CallEntry.TABLE_NAME+" ("+
            CallEntry._ID+" INTEGER PRIMARY KEY, "+
            CallEntry.COLUMN_TITLE+DATA_TYPE_TEXT+ CallEntry.COLUMN_DESCRIPTION+
            DATA_TYPE_TEXT+ CallEntry.COLUMN_ASSOCIATION+DATA_TYPE_TEXT+
            CallEntry.COLUMN_TIME +DATA_TYPE_TEXT+CallEntry.COLUMN_REMINDER+
            DATA_TYPE_TEXT+ CallEntry.COLUMN_PURPOSE+" TEXT )";
    private static final String DELETE_EVENTS_SQL =
            "DROP TABLE IF EXISTS "+ CallEntry.TABLE_NAME;

    public CallDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("Creating DB", CREATE_EVENTS_SQL);
        db.execSQL(CREATE_EVENTS_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DELETE_EVENTS_SQL);
        onCreate(db);
    }
}
