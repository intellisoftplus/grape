package com.intellisoftplus.grape;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CallsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calls);


        FragmentManager fManager = getSupportFragmentManager();
        fManager.beginTransaction()
                .add(R.id.callContainer, new CallListFragment())
                .commit();
    }
}
