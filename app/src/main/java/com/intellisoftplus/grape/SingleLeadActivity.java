package com.intellisoftplus.grape;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SingleLeadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_lead);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.singleLeadContainer, new LeadInfoFragment())
                .commit();
    }
}
