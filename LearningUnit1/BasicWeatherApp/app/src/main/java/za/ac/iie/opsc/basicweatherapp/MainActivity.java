package za.ac.iie.opsc.basicweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import za.ac.iie.opsc.basicweatherapp.model.DailyForecasts;
import za.ac.iie.opsc.basicweatherapp.model.Root;

public class MainActivity extends AppCompatActivity {

    private TextView tvWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvWeather = findViewById(R.id.tv_weather);

        URL url = NetworkUtil.buildURLForWeather();
        new FetchWeatherData().execute(url);
    }

    /**
     * Asynchronous task that requests weather data.
     */
    class FetchWeatherData extends AsyncTask<URL, Void, String> {

        private static final String LOGGING_TAG = "weatherDATA";

        @Override
        protected String doInBackground(URL... urls) {
            URL weatherURL = urls[0];
            String weatherData = null;
            try {
                weatherData =
                        NetworkUtil.getResponseFromHttpUrl(weatherURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return weatherData;
        }

        @Override
        protected void onPostExecute(String weatherData) {
            if (weatherData != null) {
                consumeJson(weatherData);
            }
            super.onPostExecute(weatherData);
        }

        protected void consumeJson(String weatherJSON) {
            if (weatherJSON != null) {
                Gson gson = new Gson();
                Root weatherData = gson.fromJson(weatherJSON, Root.class);
                for (DailyForecasts forecast : weatherData.getDailyForecasts())
                {
                    tvWeather.append("Date: " +
                            forecast.getDate().substring(0, 10) +
                            " Min: " +
                            forecast.getTemperature().getMinimum().getValue() +
                            " Max: " +
                            forecast.getTemperature().getMaximum().getValue() +
                            "\n");
                }
            }
        }
    }
}