package com.intellisoftplus.grape.db.operations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.intellisoftplus.grape.db.contracts.AppointmentContract.AppointmentEntry;
import com.intellisoftplus.grape.db.helpers.AppointmentDBHelper;

/**
 * Created by cndet on 24/08/2016.
 *
 * Deletes all events in the EventDB
 *
 */
public class DeleteAppointments extends AsyncTask<Object,Void,Void> {

    private AppointmentDBHelper helper;


    public DeleteAppointments(Context c) {
        this.helper = new AppointmentDBHelper(c);
    }
    @Override
    protected Void doInBackground(Object... objects) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(AppointmentEntry.TABLE_NAME,null,null);
        db.execSQL("delete * from "+ AppointmentEntry.TABLE_NAME);
        db.execSQL("TRUNCATE table" + AppointmentEntry.TABLE_NAME);
        db.close();
        return null;
    }
}
