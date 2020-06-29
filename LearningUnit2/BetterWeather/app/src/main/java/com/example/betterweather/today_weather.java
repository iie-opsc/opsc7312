package com.example.betterweather;

import android.Manifest;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.betterweather.Common.Common;
import com.example.betterweather.Common.ProcessImage;
import com.example.betterweather.Model.WeatherResult;
import com.example.betterweather.Permissions.Permission;
import com.example.betterweather.Retrofit.IOpenWeather;
import com.example.betterweather.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.io.FileOutputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class today_weather extends Fragment {

    private static final String TAG = "location in Fragment";
    CompositeDisposable compositeDisposable;
    IOpenWeather service;
    static today_weather instance;
    ImageView weather_image, insta;
    TextView city_name;
    TextView weather_value;
    TextView wind_speed;
    TextView humidity;
    TextView pressure;
    TextView sunrise;
    TextView sunset;
    TextView location;
    View rootview;
    final int REQUEST_CODE=2;


    public static today_weather getInstance() {
        if (instance == null)
            return new today_weather();
        return instance;
    }

    public today_weather() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getRetrofit();
        service = retrofit.create(IOpenWeather.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.today_weather_fragment, container, false);
        city_name = view.findViewById(R.id.txt_cityName);
        weather_value = view.findViewById(R.id.txt_weatherValeue);
        wind_speed = view.findViewById(R.id.txt_wind_speed);
        humidity = view.findViewById(R.id.txt_humidity);
        pressure = view.findViewById(R.id.txt_pressure);
        sunrise = view.findViewById(R.id.txt_sunrise);
        sunset = view.findViewById(R.id.txt_sunset);
        location = view.findViewById(R.id.txt_location);
        weather_image = view.findViewById(R.id.weather_image);
        insta = view.findViewById(R.id.btn_insta);

        Location location = Common.currentLocation;
        Log.i(TAG, "onCreateView: " + location);

        //get rootview

        rootview = getActivity().getWindow().getDecorView().getRootView();
        getWeatherInformation();
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProcessImage writeBitmap = new ProcessImage();
                Bitmap currentScreenshot = writeBitmap.takeScreenshot(rootview);
                writeBitmap.storeScreenshot(getContext(), currentScreenshot,"Weather Today");
                writeBitmap.pushToInstagram(getContext(),"/Weather Today");


            }
        });
        return view;
    }

    private void getWeatherInformation() {
        compositeDisposable.add(service.getWeatherLatAndLon
                (String.valueOf(Common.currentLocation.getLatitude()), String.valueOf(Common.currentLocation.getLongitude())
                        , Common.APP_ID, "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                               @Override
                               public void accept(WeatherResult weatherResult) throws Exception {
                                   Toast.makeText(getContext(), "ENTERED", Toast.LENGTH_SHORT).show();
                                   city_name.setText(weatherResult.getName());
                                   // Toast.makeText(getActivity(), "HERE" + weatherResult.getName(), Toast.LENGTH_LONG).show();
                                   Log.i(TAG, "MINE: " + weatherResult.getName());
                                   Log.d(TAG, "accept: " + weatherResult.getName());

                                   // Picasso.get().load("https://openweathermap.org/img/wn/04d@2x.png").into(weather_image);
                                   Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/").append(weatherResult
                                           .getWeather().get(0).getIcon()).append(".png").toString()).into(weather_image);


                                   weather_value.setText(String.valueOf(weatherResult.getMain().getTemp()));

                                   pressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure())).append("  hpa").toString());

                                   wind_speed.setText(new StringBuilder(String.valueOf(weatherResult.getWind().getSpeed())).append("  Km/h").toString());

                                   humidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity())).append("  %").toString());

                                   sunrise.setText(Common.convertTime(weatherResult.getSys().getSunrise()));
                                   Toast.makeText(getActivity(), Common.convertTime(weatherResult.getSys().getSunrise()), Toast.LENGTH_SHORT).show();

                                   sunset.setText(Common.convertTime(weatherResult.getSys().getSunset()));

                                   location.setText(new StringBuilder("lat & lon").append(weatherResult.getCoord()));

                               }

                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(getActivity(), "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();

                                   Log.i(TAG, "error: " + throwable.getMessage());

                                   Log.i(TAG, "accept: " + throwable.getMessage());


                               }
                           }
                )
        );


    }



}
