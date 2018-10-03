package com.example.lenovo.finalgp_test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

public class GenerateItineraryActivity extends AppCompatActivity {

    public static long differenceOfDatesInDays = 0;
    public int city_id = 2;
    private String url;
    private SeekBar seekBar;
    int progress;
    Button generateBtn;
    RadioButton adventure, dinning, sports, entertainment, historical, exploring,
            religious, artistic;
    Spinner filterSpinner, filterTypeSpinner, flightSpinner, cityFromSpinner, cityToSpinner, flightSeatSpinner;
    long datefromX = 0;
    int counter = 0;

    @Override
    protected void onResume() {
        super.onResume();
        adventure.setChecked(false);
        dinning.setChecked(false);
        sports.setChecked(false);
        entertainment.setChecked(false);
        historical.setChecked(false);
        exploring.setChecked(false);
        religious.setChecked(false);
        artistic.setChecked(false);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_generate_itinerary );

        url = "http://suspense.atwebpages.com/api/tours_price_sort.php?city_id=2";
        generateBtn = findViewById(R.id.generateIT_Btn);

        adventure = findViewById(R.id.AdventureTV);
        dinning = findViewById(R.id.DinningTV);
        sports = findViewById(R.id.SportsTV);
        entertainment = findViewById(R.id.EntertainmentTV);
        historical = findViewById(R.id.HistoricalTV);
        exploring = findViewById(R.id.ExploringTV);
        religious = findViewById(R.id.ReligiousTV);
        artistic = findViewById(R.id.ArtisticTV);



        flightSeatSpinner = findViewById(R.id.flightCategory_spinner);
        cityFromSpinner = findViewById(R.id.from_GI_spinner);
        cityToSpinner = findViewById(R.id.to_GI_spinner);
        filterSpinner = findViewById(R.id.filteredBy_spinner);
        flightSpinner = findViewById(R.id.flightCategory_spinner);
        filterTypeSpinner = findViewById(R.id.filterTypeGISpinner);
        seekBar = findViewById(R.id.seekbarBudget);

        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR,1);

        CalendarPickerView datepicker = findViewById(R.id.calendar_GIt);
        datepicker.init(today,nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE);

        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(GenerateItineraryActivity.this, ItenaryActivity.class);
                if(adventure.isChecked())
                    i.putExtra("adv", "yes");
                else
                    i.putExtra("adv", "no");
                if(dinning.isChecked())
                    i.putExtra("din", "yes");
                else
                    i.putExtra("din", "no");
                if(sports.isChecked())
                    i.putExtra("sport", "yes");
                else
                    i.putExtra("sport", "no");
                if(entertainment.isChecked())
                    i.putExtra("ent", "yes");
                else
                    i.putExtra("ent", "no");
                if(historical.isChecked())
                    i.putExtra("hist", "yes");
                else
                    i.putExtra("hist", "no");
                if(exploring.isChecked())
                    i.putExtra("exp", "yes");
                else
                    i.putExtra("exp", "no");
                if(religious.isChecked())
                    i.putExtra("rel", "yes");
                else
                    i.putExtra("rel", "no");
                if(artistic.isChecked())
                    i.putExtra("art", "yes");
                else
                    i.putExtra("art", "no");

                if(filterSpinner.getSelectedItem().equals("Price"))
                    i.putExtra("filter", "price");
                else
                    i.putExtra("filter", "rating");

                if(flightSpinner.getSelectedItem().equals("Economy"))
                    i.putExtra("flight", "economy");
                else
                    i.putExtra("flight", "business");

                if(flightSeatSpinner.getSelectedItem().equals("Economy"))
                    i.putExtra("flight_class", "eco");
                else
                    i.putExtra("flight_class", "bus");

                if(filterTypeSpinner.getSelectedItem().equals("Ascending"))
                    i.putExtra("filter_sort", "asc");
                else
                    i.putExtra("filter_sort", "desc");

                if(cityFromSpinner.getSelectedItem().toString().equals(cityToSpinner.getSelectedItem().toString())){
                    Toast.makeText(GenerateItineraryActivity.this, "Choose different cities!", Toast.LENGTH_SHORT).show();
                }

                else{
                    i.putExtra("budget", progress);
                    i.putExtra("itiCityFrom", cityFromSpinner.getSelectedItem().toString());
                    i.putExtra("itiCityTo", cityToSpinner.getSelectedItem().toString());
                    i.putExtra("differenceOfDatesInDays", differenceOfDatesInDays);
                    startActivity(i);
                }
                i.putExtra("budget", progress);
                i.putExtra("itiCityFrom", cityFromSpinner.getSelectedItem().toString());
                i.putExtra("itiCityTo", cityToSpinner.getSelectedItem().toString());
                i.putExtra("differenceOfDatesInDays", differenceOfDatesInDays);
                startActivity(i);
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
                Toast.makeText(GenerateItineraryActivity.this, "Max Budget: " + progress, Toast.LENGTH_SHORT).show();
            }
        });
        datepicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                Calendar calselected = Calendar.getInstance();
                calselected.setTime(date);

                Date df = new Date();
                Date dt = new Date();

                String selectedDate = "" + calselected.get(Calendar.DAY_OF_MONTH)
                        + "-" + (calselected.get(Calendar.MONTH) + 1)
                        + "-" + (calselected.get(Calendar.YEAR));

                if (counter % 2 == 0)
                {
                    datefromX = calselected.getTimeInMillis();
                }
                else
                {
                    long datetoX = calselected.getTimeInMillis();
                    long differenceOfDatesInMS = datetoX - datefromX;
                    long differenceOfDatesInS = differenceOfDatesInMS/1000;
                    long differenceOfDatesInM = differenceOfDatesInS/60;
                    long differenceOfDatesInH = differenceOfDatesInM/60;
                    differenceOfDatesInDays = differenceOfDatesInH/24;
                    Toast.makeText(GenerateItineraryActivity.this, String.valueOf(differenceOfDatesInDays)+" days", Toast.LENGTH_SHORT).show();
                }
                counter++;
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }
}
