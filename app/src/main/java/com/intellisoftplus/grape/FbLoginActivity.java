package com.intellisoftplus.grape;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FbLoginActivity extends AppCompatActivity {
    LoginButton loginButton;
    CallbackManager callbackManager;
    String TAG="FbLoginActivity";

    // User Session Manager Class
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_fb_login);

        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

//        getHashKey(); // run this to create facebook login hash key

//Facebook login class starts here
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
//                Log.e(TAG,"onSuccess");

                session.createUserLoginSession("facebook",
                        "zuck berg");

                Intent intent = new Intent(FbLoginActivity.this, DashboardActivity.class);
                FbLoginActivity.this.startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {
                // App code
                Log.e(TAG,"onCancel");
            }
            @Override
            public void onError(FacebookException error) {
                // App code
                Log.e(TAG,"onError");
            }

        });

        //Facebook login class ends here here



        //    Start Username Password Login

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registerIntent = new Intent(FbLoginActivity.this, RegisterActivity.class);
                FbLoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                String user_id= jsonResponse.getString("user_id");
                                String name = jsonResponse.getString("name");
                                int age = jsonResponse.getInt("age");
                                String username = jsonResponse.getString("username");

                                session.createUserLoginSession(username,
                                        name);

                                Intent intent = new Intent(FbLoginActivity.this, DashboardActivity.class);
//                                intent.putExtra("user_id", user_id);
//                                intent.putExtra("name", name);
//                                intent.putExtra("age", age);
//                                intent.putExtra("username", username);

                                FbLoginActivity.this.startActivity(intent);
                                finish();


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(FbLoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(FbLoginActivity.this);
                queue.add(loginRequest);


            }
        });
    }
//    End User pass login


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //This class creates a hash key for facebook login
    private void getHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.intellisoftplus.grape",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("name not found", e.toString());

        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());

        }
    }


    //This class that creates a hash key for facebook login ends here


    public void skipLogin(View v) {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

}
