package com.intellisoftplus.grape;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class TaskActivity extends AppCompatActivity {

    SQLiteDatabase GrapeDB = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        GrapeDB = this.openOrCreateDatabase("NewTaskDB",
                MODE_PRIVATE, null);
        Cursor cursor = GrapeDB.rawQuery("SELECT * FROM tasks", null);

        // Get the index for the column name provided
        int idColumn = cursor.getColumnIndex("id");

        //move to the first row of results
        cursor.moveToFirst();
        String tasklist = "";

        // Verify that we have results
        if(cursor != null && (cursor.getCount() > 0)){

            do{
                // Get the results and store them in a String
                String id = cursor.getString(idColumn);

                tasklist = tasklist + id + "\n";

                // Keep getting results as long as they exist
            }while(cursor.moveToNext());

            Log.v("name", tasklist);

        } else {

            Toast.makeText(this, "No Results to Show", Toast.LENGTH_SHORT).show();

        }
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
