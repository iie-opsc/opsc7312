package com.example.betterweather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.betterweather.Adapter.ForecastAdapter;
import com.example.betterweather.Common.Common;
import com.example.betterweather.Model.Forecast;
import com.example.betterweather.Retrofit.IOpenWeather;
import com.example.betterweather.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class five_day_forecast extends Fragment {

    TextView city;
    RecyclerView forecastRecyclerView;
    CompositeDisposable compositeDisposable;
    IOpenWeather service;

   private static  five_day_forecast instance= null;

    public static five_day_forecast getInstance() {
        if (instance == null)
            return new five_day_forecast();
        return instance;
    }

    public five_day_forecast()
    {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getRetrofit();
        service = retrofit.create(IOpenWeather.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemview =  inflater.inflate(R.layout.fragment_five_day_forecast, container, false);
        city = itemview.findViewById(R.id.txt_five_day_cityName);

        forecastRecyclerView = itemview.findViewById(R.id.five_day_forecastRecycler);
        forecastRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false));
        forecastRecyclerView.setHasFixedSize(true);

        getFiveDayForecastInfo();
        return itemview;
    }

    public void getFiveDayForecastInfo()
    {
        compositeDisposable.add(service.getForecastsLatAndLon
                (
                String.valueOf(Common.currentLocation.getLongitude()),
                String.valueOf(Common.currentLocation.getLatitude()),
                Common.APP_ID,
                "metric")
        .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Forecast>()
                {
                    @Override
                    public void accept(Forecast forecast) throws Exception
                    {

                        displayView(forecast);


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception
                    {
                        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("MYERROR", "accept: " + throwable.getMessage());
                    }
                }));
    }

    private void displayView(Forecast forecast)
    {

       city.setText(forecast.city.name);
        ForecastAdapter adapter = new ForecastAdapter(getContext(),forecast);
        forecastRecyclerView.setAdapter(adapter);
    }


}
