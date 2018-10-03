package com.example.lenovo.finalgp_test1.Reservations;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.finalgp_test1.Lists.ListOfOnewayFlightsActivity;
import com.example.lenovo.finalgp_test1.NavigationDrawerActivity;
import com.example.lenovo.finalgp_test1.R;

public class ReserveFlightOneWayActivity extends AppCompatActivity {

    private String flight_date, seat_class, flight_from, flight_to, flight_dept, flight_arrival, flight_airways, url;
    private int eco_price, bus_price, flight_id;

    TextView flight_date_txt, from_txt, to_txt, dept_txt, arrival_txt, airways_txt, economy_txt, business_txt, passengers_txt;
    Button bookFlight;
    ImageButton incPassengers, decPassengers;
    int passCount, user_id;
    Spinner classSpinner;


    com.android.volley.RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_flight_one_way);

        SharedPreferences shared = getSharedPreferences("userSharedpref", MODE_PRIVATE);
        user_id = shared.getInt("userID", 5);

        classSpinner = findViewById(R.id.seat_reserve_flight);
        if(classSpinner.getSelectedItem().toString().equals("Economy"))
            seat_class = "economy";
        else
            seat_class = "business";

        flight_id = getIntent().getExtras().getInt("flight_id");
        incPassengers = findViewById(R.id.IncreasePassengersBtn);
        decPassengers = findViewById(R.id.DecreasePassengersBtn);
        flight_from = getIntent().getExtras().getString("city_from");
        flight_to = getIntent().getExtras().getString("city_to");
        flight_date = getIntent().getExtras().getString("flight_date");
        flight_airways = getIntent().getExtras().getString("airways");
        //  flight_bus = getIntent().getIntExtra("business_price", 1);
        //  flight_eco  = getIntent().getIntExtra("economy_price", 1);

        requestQueue = Volley.newRequestQueue(this);
        url = "http://10.0.3.2/trip_planner/ret_flight.php?flight_id=1&user_id=2";


        flight_date_txt = findViewById(R.id.flightDateTV_oneway);
        flight_date_txt.setText(flight_date + "  " + getIntent().getExtras().getString("dep_time"));

        from_txt = findViewById(R.id.fromcity_oneway);
        from_txt.setText(ListOfOnewayFlightsActivity.city_from);

        to_txt = findViewById(R.id.tocity_oneway);
        to_txt.setText(ListOfOnewayFlightsActivity.city_to);

        airways_txt = findViewById(R.id.showAirways_onewayTV);
        airways_txt.setText(flight_airways);

        economy_txt = findViewById(R.id.Priceeconomy_onewayTV);
        eco_price = getIntent().getExtras().getInt("economy_price");
        economy_txt.setText("$ " + String.valueOf(eco_price));

        business_txt = findViewById(R.id.Pricebusiness_onewayTV);
        bus_price = getIntent().getExtras().getInt("business_price");
        business_txt.setText("$ " + String.valueOf(bus_price));

        passengers_txt = findViewById(R.id.number_Passengers_1_Tv);
        passCount = Integer.parseInt(passengers_txt.getText().toString());

       /* dept_txt = findViewById(R.id.DeptDateTV_oneway);
        dept_txt.setText( flight_dept );

        arrival_txt = findViewById(R.id.ArrivalDateTV_oneway);
        arrival_txt.setText( flight_arrival );*/

        bookFlight = findViewById(R.id.ReserveFlight_onewayBtn);
        bookFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { reserve();
            }
        });
        incPassengers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passCount++;
                passengers_txt.setText(String.valueOf(passCount));
            }
        });
        decPassengers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passCount > 1) {
                    passCount--;
                    passengers_txt.setText(String.valueOf(passCount));
                }
            }
        });
    }

    public void reserve() {

        url = "http://suspense.atwebpages.com/api/flight_reserve.php?user_id=" + user_id + "&flight_id=" + flight_id + "&num_passengers=" + passCount + "&seat_type=" + seat_class;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("done")) {
                    Toast.makeText(ReserveFlightOneWayActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ReserveFlightOneWayActivity.this, NavigationDrawerActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ReserveFlightOneWayActivity.this);
        requestQueue.add(stringRequest);
    }
}
