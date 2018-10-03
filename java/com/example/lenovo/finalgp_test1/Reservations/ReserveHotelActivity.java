package com.example.lenovo.finalgp_test1.Reservations;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.finalgp_test1.MapsActivityHotel;
import com.example.lenovo.finalgp_test1.NavigationDrawerActivity;
import com.example.lenovo.finalgp_test1.R;
import com.example.lenovo.finalgp_test1.Search.HotelActivity;
import com.squareup.picasso.Picasso;

public class ReserveHotelActivity extends AppCompatActivity {

    private double single_price, double_price;
    int nights, temp, user_id, hotel_id;
    Button reserveHotelBtn;
    String hotel_name;
    TextView sglPrice, dblPrice, sglNum, dblNum, hotelName;
    ImageButton incSingle, decSingle, incDouble, decDouble, hotelLocation;
    public static String hotelLong, hotelLat, hotelNamee;
    ImageView hotel_img;
    private String url;
    com.android.volley.RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_reserve_hotel);


        SharedPreferences shared = getSharedPreferences("userSharedpref", MODE_PRIVATE);
        user_id = shared.getInt("userID", 5);


        requestQueue = Volley.newRequestQueue(this);

        hotel_name = getIntent().getExtras().getString("hotel_name");
        single_price = getIntent().getExtras().getDouble("hotel_sgl_price");
        double_price = getIntent().getExtras().getDouble("hotel_dbl_price");
        hotel_id = getIntent().getExtras().getInt("hotel_id");
        nights = HotelActivity.num_days;
        reserveHotelBtn = findViewById(R.id.ReserveHotelBtn);
        hotelLocation = findViewById(R.id.locationBtnHotel);
        hotelName = findViewById(R.id.hotelNameTV);
        hotelName.setText(hotel_name);
        hotel_img = findViewById(R.id.hotelImage);
        Picasso.with(getApplication()).load(getIntent().getExtras().getString("hotel_img")).into(this.hotel_img);

        sglNum = findViewById(R.id.number_Single_1_Tv);
        dblNum = findViewById(R.id.number_Double_1_Tv);

        sglPrice = findViewById(R.id.pricePerSingle);
        dblPrice = findViewById(R.id.pricePerDouble);
        sglPrice.setText(String.valueOf(single_price));
        dblPrice.setText(String.valueOf(double_price));

        incSingle = this.findViewById(R.id.IncreaseSingleRoomBtn);
        decSingle = this.findViewById(R.id.DecreaseSingleRoomBtn);
        incDouble = this.findViewById(R.id.IncreaseDoubleRoomBtn);
        decDouble = this.findViewById(R.id.DecreaseDoubleRoomBtn);

        incSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = Integer.parseInt(sglNum.getText().toString());
                temp++;
                sglNum.setText(String.valueOf(temp));
            }
        });
        decSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = Integer.parseInt(sglNum.getText().toString());
                if(temp > 0){
                    temp--;
                    sglNum.setText(String.valueOf(temp));
                }
            }
        });
        incDouble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = Integer.parseInt(dblNum.getText().toString());
                temp++;
                dblNum.setText(String.valueOf(temp));
            }
        });
        decDouble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = Integer.parseInt(dblNum.getText().toString());
                if(temp > 0){
                    temp--;
                    dblNum.setText(String.valueOf(temp));
                }
            }
        });
        hotelLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ReserveHotelActivity.this, MapsActivityHotel.class);
                startActivity(i);
            }
        });
        reserveHotelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double totalPrice = (Double.parseDouble(sglNum.getText().toString())*Double.parseDouble(sglPrice.getText().toString()))+
                        (Double.parseDouble(dblNum.getText().toString())*Double.parseDouble(dblPrice.getText().toString()));
                url = "http://suspense.atwebpages.com/api/hotel_reserve.php?user_id="+user_id+"&hotel_id="+hotel_id+"&check_in=2018-10-1" + "&check_out=2018-10-5" + "&num_single="+Integer.parseInt(sglNum.getText().toString())+"&num_double="+Integer.parseInt(dblNum.getText().toString())+"&total_price="+totalPrice+"&hotel_img="+hotel_img;

                reserve();
            }
        });
    }

    public void reserve(){

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                if (response.equals("done")) {
                    Toast.makeText(ReserveHotelActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ReserveHotelActivity.this , NavigationDrawerActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

}
