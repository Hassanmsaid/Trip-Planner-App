//package com.example.lenovo.finalgp_test1.Adapters;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.example.lenovo.finalgp_test1.R;
//import com.example.lenovo.finalgp_test1.Reservations.ReserveFlightOneWayActivity;
//import com.example.lenovo.finalgp_test1.TripPlannerClasses.RoundFlightClass;
//
//import java.util.List;
//
//public class RoundFlightAdapter extends RecyclerView.Adapter<RoundFlightAdapter.ViewHolder>
//        {
//            private Context context;
//            private List<RoundFlightClass> list;
//
//            public RoundFlightAdapter(Context context, List<RoundFlightClass> list)
//            {
//                this.context = context;
//                this.list = list;
//            }
//
//            @Override
//            public RoundFlightAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
//            {
//                View v = LayoutInflater.from(context).inflate(R.layout.singleround, parent, false);
//                return new RoundFlightAdapter.ViewHolder(v);
//            }
//
//            @Override
//            public void onBindViewHolder(ViewHolder holder, int position)
//            {
//                final RoundFlightClass flightClass = list.get(position);
//                holder.textFlightNo.setText("Flight num: " + String.valueOf(flightClass.getFlight_num()));
//                holder.textFromCity.setText("From: " + String.valueOf(flightClass.getFlying_from()));
//                holder.textToCity.setText("To: " + String.valueOf(flightClass.getFlying_to()));
//                holder.textAirways.setText("Airways: " + String.valueOf(flightClass.getAirways()));
//                holder.textFlightBusinessPrice.setText(String.valueOf( flightClass.getBusiness_price()));
//                holder.textFlightEconomyPrice.setText(String.valueOf( flightClass.getEconomy_price()));
//
//                holder.relativeLayout.setOnClickListener( new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        Intent i = new Intent(context, ReserveFlightOneWayActivity.class);
//                        i.putExtra("flight_num", flightClass.getFlight_num());
//                        i.putExtra("flying_from" , flightClass.getFlying_from());
//                        i.putExtra("flying_to" , flightClass.getFlying_to());
//                        i.putExtra("airways"  , flightClass.getAirways());
//                        i.putExtra("flight_date_from", flightClass.getFlight_date_from());
//                        i.putExtra("flight_date_to", flightClass.getFlight_date_to());
//                        i.putExtra("business_price", flightClass.getBusiness_price());
//                        i.putExtra("economy_price", flightClass.getEconomy_price());
//                        context.startActivity(i);
//                    }
//                } );
//
//            }
//
//            @Override
//            public int getItemCount()
//            { return  list.size();}
//
//            public class ViewHolder extends RecyclerView.ViewHolder
//            {
//
//                public TextView textFlightNo , textFromCity ,textToCity, textFlightBusinessPrice,textFlightEconomyPrice , textAirways  ;
//                public RelativeLayout relativeLayout;
//
//                public ViewHolder (View v)
//                {
//                    super(v);
//                    textFlightNo= v.findViewById(R.id.roundFlightNoTV);
//                    textToCity= v.findViewById(R.id.roundflyingToTV);
//                    textFromCity= v.findViewById(R.id.roundflyingFromTV);
//                    textFlightBusinessPrice= v.findViewById(R.id.roundflightBusinessPriceTV);
//                    textFlightEconomyPrice= v.findViewById(R.id.roundflightEconomyPriceTV);
//                    textAirways= v.findViewById(R.id.roundAirwaysTV);
//                    relativeLayout= v.findViewById(R.id.roundflight_parent);
//                }
//            }
//        }


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
import com.example.lenovo.finalgp_test1.Reservations.ReserveFlightTwoWayActivity;
import com.example.lenovo.finalgp_test1.TripPlannerClasses.RoundFlightClass;

import java.util.List;

public class RoundFlightAdapter extends RecyclerView.Adapter<RoundFlightAdapter.ViewHolder>
{
    private Context context;
    private List<RoundFlightClass> list;

    public RoundFlightAdapter(Context context, List<RoundFlightClass> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public RoundFlightAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.singleround, parent, false);
        return new RoundFlightAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        final RoundFlightClass flightClass = list.get(position);
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
                Intent i = new Intent(context, ReserveFlightTwoWayActivity.class);
                i.putExtra("flight_num", flightClass.getFlight_num());
                i.putExtra("flying_from" , flightClass.getFlying_from());
                i.putExtra("flying_to" , flightClass.getFlying_to());
                i.putExtra("airways"  , flightClass.getAirways());
                i.putExtra("flight_date_from", flightClass.getFlight_date_from());
                i.putExtra("flight_date_to", flightClass.getFlight_date_to());
                i.putExtra("business_price", flightClass.getBusiness_price());
                i.putExtra("economy_price", flightClass.getEconomy_price());
                i.putExtra("flight_id", flightClass.getFlight_id());
                i.putExtra("dep_time", flightClass.getDeparture());
                i.putExtra("arr_time", flightClass.getArrival());
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
            textFlightNo= v.findViewById(R.id.roundFlightNoTV);
            textToCity= v.findViewById(R.id.roundflyingToTV);
            textFromCity= v.findViewById(R.id.roundflyingFromTV);
            textFlightBusinessPrice= v.findViewById(R.id.roundflightBusinessPriceTV);
            textFlightEconomyPrice= v.findViewById(R.id.roundflightEconomyPriceTV);
            textAirways= v.findViewById(R.id.roundAirwaysTV);
            relativeLayout= v.findViewById(R.id.roundflight_parent);
        }
    }
}
