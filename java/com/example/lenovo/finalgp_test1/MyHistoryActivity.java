package com.example.lenovo.finalgp_test1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.finalgp_test1.Adapters.DayAdapter;
import com.example.lenovo.finalgp_test1.TripPlannerClasses.DayClass;
import com.example.lenovo.finalgp_test1.TripPlannerClasses.ToursClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyHistoryActivity extends AppCompatActivity {

    private String url, url2;
    List<ToursClass> tourList;
    List<DayClass> RecomendedList;
    List<ToursClass> toBeDisplayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_my_history);

        SharedPreferences shared = getSharedPreferences("userSharedpref", MODE_PRIVATE);
        int user_id = shared.getInt("userID", 5);

        url = "http://suspense.atwebpages.com/api/ret_userID.php?userid="+user_id;

        tourList = new ArrayList<ToursClass>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                tourList = new ArrayList<>();

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        ToursClass toursClass = new ToursClass();

                        toursClass.setTour_id(jsonObject.getInt("tour_id_fk"));

                        tourList.add(toursClass);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                secFunction();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }
    private void secFunction(){
        RecomendedList = new ArrayList<>();

        for (int i = 0;i <tourList.size();i++){
            int x =tourList.get(i).getTour_id();
            url2 = "http://suspense.atwebpages.com/api/ret_tour_res_by_id.php?tourid="+x;

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url2, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    toBeDisplayed =new ArrayList<>();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ToursClass toursClass = new ToursClass();

                            toursClass.setTour_name(jsonObject.getString("tour_name"));
                            toursClass.setTour_img(jsonObject.getString("tour_img"));
                            toursClass.setTour_id(jsonObject.getInt("tour_id"));
                            toursClass.setTour_price(jsonObject.getInt("tour_price"));
                            toursClass.setRating(jsonObject.getDouble("rating"));
                            toursClass.setCategory(jsonObject.getString("category"));

                            toBeDisplayed.add(toursClass);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    RecyclerView recyclerView = findViewById(R.id.RecViewRecomended);
                    DayClass dc = new DayClass("", toBeDisplayed);
                    RecomendedList.add(dc);

                    DayAdapter dayAdapter = new DayAdapter(MyHistoryActivity.this, RecomendedList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MyHistoryActivity.this, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(dayAdapter);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(MyHistoryActivity.this);
            requestQueue.add(jsonArrayRequest);
        }
    }
}
