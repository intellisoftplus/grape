package com.intellisoftplus.grape;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    SQLiteDatabase contactsDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        ListAdapter theAdapter;
        ArrayList<String> phones = new ArrayList<>();
        Button getChoice = (Button) findViewById(R.id.saveContacts);

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);

                    phones.add(name + ":\n");


                    while (pCur.moveToNext()) {
//                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        phones.add(phoneNo);


//                        Toast.makeText(ContactsActivity.this, "Name: " + name + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                    }

                    pCur.close();
                }
                theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, phones);
                // ListViews display data in a scrollable list
                final ListView theListView = (ListView) findViewById(R.id.listView);
                theListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

                // Tells the ListView what data to use
                theListView.setAdapter(theAdapter);

                getChoice.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String selected = "";
                        int cntChoice = theListView.getCount();

                        SparseBooleanArray sparseBooleanArray = theListView.getCheckedItemPositions();

                        for (int i = 0; i < cntChoice; i++) {
                            if (sparseBooleanArray.get(i)) {
                                selected += theListView.getItemAtPosition(i).toString();
                            }
                        }

//                        try {
//                            //opens current database or crete it
//                            //add a db errorHandler in case of db corruption
//                            contactsDB = contactsDB.openOrCreateDatabase("ContactsList",
//                                    MODE_PRIVATE, null);
//                            //execute an SQL statement
//                            contactsDB.execSQL("CREATE TABLE IF NOT EXISTS contacts " +
//                                    "(id integer primary key, name VARCHAR, email VARCHAR);");
//                            // The database on the file system
//                            File database = getApplicationContext().getDatabasePath("ContactList.db");
//
//                            // Check if the database exists
//                            if (database.exists()) {
//                                Toast.makeText(this, "Database Created", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(this, "Database Missing", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                        catch(Exception e){
//
//                            Log.e("CONTACTS ERROR", "Error Creating Database");
//                        }

                        Toast.makeText(ContactListActivity.this,"you have imported \n" + selected + "to your Contacts",Toast.LENGTH_LONG).show();

                        Intent intent=new Intent(view.getContext(),ContactsActivity.class);
                        startActivity(intent);
                    }
                });

            }
        }
    }
}
