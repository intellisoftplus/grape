package com.intellisoftplus.grape.db.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.intellisoftplus.grape.db.contracts.AppointmentContract.AppointmentEntry;
import com.intellisoftplus.grape.db.contracts.CallContract.CallEntry;
import com.intellisoftplus.grape.db.contracts.LeadContract.LeadEntry;

/**
 * Created by cndet on 31/08/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Grape.db";
    private static final String DATA_TYPE_TEXT = " TEXT, ";

//    Appointments Table
    private static final String CREATE_APPOINTMENTS_SQL =
            "CREATE TABLE "+ AppointmentEntry.TABLE_NAME+" ("+
                    AppointmentEntry._ID+" INTEGER PRIMARY KEY, "+
                    AppointmentEntry.COLUMN_TITLE+DATA_TYPE_TEXT+ AppointmentEntry.COLUMN_DESCRIPTION+
                    DATA_TYPE_TEXT+ AppointmentEntry.COLUMN_DTSTART+DATA_TYPE_TEXT+
                    AppointmentEntry.COLUMN_DTEND+DATA_TYPE_TEXT+ AppointmentEntry.COLUMN_LOCATION
                    + DATA_TYPE_TEXT+ AppointmentEntry.COLUMN_ALLDAY+" INTEGER )";
    private static final String DELETE_APPOINTMENTS_SQL =
            "DROP TABLE IF EXISTS "+ AppointmentEntry.TABLE_NAME;

//    Calls Table
    private static final String CREATE_CALLS_SQL =
            "CREATE TABLE "+ CallEntry.TABLE_NAME+" ("+
                    CallEntry._ID+" INTEGER PRIMARY KEY, "+
                    CallEntry.COLUMN_TITLE+DATA_TYPE_TEXT+ CallEntry.COLUMN_DESCRIPTION+
                    DATA_TYPE_TEXT+ CallEntry.COLUMN_ASSOCIATION+DATA_TYPE_TEXT+
                    CallEntry.COLUMN_TIME +DATA_TYPE_TEXT+CallEntry.COLUMN_REMINDER+
                    DATA_TYPE_TEXT+ CallEntry.COLUMN_PURPOSE+" TEXT )";
    private static final String DELETE_CALLS_SQL =
            "DROP TABLE IF EXISTS "+ CallEntry.TABLE_NAME;

//  Leads Table
    private static final String CREATE_LEADS_SQL =
            "CREATE TABLE "+LeadEntry.TABLE_NAME+" ("+
                    LeadEntry._ID+" INTEGER PRIMARY KEY, "+
                    LeadEntry.COLUMN_NAMES+DATA_TYPE_TEXT+LeadEntry.COLUMN_PHONE+
                    DATA_TYPE_TEXT+LeadEntry.COLUMN_EMAIL+DATA_TYPE_TEXT+
                    LeadEntry.COLUMN_WEBSITE+DATA_TYPE_TEXT+LeadEntry.COLUMN_STATUS
                    + DATA_TYPE_TEXT+LeadEntry.COLUMN_SOURCE+DATA_TYPE_TEXT
                    +LeadEntry.COLUMN_INDUSTRY+DATA_TYPE_TEXT+LeadEntry.COLUMN_DESCRIPTION+" TEXT )";
    private static final String DELETE_LEADS_SQL =
            "DROP TABLE IF EXISTS "+LeadEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_APPOINTMENTS_SQL);
        db.execSQL(CREATE_CALLS_SQL);
        db.execSQL(CREATE_LEADS_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DELETE_APPOINTMENTS_SQL);
        db.execSQL(DELETE_CALLS_SQL);
        db.execSQL(DELETE_LEADS_SQL);
        onCreate(db);
    }
}
