package com.example.lenovo.finalgp_test1;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {
    EditText et_username, et_password, et_firstname, et_lastname, et_email;
    Button bt_register;
    String REGISTER_URL = "http://suspense.atwebpages.com/api/register.php";
    com.android.volley.RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        et_email = findViewById(R.id.emailET);
        et_firstname = findViewById(R.id.firstnameET);
        et_lastname = findViewById(R.id.lastnameET);
        et_password = findViewById(R.id.passwordET);
        et_username = findViewById(R.id.usernameET);
        bt_register = findViewById(R.id.RegisterBtn);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegister();
            }
        });
    }
    public void userRegister()
    {
        final String username,email,password,firstname,lastname;
        username = et_username.getText().toString().trim().toLowerCase();
        email= et_email.getText().toString().trim().toLowerCase();
        password = et_password.getText().toString();
        firstname = et_firstname.getText().toString().trim().toLowerCase();
        lastname = et_lastname.getText().toString().trim().toLowerCase();
        requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest( Request.Method.POST,
                REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String x = jsonObject.names().get(0).toString();

                            if (x.equals("success")) {
                                Toast.makeText(getApplicationContext(), "Welcome"  + firstname, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(RegisterActivity.this, NavigationDrawerActivity.class);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Failed !", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }){
            @Override
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("username", username);
                map.put("password", password);
                map.put("email",email);
                map.put("firstname",firstname);
                map.put("lastname",lastname);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
