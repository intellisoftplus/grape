package com.intellisoftplus.grape;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.intellisoftplus.grape.R.id.radio_male;

public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        final EditText etFName = (EditText) findViewById(R.id.etFName);
        final EditText etLName = (EditText) findViewById(R.id.etLName);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etCPassword = (EditText) findViewById(R.id.etCPassword);

        final RadioGroup gender2 = (RadioGroup) findViewById(R.id.rdGender);

        final Button bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fname = etFName.getText().toString();
                final String lname = etLName.getText().toString();
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();
                final String cpassword = etCPassword.getText().toString();

                int selectedId = gender2.getCheckedRadioButtonId();

                RadioButton genderRd = (RadioButton) findViewById(selectedId);
                final String gender = genderRd.getText().toString();

                if(etFName.getText().toString().equals("")){
                    etFName.setError("Please fill in your first name");
                    return;
                }
                if(etLName.getText().toString().equals("")){
                    etLName.setError("Please fill in your last name");
                    return;
                }
                if(etEmail.getText().toString().equals("")){
                    etEmail.setError("Please fill in the email");
                    return;
                }
                if(etPassword.getText().toString().equals("")){
                    etPassword.setError("A password is required");
                    return;
                }
                if(etCPassword.getText().toString().equals("")){
                    etCPassword.setError("Confirm your password");
                    return;
                }



                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success){
                                Intent intent = new Intent(RegisterActivity.this, FbLoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                                finish();
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                };

                RegisterRequest registerRequest = new RegisterRequest(fname,lname, email, password, gender, responseListener );
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }

    });

    }
}
