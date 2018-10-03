package com.example.lenovo.finalgp_test1.Search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.finalgp_test1.Lists.ListOfHotelsActivity;
import com.example.lenovo.finalgp_test1.R;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

public class HotelActivity extends AppCompatActivity {


    private SeekBar seekBar;
    private TextView seekVal;
    RatingBar ratingBar;
    int progress;
    String datefrom, dateto;
    int counter = 0;
    public static int num_days, min_stars;
    public static double min_rating;
    public static String sortBy, sortType;
    int fday, lday;
    EditText minRating;
    Spinner sortHotelSpinner, sortHotelTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_hotel );

        final Date today = new Date();
        Calendar nextYear = Calendar.getInstance();

        //nextYear.set(2018,5,11);

        nextYear.add(Calendar.YEAR,2);
        final CalendarPickerView datepicker = findViewById(R.id.calendar_hotel);
        datepicker.init(today,nextYear.getTime()).inMode(CalendarPickerView.SelectionMode.RANGE);

        final Spinner citySpinner = findViewById(R.id.city_hotels_spinner);
        minRating = findViewById(R.id.minRatingET);
        Button searchHotel = findViewById( R.id.SearchHotelBtn);
        ratingBar = findViewById(R.id.rating_hotels);
        sortHotelSpinner = findViewById(R.id.sort_hotel_spinner);
        sortHotelTypeSpinner = findViewById(R.id.sort_hotel_type_spinner);


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(HotelActivity.this, "Min Stars: " + (int)v, Toast.LENGTH_SHORT).show();
                min_stars = (int)v;
            }
        });

        searchHotel.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sortBy = sortHotelSpinner.getSelectedItem().toString();
                sortType = sortHotelTypeSpinner.getSelectedItem().toString();
                Intent intent = new Intent( HotelActivity.this , ListOfHotelsActivity.class );
                intent.putExtra("city_name", citySpinner.getSelectedItem().toString());
                intent.putExtra("min_rating", min_rating);
                intent.putExtra("max_single_price", progress);
                intent.putExtra("min_stars", min_stars);
                min_rating = Double.parseDouble(minRating.getText().toString());
                startActivity( intent );
            }
        } );

        seekBar = findViewById(R.id.seekBar1_hotel);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //seekVal.setText(progress);
                Toast.makeText(HotelActivity.this, "Max price: " + progress, Toast.LENGTH_SHORT).show();
            }
        });


        datepicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                //  String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);

                Calendar calselected = Calendar.getInstance();
                calselected.setTime( date );

                String selectedDate = "" + calselected.get( Calendar.DAY_OF_MONTH )
                        + "-" + ( calselected.get( Calendar.MONTH ) + 1 )
                        + "-" + ( calselected.get( Calendar.YEAR ) );


                if (counter % 2 == 0) {
                    datefrom = selectedDate;
                    Log.d( "from", datefrom );
                    fday = calselected.get( Calendar.DAY_OF_MONTH );
                } else {
                    dateto = selectedDate;
                    Log.d( "to", dateto );
                    lday = calselected.get( Calendar.DAY_OF_MONTH );
                }
                counter++;
                num_days = lday - fday + 1;
                //Toast.makeText(CarActivity.this, selectedDate, Toast.LENGTH_SHORT).show();
                Toast.makeText( HotelActivity.this, "days: " + num_days, Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }
}
