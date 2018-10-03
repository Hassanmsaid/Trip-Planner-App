package com.example.lenovo.finalgp_test1.Search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.finalgp_test1.Lists.ListOfCarsActivity;
import com.example.lenovo.finalgp_test1.R;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

public class CarActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView seekVal;
    //private EditText num_personsET;
    int progress;
    String datefrom, dateto;
    int counter = 0;
    public static int num_days;
    int fday, lday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_car );

        final Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR,1);

        final CalendarPickerView datepicker = (CalendarPickerView) findViewById(R.id.calendar);
        datepicker.init(today,nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE);

        Button searchCar = (Button)findViewById( R.id.searchCarBtn);
        //Button dateCarBtn = (Button)findViewById(R.id.calendar_carBtn);

        //final EditText destination = (EditText)findViewById(R.id.FromCity_ET);
        final Spinner citySpinner = findViewById(R.id.city_cars_spinner);
        seekBar = (SeekBar)findViewById(R.id.seekBar1_car);

       // num_personsET = (EditText)findViewById(R.id.numOfPerson_ET);


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
                Toast.makeText(CarActivity.this, "Max price: " + progress, Toast.LENGTH_SHORT).show();
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
                num_days = lday-fday+1;
                //Toast.makeText(CarActivity.this, selectedDate, Toast.LENGTH_SHORT).show();
                Toast.makeText(CarActivity.this, num_days + " Days", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

        searchCar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( CarActivity.this , ListOfCarsActivity.class );
                intent.putExtra("city_name", citySpinner.getSelectedItem().toString());
                intent.putExtra("max_car_price", progress);
                startActivity( intent );
            }
        } );
    }
}
