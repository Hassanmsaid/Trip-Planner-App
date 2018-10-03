package com.example.lenovo.finalgp_test1.Reservations;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.finalgp_test1.Adapters.DayAdapter;
import com.example.lenovo.finalgp_test1.ItenaryActivity;
import com.example.lenovo.finalgp_test1.MapsActivity;
import com.example.lenovo.finalgp_test1.NavigationDrawerActivity;
import com.example.lenovo.finalgp_test1.R;
import com.example.lenovo.finalgp_test1.Search.ToursActivity;
import com.example.lenovo.finalgp_test1.TripPlannerClasses.DayClass;
import com.example.lenovo.finalgp_test1.TripPlannerClasses.ToursClass;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReserveTourActivity extends AppCompatActivity {

    public static String tourLong, tourLat, tourName;
    private int total_price, num_persons, tour_id;
    private String tour_name, tour_category, url, urlReserve;
    TextView tour_txt, total_txt;
    ImageView tour_img;
    ImageButton map;
    Button bookTour ;
    List<DayClass> RecomendedList = new ArrayList<>();
    private List<ToursClass> tourList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_tour);

        SharedPreferences shared = getSharedPreferences("userSharedpref", MODE_PRIVATE);
        int user_id = shared.getInt("userID", 5);

        tour_id = getIntent().getExtras().getInt("tour_id");
        tour_name = getIntent().getExtras().getString("tour_name");
        map= findViewById(R.id.locationBtntour);

        if(ToursActivity.num_persons == 0)
            num_persons = 1;
        else
            num_persons = ToursActivity.num_persons;
        Log.i("persons : ", ""+num_persons);
        tour_img = findViewById(R.id.tour_img1);
        Picasso.with(getApplication()).load(getIntent().getExtras().getString("tour_img")).into(this.tour_img);

        total_price = getIntent().getIntExtra("tour_price", 1);
        total_price *= num_persons;

        tour_txt = findViewById(R.id.tour_reserve_name);
        tour_txt.setText(tour_name);

        total_txt = findViewById(R.id.tour_reserve_price);
        total_txt.setText("$ "+ String.valueOf(total_price));

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReserveTourActivity.this , MapsActivity.class);
                startActivity(intent);
            }
        });

        bookTour = findViewById(R.id.ReserveTourBtn);
        if(ItenaryActivity.isItienrary == true){
            bookTour.setVisibility(View.INVISIBLE);
        }
        bookTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reserveTour();
            }
        });

        tour_category = getIntent().getExtras().getString("category");
        url = "http://suspense.atwebpages.com/api/ret_tours.php?cityname="+getIntent().getExtras().getString("city_name");
        //url = "http://suspense.atwebpages.com/api/tours_by_pref.php?category="+tour_category;
        urlReserve = "http://suspense.atwebpages.com/api/tour_reserve.php?user_id=" + user_id +
                "&tour_id=" +  tour_id + "&num_persons=" + num_persons + "&tour_total=" + total_price;

        tourList = new ArrayList<ToursClass>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                tourList = new ArrayList<>();

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        ToursClass toursClass = new ToursClass();

                        toursClass.setTour_name(jsonObject.getString("tour name"));
                        toursClass.setTour_img(jsonObject.getString("tour img"));
                        toursClass.setTour_id(jsonObject.getInt("tour id"));
                        toursClass.setCity_name(jsonObject.getString("city name"));
                        toursClass.setTour_price(jsonObject.getInt("tour price"));
                        toursClass.setRating(jsonObject.getDouble("tour rating"));
                        toursClass.setCategory(jsonObject.getString("category"));
                        toursClass.setLongitude(jsonObject.getString("tour long"));
                        toursClass.setLatitude(jsonObject.getString("tour lat"));
                        //toursClass.setClose(Integer.parseInt(jsonObject.getString("closes_at").substring(0, 2)));
                        //toursClass.setOpen(Integer.parseInt(jsonObject.getString("opens_at").substring(0, 2)));

                        if(!toursClass.getTour_name().equals(tour_name) && toursClass.getCategory().equals(tour_category))
                            tourList.add(toursClass);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                RecomendedList = new ArrayList<>();
                RecyclerView recyclerView = findViewById(R.id.RecViewRecomended);
                DayClass dc = new DayClass("Recomended", tourList);
                RecomendedList.add(dc);
                recyclerView.setFocusable(false);

                DayAdapter dayAdapter = new DayAdapter(ReserveTourActivity.this, RecomendedList);
                recyclerView.setLayoutManager(new LinearLayoutManager(ReserveTourActivity.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(dayAdapter);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void reserveTour(){
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, urlReserve, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("done")) {
                    Toast.makeText(ReserveTourActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ReserveTourActivity.this , NavigationDrawerActivity.class);
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
