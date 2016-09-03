package com.intellisoftplus.grape;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {


    SQLiteDatabase GrapeDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        GrapeDB = this.openOrCreateDatabase("NewTaskDB",
                MODE_PRIVATE, null);

        Cursor cur = GrapeDB.rawQuery("SELECT * FROM Contacts", null);


        ListAdapter theAdapter;
        ArrayList<String> phones = new ArrayList<>();
        ContentResolver cr = getContentResolver();
//        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
//                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex("id"));
                String name = cur.getString(cur.getColumnIndex("name"));
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);

                    phones.add(name + ":\n");
                }
                theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, phones);
                // ListViews display data in a scrollable list
                ListView theListView = (ListView) findViewById(R.id.theListView);
                // Tells the ListView what data to use
                theListView.setAdapter(theAdapter);
            }
        }
    }

    public void getContacts (View view){
        Intent intent = new Intent(this, ContactListActivity.class);
        startActivity(intent);
    }
}
