package com.example.lenovo.finalgp_test1;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.example.lenovo.finalgp_test1.Lists.ListOfTwowayFlightActivity;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */

public class BlankFragmentFlight extends Fragment
{
    Spinner spinnerFrom;
    Spinner spinnerTo ;

    String datefrom, dateto;
    int counter = 0;
    public static int num_days;
    int fday, lday;

    public BlankFragmentFlight() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_blank_fragment_flight, container, false);
        spinnerFrom = rootView.findViewById(R.id.spinner_fromCityTwoWay);
        spinnerTo = rootView.findViewById(R.id.spinner_toCityTwoWay);
        final Date today = new Date();
        java.util.Calendar nextYear = java.util.Calendar.getInstance();
        nextYear.add( java.util.Calendar.YEAR, 1 );

        final CalendarPickerView datepicker = (CalendarPickerView) rootView.findViewById( R.id.calendar_flight2 );
        datepicker.init( today, nextYear.getTime() )
                .inMode( CalendarPickerView.SelectionMode.RANGE );
        datepicker.setOnDateSelectedListener( new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                //  String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);

                java.util.Calendar calselected = java.util.Calendar.getInstance();
                calselected.setTime( date );

                String day = "";
                if(calselected.get( java.util.Calendar.DAY_OF_MONTH )<10){
                    day = "0"+calselected.get( java.util.Calendar.DAY_OF_MONTH );
                }
                else
                    day = ""+calselected.get( java.util.Calendar.DAY_OF_MONTH );
                String selectedDate = "" + ( calselected.get( java.util.Calendar.YEAR )
                        + "-" + ( calselected.get( java.util.Calendar.MONTH ) + 1 )
                        + "-" +day);


                if (counter % 2 == 0) {
                    datefrom = selectedDate;
                    Log.d( "from", datefrom );
                    fday = calselected.get( java.util.Calendar.DAY_OF_MONTH );
                } else {
                    dateto = selectedDate;
                    Log.d( "to", dateto );
                    lday = calselected.get( java.util.Calendar.DAY_OF_MONTH );
                }
                counter++;

            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

        Button searchFlightTwoWay = (Button)rootView.findViewById( R.id.button_searchForFlightsTwoWay );
        searchFlightTwoWay.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), ListOfTwowayFlightActivity.class);
                // Intent intent1 = new Intent(OneWayFragmentFlight.this, ReserveFlightActivity.class);
                intent.putExtra("city_from",  spinnerFrom.getSelectedItem().toString());
                intent.putExtra("city_to", spinnerTo.getSelectedItem().toString());
                intent.putExtra("flight_date_from", datefrom);
                intent.putExtra("flight_date_to", dateto);
                startActivity( intent );

            }
        } );

        return rootView;
    }
}
