package com.intellisoftplus.grape.db.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.intellisoftplus.grape.db.contracts.TaskContract;
import com.intellisoftplus.grape.db.helpers.DatabaseHelper;

/**
 * Created by frank on 9/3/16.
 */
public class SaveTask extends AsyncTask<Object,Void,Long> {
    private DatabaseHelper helper;
    private String title, description, association, starttime, endtime;

    public SaveTask(Context context, String title,
                    String description, String association,
                    String starttime, String endtime
    ){
        this.helper = new DatabaseHelper(context);
        this.title = title;
        this.description=description;
        this.association =association;
        this.starttime =endtime;
        this.endtime =endtime;
    }

    @Override
    protected Long doInBackground(Object... objects) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_TITLE, title);
        values.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, description);
        values.put(TaskContract.TaskEntry.COLUMN_ASSOCIATION, association);
        values.put(TaskContract.TaskEntry.COLUMN_STARTTIME, starttime);
        values.put(TaskContract.TaskEntry.COLUMN_ENDTIME, endtime);

        long newRowId = db.insert(
                TaskContract.TaskEntry.TABLE_NAME,
                null,
                values);
        db.close();
        return newRowId;
    }
}
