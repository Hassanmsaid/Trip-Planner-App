package com.example.lenovo.finalgp_test1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.finalgp_test1.Adapters.DayAdapter;
import com.example.lenovo.finalgp_test1.TripPlannerClasses.DayClass;
import com.example.lenovo.finalgp_test1.TripPlannerClasses.FlightClass;
import com.example.lenovo.finalgp_test1.TripPlannerClasses.HotelClass;
import com.example.lenovo.finalgp_test1.TripPlannerClasses.ToursClass;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ItenaryActivity extends AppCompatActivity {

    String adventure, dinning, sports, entertainment, historical, exploring, religious, artistic, cityFrom, cityTo,
            url, urlHotels, urlFlight1, urlFlight2, urlReserveHotels, urlReserveTours, urlReserveFlights;
    boolean firstTime = true;
    Boolean seatType;
    int budget;
    ImageView hotelImg;
    TextView hotelName, hotelStars, hotelRating, fromTV, toTV, flighNoTV, airwaysTV, priceTV,
            fromTV2, toTV2, flighNoTV2, airwaysTV2, priceTV2 , showTotalTV;;
    public static boolean isItienrary;
    long differenceOfDatesInDays;
    public List<ToursClass> tourList;
    TextView txt;
    Button confirm , cancel;
    long numOfDays = 3;
    Map<Integer, List<ToursClass>> mapDaysTours;
    List<String> listBoolHrsPerDay = new ArrayList<>();
    Map<Integer, Integer> availableHours = new LinkedHashMap<>();
    public static Map<Integer, List<ToursClass>> dicDayNumberListOfTours = new LinkedHashMap<>();
    List<DayClass> daysList;
    private List<HotelClass> hotelList;
    private List<FlightClass> flightList1;
    private List<FlightClass> flightList2;
    public static double CalcBudget = 0;
    String hotel_img, seat_class;
    int user_id, hotel_id, tour_id, tourTotalPrice, flight_id, flight_id2;
    double hotelTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itenary);


        CalcBudget = 0;

        SharedPreferences shared = getSharedPreferences("userSharedpref", MODE_PRIVATE);
        user_id = shared.getInt("userID", 5);

        showTotalTV =findViewById(R.id.showTotalTV);

        isItienrary = true;
        hotelImg = findViewById(R.id.itienraryimgHotel);
        hotelName = findViewById(R.id.itienrarytxtHotel);
        hotelRating = findViewById(R.id.itienrarytxtHotelRating);
        hotelStars = findViewById(R.id.itienrarytxtStars);
        hotelList = new ArrayList<>();
        flightList1 = new ArrayList<>();
        flightList2 = new ArrayList<>();
        adventure = getIntent().getExtras().getString("adv");
        dinning = getIntent().getExtras().getString("din");
        sports = getIntent().getExtras().getString("sport");
        entertainment = getIntent().getExtras().getString("ent");
        historical = getIntent().getExtras().getString("hist");
        exploring = getIntent().getExtras().getString("exp");
        religious = getIntent().getExtras().getString("rel");
        artistic = getIntent().getExtras().getString("art");

        cityFrom = getIntent().getExtras().getString("itiCityFrom");
        cityTo = getIntent().getExtras().getString("itiCityTo");
        budget = getIntent().getExtras().getInt("budget");
        //txt = findViewById(R.id.txtItenary);

        fromTV = findViewById(R.id.itienraryflyingFromTV);
        toTV = findViewById(R.id.itienraryflyingToTV);
        flighNoTV = findViewById(R.id.itienraryFlightNoTV);
        airwaysTV = findViewById(R.id.itienraryAirwaysTV);
        priceTV = findViewById(R.id.itienraryflightPriceTV);

        fromTV2 = findViewById(R.id.itienraryflyingFromTV2);
        toTV2 = findViewById(R.id.itienraryflyingToTV2);
        flighNoTV2 = findViewById(R.id.itienraryFlightNoTV2);
        airwaysTV2 = findViewById(R.id.itienraryAirwaysTV2);
        priceTV2 = findViewById(R.id.itienraryflightPriceTV2);

        urlFlight1 = "http://suspense.atwebpages.com/api/ret_flights.php?departure_date=" + "2018-10-1&" + "departure_city=" + cityFrom + "&arrival_city=" + cityTo;
        urlFlight2 = "http://suspense.atwebpages.com/api/ret_flights.php?departure_date=" + "2018-10-8&" + "departure_city=" + cityTo + "&arrival_city=" + cityFrom;
        urlHotels = NavigationDrawerActivity.localUrl + "show_hotels_by_city_name.php?cityname=" + cityTo ;
        String filterSort = getIntent().getExtras().getString("filter_sort");

        if (getIntent().getExtras().getString("filter").equals("price")){
            if(filterSort.equals("desc"))
                url = "http://suspense.atwebpages.com/api/tours_price_sort.php?city_name=" + cityTo;
            else
                url = "http://suspense.atwebpages.com/api/tours_price_sort_asc.php?city_name=" + cityTo;
        }

        else{
            if(filterSort.equals("desc"))
                url = "http://suspense.atwebpages.com/api/tours_rating_sort.php?city_name=" + cityTo;
            else
                url = "http://suspense.atwebpages.com/api/tours_rating_sort_asc.php?city_name=" + cityTo;
        }

        confirm = findViewById(R.id.confirmBtn);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                reserveHotel();
                Intent i = new Intent(ItenaryActivity.this , NavigationDrawerActivity.class);
                startActivity(i);

            }
        });
        cancel = findViewById(R.id.cancelBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ItenaryActivity.this , NavigationDrawerActivity.class);
                startActivity(i);
            }

        });
        getFlight1();

    }
    private void getFlight1() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlFlight1, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        FlightClass flightClass = new FlightClass();

                        flightClass.setFlight_id(jsonObject.getInt("flight id"));
                        flightClass.setFlight_num(jsonObject.getInt("flight number"));
                        //flightClass.setFlying_from(jsonObject.getInt("flight id"));
                        //flightClass.setFlying_to(jsonObject.getInt("flight id"));
                        flightClass.setAirways(jsonObject.getString("airways"));
                        flightClass.setBusiness_seats(jsonObject.getInt("number of business seats"));
                        flightClass.setEconomy_seats(jsonObject.getInt("number of economy seats"));
                        flightClass.setBusiness_price(jsonObject.getInt("business price"));
                        flightClass.setEconomy_price(jsonObject.getInt("economy price"));
                        //flightClass.setDeparture(jsonObject.getString(""));
                        //flightClass.setArrival(jsonObject.getString(""));

                        flightList1.add(flightClass);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //********//
                    fromTV.setText("From: " + cityFrom);
                    toTV.setText("To: " + cityTo);
                    flighNoTV.setText("Flight no: " + flightList1.get(0).getFlight_num());
                    airwaysTV.setText("Airways: " + flightList1.get(0).getAirways());
                    if(getIntent().getExtras().getString("flight_class").equals("eco"))
                    {
                        priceTV.setText("Price: " + flightList1.get(0).getEconomy_price());
                    }
                    else
                    {
                        priceTV.setText("Price: " + flightList1.get(0).getBusiness_price());
                    }
                }

                CalcBudget += flightList1.get(0).getEconomy_price();
                flight_id = flightList1.get(0).getFlight_id();
                seat_class = "economy";
                getFlight2();
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

    private void getFlight2() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlFlight2, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        FlightClass flightClass = new FlightClass();

                        flightClass.setFlight_id(jsonObject.getInt("flight id"));
                        flightClass.setFlight_num(jsonObject.getInt("flight number"));
                        //flightClass.setFlying_from(jsonObject.getInt("flight id"));
                        //flightClass.setFlying_to(jsonObject.getInt("flight id"));
                        flightClass.setAirways(jsonObject.getString("airways"));
                        flightClass.setBusiness_seats(jsonObject.getInt("number of business seats"));
                        flightClass.setEconomy_seats(jsonObject.getInt("number of economy seats"));
                        flightClass.setBusiness_price(jsonObject.getInt("business price"));
                        flightClass.setEconomy_price(jsonObject.getInt("economy price"));
                        //flightClass.setDeparture(jsonObject.getString(""));
                        //flightClass.setArrival(jsonObject.getString(""));

                        flightList2.add(flightClass);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //********//
                    fromTV2.setText("From: " + cityTo);
                    toTV2.setText("To: " + cityFrom);
                    flighNoTV2.setText("Flight no: " + flightList2.get(0).getFlight_num());
                    airwaysTV2.setText("Airways: " + flightList2.get(0).getAirways());

                    if(getIntent().getExtras().getString("flight_class").equals("eco"))
                        priceTV2.setText("Price: " + flightList2.get(0).getEconomy_price());
                    else
                        priceTV2.setText("Price: " + flightList2.get(0).getBusiness_price());
                }
                CalcBudget += flightList2.get(0).getEconomy_price();
                flight_id2 = flightList2.get(0).getFlight_id();
                seat_class = "economy";
                getHotels();
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

    private void getHotels() {
        {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlHotels, new Response.Listener<JSONArray>() {
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
                            hotelClass.setHotel_rating(jsonObject.getDouble("hotel rating"));
                            hotelClass.setNum_rooms(jsonObject.getInt("number of rooms"));
                            hotelClass.setSgl_price(jsonObject.getDouble("single room price"));
                            hotelClass.setDbl_price(jsonObject.getDouble("double room price"));
                            hotelClass.setHotel_img(jsonObject.getString("hotel image"));

                            hotelList.add(hotelClass);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Collections.sort(hotelList, new Comparator<HotelClass>() {
                            @Override
                            public int compare(HotelClass hotelClass, HotelClass t1) {
                                if (getIntent().getExtras().getString("filter").equals("price")) {
                                    return Double.compare(hotelClass.getSgl_price(), t1.getSgl_price());
                                } else if(getIntent().getExtras().getString("filter").equals("rating")) {
                                    return Double.compare(t1.getHotel_rating(), hotelClass.getHotel_rating());
                                }
                                else
                                    return 0;
                            }
                        });
                        Picasso.with(ItenaryActivity.this).load(hotelList.get(0).getHotel_img()).into(hotelImg);
                        hotelName.setText(hotelList.get(0).getHotel_name());
                        hotelStars.setText(String.valueOf(hotelList.get(0).getHotel_stars()) + " Stars");
                        hotelRating.setText("Rating: " + String.valueOf(hotelList.get(0).getHotel_rating()));


                    }
                    differenceOfDatesInDays = getIntent().getExtras().getLong("differenceOfDatesInDays");
                    CalcBudget += hotelList.get(0).getSgl_price()*differenceOfDatesInDays;
                    hotel_id = hotelList.get(0).getHotel_id();
                    hotelTotalPrice = hotelList.get(0).getSgl_price()*differenceOfDatesInDays;
                    hotel_img = hotelList.get(0).getHotel_img();
                    getTours();
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
    private void getTours () {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                tourList = new ArrayList<>();
                Log.d("JSON_Hassan", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        ToursClass toursClass = new ToursClass();

                        toursClass.setTour_name(jsonObject.getString("tour_name"));
                        toursClass.setTour_img(jsonObject.getString("tour_img"));
                        toursClass.setTour_id(jsonObject.getInt("tour_id"));
                        toursClass.setTour_price(jsonObject.getInt("tour_price"));
                        toursClass.setRating(jsonObject.getDouble("rating"));
                        toursClass.setCategory(jsonObject.getString("category"));
                        toursClass.setClose(Integer.parseInt(jsonObject.getString("closes_at").substring(0, 2)));
                        toursClass.setOpen(Integer.parseInt(jsonObject.getString("opens_at").substring(0, 2)));

                        if (adventure.equals("yes")) {
                            if (toursClass.getCategory().equals("Adventure"))
                                tourList.add(toursClass);
                        }
                        if (dinning.equals("yes")) {
                            if (toursClass.getCategory().equals("Dinning"))
                                tourList.add(toursClass);
                        }
                        if (sports.equals("yes")) {
                            if (toursClass.getCategory().equals("Sports"))
                                tourList.add(toursClass);
                        }
                        if (entertainment.equals("yes")) {
                            if (toursClass.getCategory().equals("Entertainment"))
                                tourList.add(toursClass);
                        }
                        if (historical.equals("yes")) {
                            if (toursClass.getCategory().equals("Historical"))
                                tourList.add(toursClass);
                        }
                        if (exploring.equals("yes")) {
                            if (toursClass.getCategory().equals("Exploring"))
                                tourList.add(toursClass);
                        }
                        if (religious.equals("yes")) {
                            if (toursClass.getCategory().equals("Religious"))
                                tourList.add(toursClass);
                        }
                        if (artistic.equals("yes")) {
                            if (toursClass.getCategory().equals("Artistic"))
                                tourList.add(toursClass);
                        }
                        if(adventure.equals("no") && dinning.equals("no") && sports.equals("no") && entertainment.equals("no") && historical.equals("no") && exploring.equals("no") && religious.equals("no") && artistic.equals("no")){
                            tourList.add(toursClass);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                int open, close;
                differenceOfDatesInDays = getIntent().getExtras().getLong("differenceOfDatesInDays");
                int leastHrsOfDiscovery = 6;
                int MaximumBudget = getIntent().getExtras().getInt("budget");
                dicDayNumberListOfTours = new LinkedHashMap<>();
                for (int nd = 0; nd < differenceOfDatesInDays; nd++) {
                    listBoolHrsPerDay.add("000000000000000000000000");
                }
                //Toast.makeText(ItenaryActivity.this, tourList.size() + " total tours", Toast.LENGTH_SHORT).show();
                //bahwl elopening hours lel tour le zero we ones
                for (ToursClass tourE : tourList) {
                    if (MaximumBudget > CalcBudget) {

                        availableHours = new LinkedHashMap<>();
                        open = tourE.getOpen();
                        close = tourE.getClose();
                        if (close - open == 0)
                            continue;
                        int openingHours = close - open;
                        String tourBool = "000000000000000000000000";
                        for (int j = 0; j <= close; j++) {
                            if (close != 0) {
                                if (j >= open && j < close) {
                                    tourBool = tourBool.substring(0, j) + "1" + tourBool.substring(j, 23);
                                }
                            } else if (close == 0) {
                                if (j >= open && j <= 23) {
                                    tourBool = tourBool.substring(0, j) + "1" + tourBool.substring(j, 23);
                                }
                            }
                        }
                        //XORing to check elmawa3ed el tour fatha fyha weluser fady
                        boolean added = false;
                        int DayNumber = 0;
                        while (added == false && CalcBudget < MaximumBudget && DayNumber < listBoolHrsPerDay.size()) {
                            String TourXORDayHours = "";
                            String newAvailableHoursPerDay = "";
                            int ah = 0;
                            int firstIndex = 0;
                            List<ToursClass> trs = new ArrayList<>();
                            for (int j = 0; j <= 23; j++) {
                                if (tourBool.substring(j, j + 1).equals(listBoolHrsPerDay.get(DayNumber).substring(j, j + 1)) || tourBool.substring(j, j + 1).equals("0")) {
                                    TourXORDayHours += "0";
                                    if (ah > 0)
                                        availableHours.put(ah, firstIndex);
                                    ah = 0;
                                } else {
                                    TourXORDayHours += "1";
                                    if (ah == 0)
                                        firstIndex = j;
                                    ah++;

                                }
                                if (ah != 0 && j == 23) {
                                    availableHours.put(ah, firstIndex);
                                }
                                if (tourBool.substring(j, j + 1).equals(listBoolHrsPerDay.get(DayNumber).substring(j, j + 1))) {
                                    newAvailableHoursPerDay += "0";

                                } else {
                                    newAvailableHoursPerDay += "1";
                                }
                            }
                            Map<Integer, Integer> sortedAvailableHours = new TreeMap<>(availableHours);
                            //int a = 0;
                            String newAvailableHoursPerDay2 = listBoolHrsPerDay.get(DayNumber);
                            if (openingHours < leastHrsOfDiscovery) {
                                for (Integer key : sortedAvailableHours.keySet()) {
                                    if (openingHours <= key) {
                                        if (dicDayNumberListOfTours.get(DayNumber) == null) {
                                            trs.add(tourE);
                                            dicDayNumberListOfTours.put(DayNumber, trs);
                                        } else
                                            dicDayNumberListOfTours.get(DayNumber).add(tourE);
                                        added = true;
                                        listBoolHrsPerDay.set(DayNumber, newAvailableHoursPerDay);
                                        CalcBudget += tourE.getTour_price();
                                        break;
                                    }
                                }

                                //}
                            } else if (openingHours >= leastHrsOfDiscovery) {
                                int a = 0;
                                for (Integer key : sortedAvailableHours.keySet()) {
                                    if (leastHrsOfDiscovery <= key) {//&& (new ArrayList<Integer>(sortedAvailableHours.values())).get(a)<=21 && !(newAvailableHoursPerDay2.substring((new ArrayList<Integer>(sortedAvailableHours.values())).get(a), (new ArrayList<Integer>(sortedAvailableHours.values())).get(a)+1).equals("1")) && !(newAvailableHoursPerDay2.substring((new ArrayList<Integer>(sortedAvailableHours.values())).get(a)+1, (new ArrayList<Integer>(sortedAvailableHours.values())).get(a)+2).equals("1")) && !(newAvailableHoursPerDay2.substring((new ArrayList<Integer>(sortedAvailableHours.values())).get(a)+2, (new ArrayList<Integer>(sortedAvailableHours.values())).get(a)+3).equals("1"))){

                                        if (dicDayNumberListOfTours.get(DayNumber) == null) {
                                            trs.add(tourE);
                                            dicDayNumberListOfTours.put(DayNumber, trs);
                                        } else
                                            dicDayNumberListOfTours.get(DayNumber).add(tourE);
                                        added = true;

                                        Integer index = (new ArrayList<Integer>(sortedAvailableHours.values())).get(a);
                                        newAvailableHoursPerDay2 = newAvailableHoursPerDay2.substring(0, index) + "1111" + newAvailableHoursPerDay2.substring(index + 3, 23);
                                        listBoolHrsPerDay.set(DayNumber, newAvailableHoursPerDay2);
                                        CalcBudget += tourE.getTour_price();
                                        break;
                                    } else
                                        a++;
                                }
                            }
                            DayNumber++;
                        }
                    } else {
                        break;
                    }
                }
                daysList = new ArrayList<>();
                RecyclerView recyclerView = findViewById(R.id.allRecView);
                for (int i = 0; i < dicDayNumberListOfTours.size(); i++) {
                    DayClass dc = new DayClass("Day " + (i + 1), dicDayNumberListOfTours.get(i));
                    daysList.add(dc);
                }
                //Toast.makeText(ItenaryActivity.this, "Total Price = " + CalcBudget, Toast.LENGTH_SHORT).show();
                showTotalTV.setText(String.valueOf((int)CalcBudget));
                DayAdapter dayAdapter = new DayAdapter(ItenaryActivity.this, daysList);
                recyclerView.setLayoutManager(new LinearLayoutManager(ItenaryActivity.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(dayAdapter);
                recyclerView.setFocusable(false);
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

    public void reserveHotel(){

        com.android.volley.RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);

        urlReserveHotels = "http://suspense.atwebpages.com/api/hotel_reserve.php?user_id="+user_id+"&hotel_id="+hotel_id+"&check_in=2018-10-1" + "&check_out=2018-10-5" + "&num_single="+1+"&num_double="+0+"&total_price="+hotelTotalPrice+"&hotel_img="+hotel_img;

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, urlReserveHotels, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                reserveTour();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
    public void reserveTour(){
        for (int i = 0;i<dicDayNumberListOfTours.size();i++)
            for (ToursClass tc: dicDayNumberListOfTours.get(i)
                    ) {
                tour_id = 0;
                tourTotalPrice = 0;

                tour_id = tc.getTour_id();
                tourTotalPrice = tc.getTour_price();
                urlReserveTours = "http://suspense.atwebpages.com/api/tour_reserve.php?user_id=" + user_id +
                        "&tour_id=" +  tour_id + "&num_persons=" + 1 + "&tour_total=" + tourTotalPrice;

                StringRequest stringRequest = new StringRequest(urlReserveTours, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }
        reserveFlight();
    }
    public void reserveFlight() {

        urlReserveFlights = "http://suspense.atwebpages.com/api/flight_reserve.php?user_id=" + user_id + "&flight_id=" + flight_id + "&num_passengers=" + 1 + "&seat_type=" + seat_class;

        StringRequest stringRequest = new StringRequest(urlReserveFlights, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                reserveFlight2();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ItenaryActivity.this);
        requestQueue.add(stringRequest);
    }

    public void reserveFlight2() {

        urlReserveFlights = "http://suspense.atwebpages.com/api/flight_reserve.php?user_id=" + user_id + "&flight_id=" + flight_id2 + "&num_passengers=" + 1 + "&seat_type=" + seat_class;

        StringRequest stringRequest = new StringRequest(urlReserveFlights, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("done")) {
                    Toast.makeText(ItenaryActivity.this, "Done", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ItenaryActivity.this);
        requestQueue.add(stringRequest);
    }
}
