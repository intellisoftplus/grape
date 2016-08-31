package com.intellisoftplus.grape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SingleCallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_call);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.singleCallContainer, new CallInfoFragment())
                .commit();
    }
}
