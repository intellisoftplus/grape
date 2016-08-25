package com.intellisoftplus.grape;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.tamir7.contacts.Contact;
import com.github.tamir7.contacts.Contacts;
import com.github.tamir7.contacts.PhoneNumber;
import com.github.tamir7.contacts.Query;

import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Contacts.initialize(this);
    }

    public void getContacts(View view) {
        Query q = Contacts.getQuery();
        q.include(Contact.Field.PhoneNumber, Contact.Field.DisplayName);
        List<Contact> contacts = q.find();
        for (Contact c : contacts) {
            String names = c.getDisplayName();
            Log.v("Contact", c.getDisplayName());
            for (PhoneNumber p : c.getPhoneNumbers()) {
//                Log.v("Contact1", p.getNumber());

            }
        }

    }
}
