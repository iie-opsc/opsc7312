package com.example.betterweather.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.betterweather.Common.Common;
import com.example.betterweather.Model.Forecast;
import com.example.betterweather.R;
import com.squareup.picasso.Picasso;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.MyViewHolder>
{
    Context context ;
    Forecast forecast;

    public ForecastAdapter(Context contenxt, Forecast forecast) {
        this.context = contenxt;
        this.forecast = forecast;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View  itemview = LayoutInflater.from(context).inflate(R.layout.five_day_weather_items
        ,parent,false);

        return new  MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                .append(forecast.list.get(position).weather.get(0).getIcon())
                .append(".png").toString()).into(holder.weatherImage);


        holder.description.setText(new StringBuilder(String.valueOf(forecast.list.get(position).weather.get(0)
        .getDescription())));

        holder.dateTime.setText(new StringBuilder(Common.convertTime(forecast.list.get(position).getDt())));

        holder.temprature.setText(new StringBuilder(String.valueOf(forecast.list.get(position).main.getTemp()))
        .append("°C"));

    }

    @Override
    public int getItemCount()
    {
        // returns the size of the list that contains our JSON
        return forecast.list.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder
    {
        // class that decined what our RecyclerView items contain
        ImageView weatherImage;
        TextView dateTime, temprature, description;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            weatherImage = itemView.findViewById(R.id.five_day_weather_image);
            dateTime = itemView.findViewById(R.id.txt_date_time);
            temprature = itemView.findViewById(R.id.txt_5_day_weatherValue);
            description = itemView.findViewById(R.id.txt_description);

        }
    }
}
