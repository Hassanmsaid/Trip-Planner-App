package com.example.lenovo.finalgp_test1.Search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lenovo.finalgp_test1.Lists.ListOfToursActivity;
import com.example.lenovo.finalgp_test1.R;
import com.example.lenovo.finalgp_test1.Reservations.ReserveTourActivity;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

public class ToursActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private EditText num_personsET;
    int progress;
    String datefrom, dateto;
    public static int num_persons;
    int fday, lday;
    int counter = 0;
    public static String sortBy, sortType;
    Spinner sortTourSpinner, sortTourTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tours );

        final Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR,1);
        final CalendarPickerView datepicker = findViewById(R.id.calendar);
        datepicker.init(today,nextYear.getTime());

        Button searchTourBtn = findViewById(R.id.searchToursBtn);
        final Spinner citiesSpinner = findViewById(R.id.city_tours_spinner);
        seekBar = findViewById(R.id.seekBar1_tour);
        num_personsET = findViewById(R.id.numOfPerson_ET);
        sortTourSpinner = findViewById(R.id.sort_tour_spinner);
        sortTourTypeSpinner = findViewById(R.id.sort_tour_type_spinner);

        searchTourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortBy = sortTourSpinner.getSelectedItem().toString();
                sortType = sortTourTypeSpinner.getSelectedItem().toString();
                Intent intent = new Intent( ToursActivity.this , ListOfToursActivity.class );
                Intent intent1 = new Intent(ToursActivity.this, ReserveTourActivity.class);
                intent.putExtra("city_name", citiesSpinner.getSelectedItem().toString());
                intent.putExtra("max_price", progress);
                num_persons = Integer.parseInt(num_personsET.getText().toString());
                startActivity( intent );
            }
        });
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
                Toast.makeText(ToursActivity.this, "Max Price: " + progress, Toast.LENGTH_SHORT).show();
            }
        });
        datepicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                //  String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);

                Calendar calselected = Calendar.getInstance();
                calselected.setTime(date);

                String selectedDate ="" + calselected.get(Calendar.DAY_OF_MONTH)
                        + "-" + (calselected.get(Calendar.MONTH)+1)
                        +"-" + (calselected.get(Calendar.YEAR));


                if(counter %2==0) {
                    datefrom = selectedDate;
                    Log.d("from", datefrom);
                    fday = calselected.get(Calendar.DAY_OF_MONTH);
                }
                else
                {
                    dateto = selectedDate;
                    Log.d("to", dateto);
                    lday = calselected.get(Calendar.DAY_OF_MONTH);
                }
                counter++;
                //Toast.makeText(CarActivity.this, selectedDate, Toast.LENGTH_SHORT).show();
                //Toast.makeText(ToursActivity.this, "days: " + num_days, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }
}
