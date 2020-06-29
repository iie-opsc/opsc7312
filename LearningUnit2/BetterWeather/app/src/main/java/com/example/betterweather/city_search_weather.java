package com.example.betterweather;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.betterweather.Common.Common;
import com.example.betterweather.Model.WeatherResult;
import com.example.betterweather.Retrofit.IOpenWeather;
import com.example.betterweather.Retrofit.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.label305.asynctask.SimpleAsyncTask;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class city_search_weather extends Fragment {

    CompositeDisposable compositeDisposable;
    IOpenWeather service;
    ImageView cityWeatherImage;
    TextView temperature, description;
    CardView cityCardView;
    MaterialSearchBar materialSearchBar;
    private List<String> cityNames;

    private static city_search_weather instance = null;

    public static city_search_weather getInstance() {
        if (instance == null)
            return new city_search_weather();
        return instance;
    }

    public city_search_weather()
    {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getRetrofit();
        service = retrofit.create(IOpenWeather.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_city_search_weather, container, false);

        cityWeatherImage = itemView.findViewById(R.id.city_weather_image);
        temperature = itemView.findViewById(R.id.txt_city_weatherValue);
        description = itemView.findViewById(R.id.txt_city_description);
        cityCardView = itemView.findViewById(R.id.city_cardview);
        cityCardView.setVisibility(View.GONE);
        materialSearchBar = itemView.findViewById(R.id.sb_city_name);
        materialSearchBar.setEnabled(false);


        new LoadCities().execute();

        return itemView;
    }

    private class LoadCities extends SimpleAsyncTask<List<String>> {


        @Override
        protected List<String> doInBackgroundSimple() {
            cityNames = new ArrayList<>();

            try {
                StringBuilder builder = new StringBuilder();
                InputStream in = getResources().openRawResource(R.raw.city_list);
                GZIPInputStream gzipInputStream = new GZIPInputStream(in);
                InputStreamReader reader = new InputStreamReader(gzipInputStream);
                BufferedReader finalReader = new BufferedReader(reader);

                String cities;

                while ((cities = finalReader.readLine()) != null) {
                    builder.append(cities);
                    cityNames = new Gson().fromJson(builder.toString(), new TypeToken<List<String>>() {
                    }.getType());
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return cityNames;
        }

        @Override
        protected void onSuccess(final List<String> cityNames) {
            super.onSuccess(cityNames);

            materialSearchBar.setEnabled(true);
            materialSearchBar.addTextChangeListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    List<String> suggest = new ArrayList<>();
                    for (String search : cityNames)
                    {
                        if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                            suggest.add(search);

                    }

                    materialSearchBar.setLastSuggestions(suggest);
                }


                @Override
                public void afterTextChanged(Editable s) {

                }
            });
           materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
               @Override
               public void onSearchStateChanged(boolean enabled) {
                  // Toast.makeText(getContext(), "boom", Toast.LENGTH_SHORT).show();

               }

               @Override
               public void onSearchConfirmed(CharSequence text) {

                    cityCardView.setVisibility(View.VISIBLE);
                   getWeatherbyCity(text.toString());

               }

               @Override
               public void onButtonClicked(int buttonCode)
               {
                  // Toast.makeText(getContext(), "BAAAAM", Toast.LENGTH_SHORT).show();

               }
           });
            materialSearchBar.setLastSuggestions(cityNames);


        }



    }

    public void getWeatherbyCity(String city)
    {
        compositeDisposable.add(service.getWeatherbyCity(city,Common.APP_ID,"metric")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<WeatherResult>() {
            @Override
            public void accept(WeatherResult weatherResult) throws Exception {

                Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/").append(weatherResult
                        .getWeather().get(0).getIcon()).append(".png").toString()).into(cityWeatherImage);

                temperature.setText(String.valueOf(weatherResult.getMain().getTemp()));
                Toast.makeText(getContext(), String.valueOf(weatherResult.getMain().getTemp()), Toast.LENGTH_SHORT).show();
                description.setText(new StringBuilder(String.valueOf(weatherResult.getWeather().get(0)
                        .getDescription())));


            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d("CityError", "accept: "+ throwable.getMessage());
                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }));
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
