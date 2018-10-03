package com.example.lenovo.finalgp_test1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    String LOGIN_URL = "http://suspense.atwebpages.com/api/login.php";

    EditText et_username;
    EditText et_password;
    Button signinBtn;
    Button signupBtn;
    String username,password;
    com.android.volley.RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        if(SharedPrefManager.getmInstance(getApplicationContext()).isLoggedIn())
        {
            Intent i = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
            startActivity(i);
            finish();
        }
        et_username = findViewById(R.id.email);
        et_password = findViewById(R.id.password);
        requestQueue = Volley.newRequestQueue(this);
        final SharedPreferences pref = getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME,MODE_PRIVATE);

        signinBtn = findViewById( R.id.loginBtn );
        signinBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                userLogin();
            }
        } );


        signupBtn = (Button) findViewById( R.id.signupBtn );
        signupBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent( LoginActivity.this , RegisterActivity.class );
                startActivity( intent );
            }
        } );

    }
    private void userLogin() {
        username  = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest( Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String x = jsonObject.names().get(0).toString();
                    if (!x.equals("fail")) {
                        SharedPrefManager.getmInstance(getApplicationContext()).
                                user_Login(jsonObject.getInt("user_id")
                                        ,jsonObject.getString("username")
                                        ,jsonObject.getString("email")

                                );
                        Toast.makeText(getApplicationContext(), "Welcome back!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent( LoginActivity.this , NavigationDrawerActivity.class );
                        startActivity( intent );
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed! Try again", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("username", username);
                map.put("password", password);
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }
}
