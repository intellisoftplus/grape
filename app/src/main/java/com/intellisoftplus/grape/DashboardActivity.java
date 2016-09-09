package com.intellisoftplus.grape;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity {
    private Toolbar toolbar;

    ActionBarDrawerToggle toggle;

    // User Session Manager Class
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Session class instance
        session = new UserSessionManager(getApplicationContext());

        if(session.checkLogin())
            finish();


        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // get name
        String name = user.get(UserSessionManager.KEY_NAME);
        // get email
        String email = user.get(UserSessionManager.KEY_EMAIL);

        TextView tvuser = (TextView) findViewById(R.id.tvUser);

        tvuser.setText(name + " " + email);

        Button appointments = (Button)findViewById(R.id.appointments);
        Button contacts = (Button)findViewById(R.id.contacts);
        Button leads = (Button)findViewById(R.id.leads);
        Button calls = (Button)findViewById(R.id.calls);
        Button task = (Button)findViewById(R.id.task);
        appointments.setOnClickListener(clickHandler);
        contacts.setOnClickListener(clickHandler);
        leads.setOnClickListener(clickHandler);
        calls.setOnClickListener(clickHandler);
        task.setOnClickListener(clickHandler);
        Button logout = (Button)findViewById(R.id.bLogout);
        logout.setOnClickListener(clickHandler);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggle.onOptionsItemSelected(item))
            return  true;
        return super.onOptionsItemSelected(item);
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
                case R.id.task:
                    changeActivity(TaskActivity.class);
                    break;
                case R.id.bLogout:
                    session.logoutUser();
                    LoginManager.getInstance().logOut();
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    public void changeActivity(Class classObj){
        Intent intent = new Intent(this, classObj);
        startActivity(intent);

    }
}
