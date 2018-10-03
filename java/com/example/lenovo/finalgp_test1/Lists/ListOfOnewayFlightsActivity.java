package com.example.lenovo.finalgp_test1.Lists;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.finalgp_test1.Adapters.FlightAdapter;
import com.example.lenovo.finalgp_test1.R;
import com.example.lenovo.finalgp_test1.TripPlannerClasses.FlightClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListOfOnewayFlightsActivity extends AppCompatActivity {

    private String url;
    private RecyclerView flightL;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<FlightClass> flightList;
    private RecyclerView.Adapter adapter;
    public static String city_from , city_to;
    private String flight_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_oneway_flights);
        city_from = getIntent().getExtras().getString("city_from");
        city_to = getIntent().getExtras().getString("city_to");
        flight_date = getIntent().getExtras().getString("flight_date");
        //Toast.makeText(this, "max price: "+ max_car_price, Toast.LENGTH_SHORT).show();

        url= "http://suspense.atwebpages.com/api/ret_flights.php?departure_city=" + city_from +"&arrival_city=" +city_to + "&departure_date=" + flight_date;
        flightL = findViewById( R.id.flight_oneway_list);
        flightList = new ArrayList<>();
        adapter = new FlightAdapter(getApplicationContext(), flightList);
        linearLayoutManager = new LinearLayoutManager( this );
        linearLayoutManager.setInitialPrefetchItemCount( LinearLayoutManager.VERTICAL );
        dividerItemDecoration = new DividerItemDecoration( flightL.getContext(), linearLayoutManager.getOrientation() );

        flightL.setHasFixedSize( true );
        flightL.setLayoutManager( linearLayoutManager );
        flightL.addItemDecoration( dividerItemDecoration );
        flightL.setAdapter( adapter );
        getData();
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        FlightClass flightClass = new FlightClass(  );
                        flightClass.setFlight_id(jsonObject.getInt("flight id"));
                        flightClass.setFlight_num(jsonObject.getInt("flight number"));
                        flightClass.setFlying_from(city_from);
                        flightClass.setFlying_to(city_to);
                        flightClass.setDeparture(jsonObject.getString("departure time").substring(0,5));
                        flightClass.setArrival(jsonObject.getString("arrival time").substring(0,5));
                        flightClass.setFlight_date(jsonObject.getString("departure date"));
                        flightClass.setAirways(jsonObject.getString("airways"));
                        flightClass.setBusiness_seats(jsonObject.getInt("number of business seats"));
                        flightClass.setEconomy_seats(jsonObject.getInt("number of economy seats"));
                        flightClass.setBusiness_price(jsonObject.getInt("business price"));
                        flightClass.setEconomy_price(jsonObject.getInt("economy price"));
                        //flightClass.setDeparture(jsonObject.getString(""));
                        //flightClass.setArrival(jsonObject.getString(""));
                            flightList.add(flightClass);
                         //}
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }

                    }
                    Toast.makeText( ListOfOnewayFlightsActivity.this, flightList.size() + " Flights", Toast.LENGTH_LONG ).show();
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
