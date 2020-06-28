package za.ac.iie.opsc.geoweather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import za.ac.iie.opsc.geoweather.model.currentweather.CurrentWeather;
import za.ac.iie.opsc.geoweather.retrofit.IAccuWeather;
import za.ac.iie.opsc.geoweather.retrofit.RetrofitClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrentWeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentWeatherFragment extends Fragment {

    private static final String ARG_LOCATION_NAME = "locationName";
    private static final String ARG_LOCATION_KEY = "locationKey";

    private String locationName;
    private String locationKey;

    private IAccuWeather weatherService;
    private CompositeDisposable compositeDisposable;

    private TextView tvWeatherText;
    private TextView tvTemperature;
    private TextView tvLocationName;

    public CurrentWeatherFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getRetrofit();
        weatherService = retrofit.create(IAccuWeather.class);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param locationName The localised location name.
     * @param locationKey The location key.
     * @return A new instance of fragment CurrentWeatherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrentWeatherFragment newInstance(String locationName, String locationKey) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LOCATION_NAME, locationName);
        args.putString(ARG_LOCATION_KEY, locationKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            locationName = getArguments().getString(ARG_LOCATION_NAME);
            locationKey = getArguments().getString(ARG_LOCATION_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        tvWeatherText = view.findViewById(R.id.tv_weathertext);
        tvTemperature = view.findViewById(R.id.tv_temperature);
        tvLocationName = view.findViewById(R.id.tv_locationname);
        tvLocationName.setText(locationName);
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
        return view;
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