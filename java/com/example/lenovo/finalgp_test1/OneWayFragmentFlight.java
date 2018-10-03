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

import com.example.lenovo.finalgp_test1.Lists.ListOfOnewayFlightsActivity;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneWayFragmentFlight extends Fragment
{

    Spinner spinnerFrom;
    Spinner spinnerTo ;
    int fday, lday;
    String datefrom, dateto;
    int counter = 0;

    public OneWayFragmentFlight()
    {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate( R.layout.fragment_one_way_fragment_flight, container, false );

        spinnerFrom = rootView.findViewById(R.id.spinner_fromCityOneWay);
        spinnerTo = rootView.findViewById(R.id.spinner_toCityOneWay);

        final Date today = new Date();
        android.icu.util.Calendar nextYear = android.icu.util.Calendar.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            nextYear.add(android.icu.util.Calendar.YEAR,1);
        }
        final CalendarPickerView datepicker = rootView.findViewById(R.id.calendar_flight1);
        datepicker.init(today,nextYear.getTime());

        datepicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener()
        {
            @Override
            public void onDateSelected(Date date) {

                android.icu.util.Calendar calselected = android.icu.util.Calendar.getInstance();
                calselected.setTime(date);
                String selectedDate = (calselected.get(android.icu.util.Calendar.YEAR))
                        + "-" + (calselected.get(android.icu.util.Calendar.MONTH)+1)
                        +"-" + + calselected.get(android.icu.util.Calendar.DAY_OF_MONTH);


                if(counter %2==0) {
                    datefrom = selectedDate;
                    Log.d("from", datefrom);
                    fday = calselected.get(android.icu.util.Calendar.DAY_OF_MONTH);
                }
                else
                {
                    dateto = selectedDate;
                    Log.d("to", dateto);
                    lday = calselected.get(android.icu.util.Calendar.DAY_OF_MONTH);
                }
                counter++;

            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

        Button searchFlightOneWay = (Button)rootView.findViewById( R.id.button_searchForFlightsOneWay );
        searchFlightOneWay.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                 Intent intent = new Intent(getActivity(), ListOfOnewayFlightsActivity.class);
                // Intent intent1 = new Intent(OneWayFragmentFlight.this, ReserveFlightActivity.class);
                intent.putExtra("city_from", spinnerFrom.getSelectedItem().toString());
                intent.putExtra("city_to", spinnerTo.getSelectedItem().toString());
                intent.putExtra("flight_date", datefrom);
                startActivity( intent );

            }
        } );

        return rootView;
    }

}

