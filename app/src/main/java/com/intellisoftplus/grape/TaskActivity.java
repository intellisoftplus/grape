package com.intellisoftplus.grape;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskActivity extends AppCompatActivity {

    SQLiteDatabase GrapeDB = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        GrapeDB = this.openOrCreateDatabase("NewTaskDB",
                MODE_PRIVATE, null);

        Cursor cursor = GrapeDB.rawQuery("SELECT * FROM tasks", null);
        final Context context = this;
        ArrayAdapter<String> arrayAdapter;
        String tasklist = "";
        ListView lview;

//        int idColumn = cursor.getColumnIndex("id");
        int titleColumn = cursor.getColumnIndex("title");

        //move to the first row of results
        cursor.moveToFirst();

;

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
            List<String> myList = new ArrayList<String>(Arrays.asList(tasklist.split(",")));

            Log.v("name", String.valueOf(tasklist));
            lview = (ListView)findViewById(R.id.listTaskview);
            arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, myList);
            lview.setAdapter(arrayAdapter);

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
