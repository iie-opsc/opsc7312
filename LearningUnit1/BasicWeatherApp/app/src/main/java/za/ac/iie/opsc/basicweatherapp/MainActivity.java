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

import java.io.IOException;
import java.net.URL;

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
                tvWeather.setText(weatherData);
            }
            super.onPostExecute(weatherData);
        }
    }
}