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
import com.example.lenovo.finalgp_test1.Adapters.TourAdapter;
import com.example.lenovo.finalgp_test1.ItenaryActivity;
import com.example.lenovo.finalgp_test1.R;
import com.example.lenovo.finalgp_test1.Search.ToursActivity;
import com.example.lenovo.finalgp_test1.TripPlannerClasses.ToursClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListOfToursActivity extends AppCompatActivity {
    private String cityname;
    private int max_price, num_persons;
    private String url;
    //private String url = "http://192.168.56.1/trip_planner/ret_hotels.php";

    private RecyclerView tourL;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<ToursClass> tourList;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_list_of_tours );

        ItenaryActivity.isItienrary = false;
        cityname = getIntent().getExtras().getString("city_name");
        max_price = getIntent().getExtras().getInt("max_price");
        num_persons = getIntent().getExtras().getInt("num_persons");

        url = "http://suspense.atwebpages.com/api/ret_tours.php?cityname=" + cityname;
        tourL = findViewById( R.id.tours_rec );
        tourList = new ArrayList<ToursClass>();
        adapter = new TourAdapter(getApplicationContext(), tourList);
        linearLayoutManager = new LinearLayoutManager( this );
        linearLayoutManager.setInitialPrefetchItemCount( LinearLayoutManager.VERTICAL );
        dividerItemDecoration = new DividerItemDecoration( tourL.getContext(), linearLayoutManager.getOrientation() );

        tourL.setHasFixedSize( true );
        tourL.setLayoutManager( linearLayoutManager );
        tourL.addItemDecoration( dividerItemDecoration );
        tourL.setAdapter( adapter );
        getData();
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("hhhhhhhhhhh", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        ToursClass toursClass = new ToursClass();
                        toursClass.setTour_id(jsonObject.getInt( "tour id" ));
                        toursClass.setTour_name(jsonObject.getString( "tour name" ) );
                        toursClass.setTour_price(jsonObject.getInt( "tour price" ) );
                        toursClass.setLongitude(jsonObject.getString("tour long"));
                        toursClass.setLatitude(jsonObject.getString("tour lat"));
                        toursClass.setCity_name(jsonObject.getString("city name"));
                        toursClass.setTour_img(jsonObject.getString("tour img"));
                        toursClass.setRating(jsonObject.getDouble("tour rating"));
                        toursClass.setCategory(jsonObject.getString("category"));
                        if(jsonObject.getInt( "tour price" ) <= max_price) {
                            Log.i("price", String.valueOf(toursClass.getTour_price()));
                            tourList.add(toursClass);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                    Collections.sort(tourList, new Comparator<ToursClass>() {
                        @Override
                        public int compare(ToursClass toursClass, ToursClass t1) {
                            if (ToursActivity.sortBy.equals("Price")) {
                                if (ToursActivity.sortType.equals("Descending")) {
                                    return Integer.compare(t1.getTour_price(), toursClass.getTour_price());
                                }
                                else{
                                    return Integer.compare(toursClass.getTour_price(), t1.getTour_price());
                                }
                            }
                            else if(ToursActivity.sortBy.equals("Rating")){
                                if(ToursActivity.sortType.equals("Descending")){
                                    return Double.compare(t1.getRating(), toursClass.getRating());
                                }
                                else{
                                    return Double.compare(toursClass.getRating(), t1.getRating());
                                }
                            }
                            else if(ToursActivity.sortBy.equals("Name")){
                                if(ToursActivity.sortType.equals("Descending")){
                                    return String.valueOf(toursClass.getTour_name()).compareTo(t1.getTour_name());
                                }
                                else{
                                    return String.valueOf(toursClass.getTour_name()).compareTo(t1.getTour_name());
                                }
                            }
                            else
                                return 0;
                        }
                    });
                }
                Toast.makeText( ListOfToursActivity.this, tourList.size() + " Tours", Toast.LENGTH_LONG ).show();
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
