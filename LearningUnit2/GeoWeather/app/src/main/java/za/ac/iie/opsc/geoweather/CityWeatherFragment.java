package za.ac.iie.opsc.geoweather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import za.ac.iie.opsc.geoweather.model.currentweather.CurrentWeather;
import za.ac.iie.opsc.geoweather.model.location.AccuWeatherLocation;
import za.ac.iie.opsc.geoweather.retrofit.IAccuWeather;
import za.ac.iie.opsc.geoweather.retrofit.RetrofitClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CityWeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CityWeatherFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private IAccuWeather weatherService;
    private CompositeDisposable compositeDisposable;

    private HashMap<String, String> citiesHashMap;

    private MaterialSearchBar materialSearchBar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView tvWeatherText;
    private TextView tvTemperature;
    private TextView tvLocationName;

    public CityWeatherFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getRetrofit();
        weatherService = retrofit.create(IAccuWeather.class);
        citiesHashMap = new HashMap<>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CityWeatherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CityWeatherFragment newInstance(String param1, String param2) {
        CityWeatherFragment fragment = new CityWeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_city_weather, container, false);
        materialSearchBar = view.findViewById(R.id.sb_city_name);
        materialSearchBar.setEnabled(false);
        tvWeatherText = view.findViewById(R.id.tv_weathertext);
        tvTemperature = view.findViewById(R.id.tv_temperature);
        tvLocationName = view.findViewById(R.id.tv_locationname);

        compositeDisposable.add(weatherService.getTop150Cities
                (BuildConfig.ACCUWEATHER_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AccuWeatherLocation>>()
                {
                    @Override
                    public void accept(List<AccuWeatherLocation> topCities) throws Exception {
                        if (!topCities.isEmpty()) {
                            populateSearchData(topCities);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception
                    {
                        Log.d("MYERROR", "accept: " + throwable.getMessage());
                    }
                }));
        return view;
    }

    private void populateSearchData(List<AccuWeatherLocation> topCities) {

        citiesHashMap.clear();
        for (AccuWeatherLocation city : topCities) {
            citiesHashMap.put(city.getLocalizedName(), city.getKey());
        }

        materialSearchBar.setEnabled(true);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                List<String> suggest = new ArrayList<>();
                for (String city : citiesHashMap.keySet()) {
                    if (city.toLowerCase().contains(
                            materialSearchBar.getText().toLowerCase()))
                        suggest.add(city);
                }
                Collections.sort(suggest);
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        materialSearchBar.setOnSearchActionListener(
                new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Log.d("Search:", text + "");
                getWeatherByCityName(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {
            }
        });

        ArrayList suggestionsList = new ArrayList(citiesHashMap.keySet());
        Collections.sort(suggestionsList);
        materialSearchBar.setLastSuggestions(suggestionsList);
    }

    private void getWeatherByCityName(String cityName) {
        tvLocationName.setText(cityName);
        String locationKey = citiesHashMap.get(cityName.toString());
        compositeDisposable.add(weatherService.getCurrentConditions
                (
                        locationKey,
                        BuildConfig.ACCUWEATHER_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CurrentWeather>>()
                {
                    @Override
                    public void accept(List<CurrentWeather> weather) throws Exception {
                        if (!weather.isEmpty()) {
                            displayData(weather.get(0));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception
                    {
                        Log.d("MYERROR", "accept: " + throwable.getMessage());
                    }
                }));
    }

    private void displayData(CurrentWeather weather) {
        tvWeatherText.setText(weather.getWeatherText());
        tvTemperature.setText(weather.getTemperature().getMetric().getValue() +
                " " + weather.getTemperature().getMetric().getUnit());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}