package com.example.lenovo.finalgp_test1.Adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.finalgp_test1.R;
import com.example.lenovo.finalgp_test1.TripPlannerClasses.DayClass;

import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder> {

    private List<DayClass> dayClasses;
    private Context context;

    public DayAdapter(Context context, List<DayClass> dayClasses) {
        this.dayClasses = dayClasses;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_day, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final String date = dayClasses.get(position).getDate();
        holder.textDay.setText(date);
        List x = dayClasses.get(position).getToursPerDay();
        TourAdapter tourAdapter = new TourAdapter(context, x);
        holder.daysRec.setHasFixedSize(true);
        holder.daysRec.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.daysRec.setAdapter(tourAdapter);
    }

    @Override
    public int getItemCount() {
        return dayClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textDay;
        public RecyclerView daysRec;
        public ViewHolder(View itemView) {
            super(itemView);
            textDay = itemView.findViewById(R.id.txtDayItienrary);
            daysRec = itemView.findViewById(R.id.daysRec);
        }
    }
}
