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
import com.example.lenovo.finalgp_test1.Adapters.HotelAdapter;
import com.example.lenovo.finalgp_test1.NavigationDrawerActivity;
import com.example.lenovo.finalgp_test1.R;
import com.example.lenovo.finalgp_test1.Search.HotelActivity;
import com.example.lenovo.finalgp_test1.TripPlannerClasses.HotelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListOfHotelsActivity extends AppCompatActivity {

    private String url, city_name, sortBy, sortType;
    private int max_single_price, minStars;
    private double minRating;


    private RecyclerView hotelL;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<HotelClass> hotelList;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_list_of_hotels );

        city_name = getIntent().getExtras().getString("city_name");
        minRating = HotelActivity.min_rating;
        max_single_price = getIntent().getExtras().getInt("max_single_price");
        minStars = HotelActivity.min_stars;

        //sortBy = sortHotelSpinner.getSelectedItem().toString();
        //sortBy = String.valueOf(sortHotelSpinner.getSelectedItem());
        //sortType = sortHotelTypeSpinner.getSelectedItem().toString();
        //sortType = String.valueOf(sortHotelTypeSpinner.getSelectedItem());
        //url = "http://10.0.3.2/trip_planner/show_hotels_by_city_name.php?cityname=" + city_name;
        //url = "http://suspense.atwebpages.com/api/show_hotels_by_city_name.php?cityname=" + city_name;
        url = NavigationDrawerActivity.localUrl + "show_hotels_by_city_name.php?cityname=" + city_name;
        hotelL = findViewById( R.id.main_list );
        hotelList = new ArrayList<>();
        adapter = new HotelAdapter( getApplicationContext(), hotelList );
        linearLayoutManager = new LinearLayoutManager( this );
        linearLayoutManager.setInitialPrefetchItemCount( LinearLayoutManager.VERTICAL );
        dividerItemDecoration = new DividerItemDecoration( hotelL.getContext(), linearLayoutManager.getOrientation() );

        hotelL.setHasFixedSize( true );
        hotelL.setLayoutManager( linearLayoutManager );
        hotelL.addItemDecoration( dividerItemDecoration );
        hotelL.setAdapter( adapter );
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
                        HotelClass hotelClass = new HotelClass();
                        hotelClass.setHotel_id(jsonObject.getInt("hotel id"));
                        hotelClass.setHotel_name( jsonObject.getString( "hotel name" ) );
                        hotelClass.setHotel_stars( jsonObject.getInt( "hotel stars" ) );
                        hotelClass.setHotel_longitude(jsonObject.getString("hotel long"));
                        hotelClass.setHotel_latitude(jsonObject.getString("hotel lat"));
                        //hotelClass.setHotel_rating(jsonObject.getDouble("hotel rating"));
                        hotelClass.setHotel_rating(jsonObject.getDouble("hotel rating"));
                        hotelClass.setNum_rooms(jsonObject.getInt("number of rooms"));
                        hotelClass.setSgl_price(jsonObject.getDouble("single room price"));
                        hotelClass.setDbl_price(jsonObject.getDouble("double room price"));
                        hotelClass.setHotel_img(jsonObject.getString("hotel image"));

                        if(hotelClass.getHotel_rating() >= minRating && hotelClass.getSgl_price() <= max_single_price && hotelClass.getHotel_stars() >= minStars){
                            hotelList.add(hotelClass);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }


                    Collections.sort(hotelList, new Comparator<HotelClass>() {
                        @Override
                        public int compare(HotelClass hotelClass, HotelClass t1) {
                            if (HotelActivity.sortBy.equals("Stars")) {
                                if (HotelActivity.sortType.equals("Descending")) {
                                    return Integer.valueOf(t1.getHotel_stars()).compareTo(hotelClass.getHotel_stars());
                                }
                                else{
                                    return Integer.valueOf(hotelClass.getHotel_stars()).compareTo(t1.getHotel_stars());
                                }
                            }
                            else if(HotelActivity.sortBy.equals("Rating")){
                                if(HotelActivity.sortType.equals("Descending")){
                                    return Double.valueOf(t1.getHotel_rating()).compareTo(hotelClass.getHotel_rating());
                                }
                                else{
                                    return Double.valueOf(hotelClass.getHotel_rating()).compareTo(t1.getHotel_rating());
                                }
                            }
                            else if(HotelActivity.sortBy.equals("Price")){
                                if(HotelActivity.sortType.equals("Descending")){
                                    return Double.valueOf(t1.getSgl_price()).compareTo(hotelClass.getSgl_price());
                                }
                                else{
                                    return Double.valueOf(hotelClass.getSgl_price()).compareTo(t1.getSgl_price());
                                }
                            }
                            else if(HotelActivity.sortBy.equals("Name")){
                                if(HotelActivity.sortType.equals("Descending")){
                                    return String.valueOf(t1.getHotel_name()).compareTo(hotelClass.getHotel_name());
                                }
                                else{
                                    return String.valueOf(hotelClass.getHotel_name()).compareTo(t1.getHotel_name());
                                }
                            }
                            else
                                return 0;
                        }
                    });
                }
                Toast.makeText( ListOfHotelsActivity.this, hotelList.size() + " Hotels", Toast.LENGTH_LONG ).show();
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
