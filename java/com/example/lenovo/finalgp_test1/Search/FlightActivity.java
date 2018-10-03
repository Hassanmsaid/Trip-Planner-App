package com.example.lenovo.finalgp_test1.Search;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lenovo.finalgp_test1.R;
import com.example.lenovo.finalgp_test1.flightsFragmentAdapter;

public class FlightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_flight);
        try {
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
            viewPager.setAdapter(new flightsFragmentAdapter(getSupportFragmentManager()));

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout2);
            tabLayout.setupWithViewPager(viewPager);
        }
        catch (Exception e){
            setContentView(R.layout.activity_flight);
        }
    }}

