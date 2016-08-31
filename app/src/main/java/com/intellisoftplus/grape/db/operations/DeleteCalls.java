package com.intellisoftplus.grape.db.operations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.intellisoftplus.grape.db.contracts.CallContract.CallEntry;
import com.intellisoftplus.grape.db.helpers.CallDBHelper;

/**
 * Created by cndet on 31/08/2016.
 */
public class DeleteCalls extends AsyncTask<Object,Void,Void> {
    private CallDBHelper helper;

    public DeleteCalls(Context c){
        this.helper =new CallDBHelper(c);
    }

    @Override
    protected Void doInBackground(Object... objects) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(CallEntry.TABLE_NAME,null,null);
        db.execSQL("delete * from "+ CallEntry.TABLE_NAME);
        db.execSQL("TRUNCATE table" + CallEntry.TABLE_NAME);
        db.close();
        return null;
    }
}
