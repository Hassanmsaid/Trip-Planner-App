package com.example.lenovo.finalgp_test1.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.finalgp_test1.R;
import com.example.lenovo.finalgp_test1.Reservations.ReserveFlightOneWayActivity;
import com.example.lenovo.finalgp_test1.TripPlannerClasses.FlightClass;

import java.util.List;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.ViewHolder> {


    private Context context;
    private List<FlightClass> list;

    public FlightAdapter(Context context, List<FlightClass> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.single_flight, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        final FlightClass flightClass = list.get(position);
        holder.textFlightNo.setText("Flight num: " + String.valueOf(flightClass.getFlight_num()));
        holder.textFromCity.setText("From: " + String.valueOf(flightClass.getFlying_from()));
        holder.textToCity.setText("To: " + String.valueOf(flightClass.getFlying_to()));
        holder.textAirways.setText("Airways: " + String.valueOf(flightClass.getAirways()));
        holder.textFlightBusinessPrice.setText(String.valueOf( flightClass.getBusiness_price()));
        holder.textFlightEconomyPrice.setText(String.valueOf( flightClass.getEconomy_price()));

        holder.relativeLayout.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(context, ReserveFlightOneWayActivity.class);
                i.putExtra("flight_num", flightClass.getFlight_num());
                i.putExtra("flight_id", flightClass.getFlight_id());
                i.putExtra("flying_from" , flightClass.getFlying_from());
                i.putExtra("flying_to" , flightClass.getFlying_to());
                i.putExtra("dep_time", flightClass.getDeparture());
                i.putExtra("arr_time", flightClass.getArrival());
                i.putExtra("airways"  , flightClass.getAirways());
                i.putExtra("flight_date", flightClass.getFlight_date());
                i.putExtra("business_price", flightClass.getBusiness_price());
                i.putExtra("economy_price", flightClass.getEconomy_price());
                context.startActivity(i);
            }
        } );

    }
    @Override
    public int getItemCount()
    { return  list.size();}

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView textFlightNo , textFromCity ,textToCity, textFlightBusinessPrice,textFlightEconomyPrice , textAirways  ;
        public RelativeLayout relativeLayout;

        public ViewHolder (View v)
        {
            super(v);
            textFlightNo= v.findViewById(R.id.FlightNoTV);
            textToCity= v.findViewById(R.id.flyingToTV);
            textFromCity= v.findViewById(R.id.flyingFromTV);
            textFlightBusinessPrice= v.findViewById(R.id.flightBusinessPriceTV);
            textFlightEconomyPrice= v.findViewById(R.id.flightEconomyPriceTV);
            textAirways= v.findViewById(R.id.AirwaysTV);
            relativeLayout= v.findViewById(R.id.flight_parent);
        }
    }
}
