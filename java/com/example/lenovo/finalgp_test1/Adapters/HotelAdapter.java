package com.example.lenovo.finalgp_test1.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.finalgp_test1.R;
import com.example.lenovo.finalgp_test1.Reservations.ReserveHotelActivity;
import com.example.lenovo.finalgp_test1.TripPlannerClasses.HotelClass;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Lenovo on 6/03/2018.
 */

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {

    private Context context;
    private List<HotelClass> list;

    public HotelAdapter(Context context, List<HotelClass> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.single_hotel, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HotelClass hotelClass = list.get(position);
        holder.textHotel.setText(hotelClass.getHotel_name());
        holder.textRating.setText(String.valueOf( "Rating: " + hotelClass.getHotel_rating()));
        holder.textStars.setText(String.valueOf("Stars: " + hotelClass.getHotel_stars()));
        Picasso.with(this.context).load(hotelClass.getHotel_img()).into(holder.hotelImg);
        //Picasso.with(this.context).load("http://q.bstatic.com/images/hotel/square128/117/11753593.jpg").into(holder.hotelImg);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "" + hotelClass.getHotel_img(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, ReserveHotelActivity.class);
                ReserveHotelActivity.hotelLong = hotelClass.getHotel_longitude();
                ReserveHotelActivity.hotelLat = hotelClass.getHotel_latitude();
                ReserveHotelActivity.hotelNamee = hotelClass.getHotel_name();
                //Toast.makeText(context, hotelClass.getHotel_longitude(), Toast.LENGTH_SHORT).show();

                i.putExtra("hotel_name", hotelClass.getHotel_name());
                i.putExtra("hotel_dbl_price", hotelClass.getDbl_price());
                i.putExtra("hotel_sgl_price", hotelClass.getSgl_price());
                i.putExtra("hotel_img", hotelClass.getHotel_img());
                i.putExtra("hotel_id", hotelClass.getHotel_id());
                context.startActivity(i);
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textHotel, textStars, textRating;
        public RelativeLayout relativeLayout;
        public ImageView hotelImg;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textHotel= itemView.findViewById(R.id.txtHotel);
            this.textStars= itemView.findViewById(R.id.txtStars);
            this.hotelImg = itemView.findViewById(R.id.imgHotel);
            this.textRating = itemView.findViewById(R.id.txtHotelRating);
            this.relativeLayout = itemView.findViewById(R.id.hotel_parent);
        }
    }
}
