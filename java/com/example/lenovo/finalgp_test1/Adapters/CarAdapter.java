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
import com.example.lenovo.finalgp_test1.Reservations.ReserveCarActivity;
import com.example.lenovo.finalgp_test1.TripPlannerClasses.CarClass;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by LENOVO on 2018-03-06.
 */

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {

    private Context context;
    private List<CarClass> list;

    public CarAdapter(Context context, List<CarClass> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_car, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        final CarClass carClass = list.get(position);
        holder.textCarModel.setText(carClass.getCar_model());
        holder.textCarPrice.setText(String.valueOf(carClass.getRent_price()));
        Picasso.with(this.context).load(carClass.getCar_img()).into(holder.carImg);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "id: " + carClass.getCar_id(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, ReserveCarActivity.class);
                i.putExtra("car_id", carClass.getCar_id());
                i.putExtra("car_model", carClass.getCar_model());
                i.putExtra("car_price", carClass.getRent_price());
                i.putExtra("car_img", carClass.getCar_img());
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
        public TextView textCarModel, textCarPrice;
        public RelativeLayout relativeLayout;
        public ImageView carImg;
        public ViewHolder(View itemView) {
            super(itemView);
            textCarModel= itemView.findViewById(R.id.txtCarModel);
            textCarPrice= itemView.findViewById(R.id.txtCarPrice);
            relativeLayout= itemView.findViewById(R.id.car_parent);
            carImg= itemView.findViewById(R.id.imgCar);
        }
    }
}
