package com.intellisoftplus.grape;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AppointmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        FragmentManager fManager = getSupportFragmentManager();
        fManager.beginTransaction()
            .add(R.id.appointmentContainer, AppointmentListFragment.newInstance())
            .commit();
    }
}
