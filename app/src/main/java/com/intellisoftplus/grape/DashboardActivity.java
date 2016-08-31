package com.intellisoftplus.grape;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Button appointments = (Button)findViewById(R.id.appointments);
        Button contacts = (Button)findViewById(R.id.contacts);
        Button leads = (Button)findViewById(R.id.leads);
        Button calls = (Button)findViewById(R.id.calls);
        appointments.setOnClickListener(clickHandler);
        contacts.setOnClickListener(clickHandler);
        leads.setOnClickListener(clickHandler);
        calls.setOnClickListener(clickHandler);
    }

    View.OnClickListener clickHandler = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.appointments:
                    changeActivity(AppointmentsActivity.class);
                    break;
                case R.id.contacts:
                    changeActivity(ContactsActivity.class);
                    break;
                case R.id.leads:
                    changeActivity(LeadsActivity.class);
                    break;
                case R.id.calls:
                    changeActivity(CallsActivity.class);
                    break;
            }
        }
    };

    public void changeActivity(Class classObj){
        Intent intent = new Intent(this, classObj);
        startActivity(intent);

    }
}
