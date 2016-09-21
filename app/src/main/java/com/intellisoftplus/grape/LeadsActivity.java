package com.intellisoftplus.grape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import static com.intellisoftplus.grape.R.attr.title;


public class LeadsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leads);


        FragmentManager fManager = getSupportFragmentManager();
        fManager.beginTransaction()
                .add(R.id.leadsContainer, new LeadsListFragment())
                .commit();



    }
}
