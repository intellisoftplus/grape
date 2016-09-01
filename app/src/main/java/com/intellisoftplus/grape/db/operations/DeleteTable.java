package com.intellisoftplus.grape.db.operations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.intellisoftplus.grape.db.helpers.DatabaseHelper;

/**
 * Created by cndet on 31/08/2016.
 */
public class DeleteTable extends AsyncTask<Object,Void,Void> {

    private DatabaseHelper helper;
    private String tableName;

    public DeleteTable(Context c, String tableName) {
        this.helper = new DatabaseHelper(c);
        this.tableName=tableName;
    }
    @Override
    protected Void doInBackground(Object... objects) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(tableName,null,null);
        db.execSQL("delete * from "+ tableName);
        db.execSQL("TRUNCATE table" + tableName);
        db.close();
        return null;
    }
}
