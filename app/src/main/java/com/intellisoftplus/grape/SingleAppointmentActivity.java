package com.intellisoftplus.grape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SingleAppointmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_appointment);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.singleAppointmentContainer, new AppointmentInfoFragment())
                .commit();
    }
}
