package com.intellisoftplus.grape;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddTaskActivity extends AppCompatActivity{

    SQLiteDatabase GrapeDB = null;

    EditText title,description,association, startTime, endTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        title = (EditText)findViewById(R.id.editTittle);
        description = (EditText)findViewById(R.id.editDescription);
        association = (EditText)findViewById(R.id.editAssociation);
        startTime = (EditText)findViewById(R.id.editStartTime);
        endTime = (EditText)findViewById(R.id.editEndTime);
    }

    public void saveTask(View view) {

        title = (EditText)findViewById(R.id.editTittle);
        description = (EditText)findViewById(R.id.editDescription);
        association = (EditText)findViewById(R.id.editAssociation);
        startTime = (EditText)findViewById(R.id.editStartTime);
        endTime = (EditText)findViewById(R.id.editEndTime);

        if (title.getText().toString().equals("")) {
            title.setError("Please fill in the title");
            return;
        }
        if (description.getText().toString().equals("")) {
            description.setError("Please fill in the description");
            return;
        }
        if (association.getText().toString().equals("")) {
            association.setError("Please fill in the association");
            return;
        }
        if (startTime.getText().toString().equals("")) {
            startTime.setError("Please fill in the time");
            return;
        }
        if (endTime.getText().toString().equals("")) {
            endTime.setError("Please fill in the time");git
            return;
        }

        String taskTitle = title.getText().toString();
        String taskDescription = description.getText().toString();
        String taskAssociation = association.getText().toString();
        String taskStartTime = startTime.getText().toString();
        String taskEndTime = endTime.getText().toString();

            try {
                GrapeDB = this.openOrCreateDatabase("NewTaskDB",
                        MODE_PRIVATE, null);

                GrapeDB.execSQL("CREATE TABLE IF NOT EXISTS tasks " +
                        "(id integer primary key, title VARCHAR, " +
                        "description VARCHAR, association VARCHAR, " +
                        "startTime VARCHAR, endTime VARCHAR);");

                GrapeDB.execSQL("INSERT INTO tasks(title, description, association, startTime, endTime) " +
                        "VALUES('" + taskTitle +"', '" + taskDescription + "','" + taskAssociation + "','" + taskStartTime + "','" + taskEndTime + "');");

                Log.v("title", taskTitle+" "+taskDescription);
            }
            catch (Exception e){
                Log.e("NEW TASK ERROR","Error creating Database");
            }


//        GrapeDB.execSQL("INSERT INTO tasks(title, description, association, startTime, endTime) " +
//                "VALUES('" + taskTitle +"', '" + taskDescription + "','" + taskAssociation + "','" + taskStartTime + "','" + taskEndTime + "');");

        Log.v("title", taskTitle+" "+taskDescription);

        Intent intent = new Intent(this,TaskActivity.class);
        startActivity(intent);
    }
}
