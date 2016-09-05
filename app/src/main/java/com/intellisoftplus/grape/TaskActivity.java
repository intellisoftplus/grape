package com.intellisoftplus.grape;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);


        FragmentManager fManager = getSupportFragmentManager();
        fManager.beginTransaction()
                .add(R.id.taskContainer, new TaskListFragment())
                .commit();
    }
}
