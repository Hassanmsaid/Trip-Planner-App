package com.example.lenovo.finalgp_test1.Reservations;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.finalgp_test1.NavigationDrawerActivity;
import com.example.lenovo.finalgp_test1.R;
import com.example.lenovo.finalgp_test1.Search.CarActivity;
import com.squareup.picasso.Picasso;

public class ReserveCarActivity extends AppCompatActivity {

    private int total_price, num_days, car_id;
    private String car_model, url;
    TextView car_txt, total_txt;
    ImageView car_img;
    Button bookCar;
    com.android.volley.RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_reserve_car );

        SharedPreferences shared = getSharedPreferences("userSharedpref", MODE_PRIVATE);
        int user_id = shared.getInt("userID", 5);

        car_model = getIntent().getExtras().getString("car_model");
        car_id = getIntent().getExtras().getInt("car_id");
        requestQueue = Volley.newRequestQueue(this);
        //url = "http://10.0.3.2/trip_planner/car_reserve.php?car_id=1&user_id=2&num_days=4&car_total=2222";

        num_days = CarActivity.num_days;
        if(num_days==0)
            num_days = 1;

        //Toast.makeText(this, ""+ getIntent().getExtras().getString("car_img"), Toast.LENGTH_SHORT).show();
        car_img = findViewById(R.id.car_img1);
        Picasso.with(getApplication()).load(getIntent().getExtras().getString("car_img")).into(this.car_img);

        total_price = getIntent().getIntExtra("car_price", 1);
        total_price *= num_days;
        //url = "http://10.0.3.2/trip_planner/car_reserve.php?car_id=" + car_id + "&user_id=" + user_id + "&num_days=" + num_days + "&car_total=" + total_price;
        url = "http://suspense.atwebpages.com/api/car_reserve.php?car_id=" + car_id + "&user_id=" + user_id + "&num_days=" + num_days + "&car_total=" + total_price;
        car_txt = findViewById(R.id.car_type);
        car_txt.setText(car_model);

        total_txt = findViewById(R.id.car_total);
        total_txt.setText("$ " + String.valueOf(total_price));

        bookCar = findViewById(R.id.ReserveCarBtn);
        bookCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reserve();
            }
        });
    }
    public void reserve(){

        StringRequest stringRequest = new StringRequest( Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                    Log.d("myreponse", response);
                    if (response.equals("done")) {
                        Toast.makeText(ReserveCarActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ReserveCarActivity.this , NavigationDrawerActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG).show();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}
