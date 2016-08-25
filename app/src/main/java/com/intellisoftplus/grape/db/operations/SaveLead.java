package com.intellisoftplus.grape.db.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.intellisoftplus.grape.db.contracts.LeadContract.LeadEntry;
import com.intellisoftplus.grape.db.helpers.LeadsDBHelper;

/**
 * Created by User on 8/24/2016.
 */
public class SaveLead extends AsyncTask<Object,Void,Long> {

    private LeadsDBHelper helper;
    private String names;
    private String phone;
    private String email;
    private String website;
    private String status;
    private String source;
    private String industry;
    private String description;
    public SaveLead(Context context, String names,
                     String phone, String email,
                     String website, String status,
                     String source, String industry, String description) {
        this.helper = new LeadsDBHelper(context);
        this.names = names;
        this.description = description;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.status = status;
        this.source = source;
        this.industry = industry;
    }

    @Override
    protected Long doInBackground(Object... objects) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LeadEntry.COLUMN_NAMES, names);
        values.put(LeadEntry.COLUMN_DESCRIPTION, description);
        values.put(LeadEntry.COLUMN_PHONE, phone);
        values.put(LeadEntry.COLUMN_EMAIL, email);
        values.put(LeadEntry.COLUMN_WEBSITE, website);
        values.put(LeadEntry.COLUMN_STATUS, status);
        values.put(LeadEntry.COLUMN_SOURCE, source);
        values.put(LeadEntry.COLUMN_INDUSTRY, industry);



        long newRowId = db.insert(
                LeadEntry.TABLE_NAME,
                null,
                values);
        db.close();
        return newRowId;
    }
}
