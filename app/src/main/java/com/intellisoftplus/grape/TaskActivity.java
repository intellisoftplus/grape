package com.intellisoftplus.grape;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
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

        SimpleCursorAdapter dataAdapter;


//        ListAdapter theAdapter;
//        String tasklist = new String();

        // Get the index for the column name provided
        int idColumn = cursor.getColumnIndex("id");
        int titleColumn = cursor.getColumnIndex("title");

        //move to the first row of results
        cursor.moveToFirst();
        String tasklist = "";

        // Verify that we have results
        if(cursor != null && (cursor.getCount() > 0)){

            do{
                // Get the results and store them in a String
//                String id = cursor.getString(idColumn);
                String title = cursor.getString(titleColumn);

                tasklist = tasklist + title + "\n";

                // Keep getting results as long as they exist
            }while(cursor.moveToNext());
            cursor.close();

            Log.v("name", tasklist);


//            theAdapter = new SimpleAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, tasklist);
//            final ListView theListView = (ListView)findViewById(R.id.taskview);
//            theListView.setAdapter(theAdapter);


        }
        else {

            Toast.makeText(this, "No Results to Show", Toast.LENGTH_SHORT).show();

        }
    }

    public void addTask(View view) {

        Intent intent = new Intent(this,AddTaskActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {

        GrapeDB.close();

        super.onDestroy();
    }
}
