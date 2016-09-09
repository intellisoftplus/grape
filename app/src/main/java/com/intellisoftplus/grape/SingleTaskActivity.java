package com.intellisoftplus.grape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SingleTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.singleTaskContainer, new TaskInfoFragment())
                .commit();
    }
}
