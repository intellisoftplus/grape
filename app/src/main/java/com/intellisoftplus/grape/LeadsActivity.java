package com.intellisoftplus.grape;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;


public class LeadsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leads);

        Button createLead = (Button)findViewById(R.id.addLeadBtn);
        //unused will activate later
        createLead.setOnClickListener(clickHandler);

    }

    View.OnClickListener clickHandler  = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.addLeadBtn:
                    //bring up fragment
                break;

            }


        }
    };
}
