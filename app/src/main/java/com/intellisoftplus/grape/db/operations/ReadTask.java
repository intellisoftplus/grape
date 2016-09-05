package com.intellisoftplus.grape.db.operations;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.intellisoftplus.grape.db.contracts.TaskContract;
import com.intellisoftplus.grape.db.helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank on 9/5/16.
 */
public class ReadTask extends AsyncTask<Object,Void,List<TaskContract>>{
    private DatabaseHelper helper;
    private static String[] projection = {
            TaskContract.TaskEntry._ID,
            TaskContract.TaskEntry.COLUMN_TITLE, TaskContract.TaskEntry.COLUMN_DESCRIPTION,
            TaskContract.TaskEntry.COLUMN_ASSOCIATION, TaskContract.TaskEntry.COLUMN_STARTTIME,
            TaskContract.TaskEntry.COLUMN_ENDTIME
    };
    public ReadTask(Context c) {
        this.helper = new DatabaseHelper(c);
    }
    @Override
    protected List<TaskContract> doInBackground(Object[] objects) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(
                TaskContract.TaskEntry.TABLE_NAME, projection,
                null, null, null, null,
                TaskContract.TaskEntry._ID + " DESC"
        );
        List<TaskContract> task = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                task.add(new TaskContract(
                        cursor.getInt(0),cursor.getString(1),
                        cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),
                        cursor.getString(6)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return task;
    }
}


