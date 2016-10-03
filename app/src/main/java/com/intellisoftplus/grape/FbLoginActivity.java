package com.intellisoftplus.grape;

import android.app.ProgressDialog;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

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


        FacebookSdk.sdkInitialize(this.getApplicationContext());
        LoginManager.getInstance().logOut();

//        getHashKey(); // run this to create facebook login hash key

//Facebook login class starts here


        loginButton = (LoginButton) findViewById(R.id.login_button);
        final ProgressBar login_progress_bar = (ProgressBar) findViewById(R.id.login_progress_bar);

        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));

        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
//                Log.e(TAG,"onSuccess");
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();

                if (accessToken != null) {
                    loginButton.setVisibility(View.GONE);
                    login_progress_bar.setVisibility(View.VISIBLE);

                }
                // Facebook Email address
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.v("Dashboard2Activity", response.toString());

                                try {
                                    String fname = object.getString("first_name");
                                    String lname = object.getString("last_name");
                                    String id = object.getString("id");
                                    String Email = object.getString("email");
                                    String gender = object.getString("gender");

                                    session.createUserLoginSession(fname,
                                            Email);

                                    Intent intent = new Intent(FbLoginActivity.this, Dashboard2Activity.class);
                                    FbLoginActivity.this.startActivity(intent);
                                    finish();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                // App code
//                Log.e(TAG,"onCancel");
                LoginManager.getInstance().logOut();
            }
            @Override
            public void onError(FacebookException error) {
                // App code
//                Log.e(TAG,"onError");
                LoginManager.getInstance().logOut();
            }

        });

        //Facebook login class ends here here



        //    Start Username Password Login

        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registerIntent = new Intent(FbLoginActivity.this, RegisterActivity.class);
                FbLoginActivity.this.startActivity(registerIntent);
                finish();
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                if(etEmail.getText().toString().equals("")){
                    etEmail.setError("Please fill in the email");
                    return;
                }

                if(etPassword.getText().toString().equals("")){
                    etPassword.setError("A password is required");
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                String user_id= jsonResponse.getString("user_id");
                                String fname = jsonResponse.getString("fname");
                                String lname = jsonResponse.getString("lname");
                                String email = jsonResponse.getString("email");
                                String gender = jsonResponse.getString("gender");

                                session.createUserLoginSession(fname,
                                        email);

                                Intent intent = new Intent(FbLoginActivity.this, Dashboard2Activity.class);
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

                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
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


}
