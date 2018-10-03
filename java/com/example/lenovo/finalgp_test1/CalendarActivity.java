package com.example.lenovo.finalgp_test1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lenovo.finalgp_test1.Search.CarActivity;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {
    String datefrom, dateto;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_calendar );

        final Date today = new Date();
        Button confirmCarBtn = (Button)findViewById(R.id.car_calender_confirm);
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR,1);

        final CalendarPickerView datepicker = (CalendarPickerView) findViewById(R.id.calendar);
        datepicker.init(today,nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE);
        // .withSelectedDate(today);


        datepicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                //  String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);

                Calendar calselected = Calendar.getInstance();
                calselected.setTime(date);

                String selectedDate ="" + calselected.get(Calendar.DAY_OF_MONTH)
                        + "-" + (calselected.get(Calendar.MONTH)+1)
                        +"-" + (calselected.get(Calendar.YEAR));

                // final String dateee = String.valueOf(Datee);

                if(counter %2==0) {
                    datefrom = selectedDate;
                    Log.d("from", datefrom);

                }
                else
                {
                    dateto = selectedDate;
                    Log.d("to", dateto);
                }
                counter++;
                Toast.makeText(CalendarActivity.this, selectedDate, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

        confirmCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CalendarActivity.this, CarActivity.class);
                i.putExtra("date_from", datefrom);
                i.putExtra("date_to", dateto);
                startActivity(i);

                /*Intent i2 = new Intent(CalendarActivity.this, FlightActivity.class);
                i2.putExtra("flight_date_from", datefrom);
                i2.putExtra("flight_date_to", dateto);
                startActivity(i2);*/
            }
        });

    }
}
