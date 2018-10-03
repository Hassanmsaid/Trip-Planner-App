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
import com.example.lenovo.finalgp_test1.Adapters.CarAdapter;
import com.example.lenovo.finalgp_test1.R;
import com.example.lenovo.finalgp_test1.TripPlannerClasses.CarClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListOfCarsActivity extends AppCompatActivity {

    private String url;
    //private String url = "http://192.168.56.1/trip_planner/ret_hotels.php";

    private RecyclerView carL;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<CarClass> carList;
    private RecyclerView.Adapter adapter;

    private String city_name;
    private int max_car_price;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_list_of_cars );


        city_name = getIntent().getExtras().getString("city_name");
        max_car_price = getIntent().getExtras().getInt("max_car_price");
        //Toast.makeText(this, "max price: "+ max_car_price, Toast.LENGTH_SHORT).show();
        url= "http://suspense.atwebpages.com/api/ret_cars.php?cityname="+ city_name;
        carL = findViewById( R.id.car_list );
        carList = new ArrayList<>();
        adapter = new CarAdapter(getApplicationContext(), carList);
        linearLayoutManager = new LinearLayoutManager( this );
        linearLayoutManager.setInitialPrefetchItemCount( LinearLayoutManager.VERTICAL );
        dividerItemDecoration = new DividerItemDecoration( carL.getContext(), linearLayoutManager.getOrientation() );

        carL.setHasFixedSize( true );
        carL.setLayoutManager( linearLayoutManager );
        carL.addItemDecoration( dividerItemDecoration );
        carL.setAdapter( adapter );
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
                        CarClass carClass = new CarClass();
                        carClass.setCar_model( jsonObject.getString( "car model" ) );
                        carClass.setRent_price(jsonObject.getInt( "rental price" ) );
                        carClass.setCar_img(jsonObject.getString( "car img" ) );
                        carClass.setCar_id(jsonObject.getInt("car id"));
                        carClass.setCity_id(jsonObject.getInt("city id"));
                        carClass.setIs_rented(jsonObject.getString("is rented"));
                        if(jsonObject.getInt( "rental price" ) <= max_car_price && jsonObject.getString("is rented").equals("0")) {
                            carList.add(carClass);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                Toast.makeText( ListOfCarsActivity.this, carList.size() + " Cars", Toast.LENGTH_LONG ).show();
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
