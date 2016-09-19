package com.intellisoftplus.grape.db.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.intellisoftplus.grape.db.contracts.CallContract;
import com.intellisoftplus.grape.db.contracts.ContactContract;
import com.intellisoftplus.grape.db.helpers.DatabaseHelper;

/**
 * Created by frank on 9/3/16.
 */
public class SaveContact extends AsyncTask<Object,Void,Long> {
    private DatabaseHelper helper;
    private String name, number;

    public SaveContact(Context context, String name,
                    String number
    ){
        this.helper = new DatabaseHelper(context);
        this.name = name;
        this.number=number;
    }

    @Override
    protected Long doInBackground(Object... objects) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContactContract.ContactEntry.COLUMN_NAME, name);
        values.put(ContactContract.ContactEntry.COLUMN_NUMBER, number);

        long newRowId = db.insert(
                CallContract.CallEntry.TABLE_NAME,
                null,
                values);
        db.close();
        return newRowId;
    }
}
