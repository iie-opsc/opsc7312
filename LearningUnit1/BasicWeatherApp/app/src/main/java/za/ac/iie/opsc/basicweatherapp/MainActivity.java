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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tvWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView ivAccuWeather = findViewById(R.id.iv_accuweather);
        ivAccuWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.accuweather.com/"));
                startActivity(intent);
            }
        });

        tvWeather = findViewById(R.id.tv_weather);

        URL url = NetworkUtil.buildURLForWeather();
        new FetchWeatherData().execute(url);
    }

    /**
     * Asynchronous task that requests weather data.
     */
    class FetchWeatherData extends AsyncTask<URL, Void, String> {

        private ArrayList<Forecast> fiveDaylList = new ArrayList<Forecast>();
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
            if (fiveDaylList != null) {
                fiveDaylList.clear();
            }

            if (weatherJSON != null) {
                try {
                    // get the root JSON object
                    JSONObject rootWeatherData = new JSONObject(weatherJSON);
                    // find the daily forecasts array
                    JSONArray fiveDayForecast = rootWeatherData.getJSONArray("DailyForecasts");

                    // get data from each entry in the array
                    for (int i = 0 ; i < fiveDayForecast.length(); i++) {
                        Forecast forecastObject = new Forecast();

                        JSONObject dailyWeather = fiveDayForecast.getJSONObject(i);

                        // get date
                        String date = dailyWeather.getString("Date");
                        Log.i(LOGGING_TAG, "consumeJson: Date" + date);
                        forecastObject.setDate(date);

                        // get minimum temperature
                        JSONObject temperatureObject = dailyWeather.getJSONObject("Temperature");
                        JSONObject minTempObject = temperatureObject.getJSONObject("Minimum");
                        String minTemp = minTempObject.getString("Value");
                        Log.i(LOGGING_TAG, "consumeJson: minTemp" + minTemp);
                        forecastObject.setMinimumTemperature(minTemp);

                        // get maximum temperature
                        JSONObject maxTempObject = temperatureObject.getJSONObject("Maximum");
                        String maxTemp = maxTempObject.getString("Value");
                        Log.i(LOGGING_TAG, "consumeJson: maxTemp" + maxTemp);
                        forecastObject.setMaximumTemperature(maxTemp);

                        fiveDaylList.add(forecastObject);

                        tvWeather.append("Date: " + date + " Min: " + minTemp + " Max: " + maxTemp + "\n");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}