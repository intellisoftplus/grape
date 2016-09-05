package com.intellisoftplus.grape.db.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.intellisoftplus.grape.db.contracts.CallContract;
import com.intellisoftplus.grape.db.contracts.TaskContract;
import com.intellisoftplus.grape.db.helpers.DatabaseHelper;

/**
 * Created by frank on 9/5/16.
 */
public class SingleTask extends AsyncTask<Object,Void,TaskContract> {
    private DatabaseHelper helper;
    private static String[] projection = {
            TaskContract.TaskEntry._ID,
            TaskContract.TaskEntry.COLUMN_TITLE, TaskContract.TaskEntry.COLUMN_DESCRIPTION,
            TaskContract.TaskEntry.COLUMN_STARTTIME, TaskContract.TaskEntry.COLUMN_ASSOCIATION,
            TaskContract.TaskEntry.COLUMN_ENDTIME
    };
    private String type;
    private String[] selectionArgs;
    private ContentValues values;
    private String selection = CallContract.CallEntry._ID + " = ?";
    public SingleTask(Context c, long id, String type, ContentValues values) {
        this.helper = new DatabaseHelper(c);
        this.selectionArgs = new String[]{id+""};
        this.type=type;
        this.values=values;
    }
    @Override
    protected TaskContract doInBackground(Object... objects) {
        TaskContract event = null;
        switch (type) {
            case "read":
                event = getEvent();
                break;
            case "update":
                event = updateEvent(values);
                break;
            case "delete":
                event = deleteEvent();
                break;
        }

        return event;
    }

    public TaskContract getEvent(){
        SQLiteDatabase db = helper.getReadableDatabase();
        TaskContract event = null;
        Cursor cursor = db.query(
                TaskContract.TaskEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            event = new TaskContract(
                    cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5),
                    cursor.getString(6)
            );
        }
        cursor.close();
        return event;
    }

    public TaskContract updateEvent(ContentValues values){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.update(
                TaskContract.TaskEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
        db.close();

        return null;
    }

    public TaskContract deleteEvent(){
        SQLiteDatabase db = helper.getReadableDatabase();
        db.delete(
                TaskContract.TaskEntry.TABLE_NAME,
                selection,
                selectionArgs
        );
        return null;
    }
}
