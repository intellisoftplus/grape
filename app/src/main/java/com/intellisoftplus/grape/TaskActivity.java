package com.intellisoftplus.grape;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class TaskActivity extends AppCompatActivity {

    SQLiteDatabase GrapeDB = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
    }

    public void addTask(View view) {


        try {
            GrapeDB = this.openOrCreateDatabase("NewTaskDB",
                    MODE_PRIVATE, null);

            GrapeDB.execSQL("CREATE TABLE IF NOT EXISTS tasks " +
                    "(id integer primary key, title VARCHAR, " +
                    "description VARCHAR, association VARCHAR, " +
                    "startTime VARCHAR, endTime VARCHAR);");
        }
        catch (Exception e){
            Log.e("NEW TASK ERROR","Error creating Database");
        }

        Intent intent = new Intent(this,AddTaskActivity.class);
        startActivity(intent);
    }
}
