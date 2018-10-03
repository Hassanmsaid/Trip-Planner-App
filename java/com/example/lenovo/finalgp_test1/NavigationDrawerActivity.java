package com.example.lenovo.finalgp_test1;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.finalgp_test1.Adapters.HotelAdapter;
import com.example.lenovo.finalgp_test1.Search.CarActivity;
import com.example.lenovo.finalgp_test1.Search.FlightActivity;
import com.example.lenovo.finalgp_test1.Search.HotelActivity;
import com.example.lenovo.finalgp_test1.Search.ToursActivity;
import com.example.lenovo.finalgp_test1.TripPlannerClasses.HotelClass;
import com.example.lenovo.finalgp_test1.TripPlannerClasses.ToursClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    public static String localUrl;
    private String url, url2;

    private RecyclerView hotelL;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<HotelClass> hotelList;
    private RecyclerView.Adapter adapter;

    private RecyclerView tourL;
    private LinearLayoutManager linearLayoutManager2;
    private DividerItemDecoration dividerItemDecoration2;
    private List<ToursClass> tourList;
    private RecyclerView.Adapter adapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_navigation_drawer );

        localUrl = "http://192.168.1.108/trip_planner/";
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle("Trip Planner");
        ImageButton hotelimg  = findViewById( R.id.HotelimageButton );
        ImageButton flightimg = findViewById( R.id.FlightimageButton );
        ImageButton carsimg   = findViewById( R.id.CarimageButton );
        ImageButton toursimg  = findViewById( R.id.ToursimageButton );
        Button generateIT     = findViewById( R.id.GenerateItineraryBtn );

        //url = "http://suspense.atwebpages.com/api/show_hotels_by_city_name.php?cityname=" + "London";
        //url = "http://192.168.1.102/trip_planner/show_hotels_by_city_name.php?cityname=London";
        url = localUrl + "all_hotels.php";
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
        hotelL.setFocusable(false);

        getHotels();

        hotelimg.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent( NavigationDrawerActivity.this , HotelActivity.class );
                startActivity( intent );
            }


        } );

        flightimg.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( NavigationDrawerActivity.this , FlightActivity.class );
                startActivity( intent );
            }
        } );

        carsimg.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( NavigationDrawerActivity.this , CarActivity.class );
                startActivity( intent );
            }
        } );
        toursimg.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( NavigationDrawerActivity.this , ToursActivity.class );
                startActivity( intent );
            }
        } );
        generateIT.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( NavigationDrawerActivity.this , GenerateItineraryActivity.class );
                startActivity( intent );
            }
        } );
        FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }
        } );

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.navigation_drawer, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {

        FragmentManager fragmentManager = getFragmentManager();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Hotel)
        {
            Intent intent = new Intent( NavigationDrawerActivity.this , HotelActivity.class );
            startActivity( intent );
        }
        else if (id == R.id.nav_Flight)
        {
            Intent intent = new Intent( NavigationDrawerActivity.this , FlightActivity.class);
            startActivity( intent );
        }
        else if (id == R.id.nav_Car)
        {
            Intent intent = new Intent( NavigationDrawerActivity.this , CarActivity.class);
            startActivity( intent );        }
        else if (id == R.id.nav_Tour)
        {
            Intent intent = new Intent( NavigationDrawerActivity.this , ToursActivity.class);
            startActivity( intent );
        }

        else if (id == R.id.nav_CurrencyConverter)
        {
            Intent intent = new Intent( NavigationDrawerActivity.this , CurrencyConverterActivity.class);
            startActivity( intent );
        }
        else if (id == R.id.nav_MyHistory)
        {
            Intent intent = new Intent( NavigationDrawerActivity.this , MyHistoryActivity.class);
            startActivity( intent );
        }
        else if (id == R.id.nav_EditProfile)
        {
            Intent intent = new Intent( NavigationDrawerActivity.this , EditProfileActivity.class);
            startActivity( intent );
        }
        else if (id == R.id.nav_SignOut)
        {

            SharedPreferences preferences =getSharedPreferences("userSharedpref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            finish();
            Intent intent = new Intent( NavigationDrawerActivity.this , LoginActivity.class);
            startActivity( intent );
        }
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    private void getHotels(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        HotelClass hotelClass = new HotelClass();
                        hotelClass.setHotel_id(jsonObject.getInt("hotel id"));
                        hotelClass.setHotel_name(jsonObject.getString("hotel name"));
                        hotelClass.setHotel_stars(jsonObject.getInt("hotel stars"));
                        hotelClass.setHotel_longitude(jsonObject.getString("hotel long"));
                        hotelClass.setHotel_latitude(jsonObject.getString("hotel lat"));
                        //hotelClass.setHotel_rating(jsonObject.getDouble("hotel rating"));
                        hotelClass.setHotel_rating(jsonObject.getDouble("hotel rating"));
                        hotelClass.setNum_rooms(jsonObject.getInt("number of rooms"));
                        hotelClass.setSgl_price(jsonObject.getDouble("single room price"));
                        hotelClass.setDbl_price(jsonObject.getDouble("double room price"));
                        hotelClass.setHotel_img(jsonObject.getString("hotel image"));

                        if (hotelClass.getHotel_rating() >= 4)
                            hotelList.add(hotelClass);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //hotelL.setFocusable(false);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    private void getTours(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
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
                        if(toursClass.getRating() > 3.5) {
                            Log.i("price", String.valueOf(toursClass.getTour_price()));
                            tourList.add(toursClass);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //Toast.makeText( ListOfHotelsActivity.this, hotelList.size() + " Hotels", Toast.LENGTH_LONG ).show();
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
