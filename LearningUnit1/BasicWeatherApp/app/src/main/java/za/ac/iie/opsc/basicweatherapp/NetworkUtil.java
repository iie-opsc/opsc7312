package za.ac.iie.opsc.basicweatherapp;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Utility class that handles the network connections to AccuWeather.
 */
public class NetworkUtil {

    private static final String WEATHERBASE_URL =
        "https://dataservice.accuweather.com/forecasts/v1/daily/5day/305605";
    private static final String PARAM_METRIC ="metric";
    private static final String METRIC_VALUE = "true";
    private static final String PARAM_API_KEY = "apikey";
    private static final String LOGGING_TAG = "URLWECREATED";

    private NetworkUtil() {
    }

    public static URL buildURLForWeather() {

        Uri buildUri = Uri.parse(WEATHERBASE_URL).buildUpon()
                .appendQueryParameter(PARAM_API_KEY,
                        BuildConfig.ACCUWEATHER_API_KEY) // passing in api key
                .appendQueryParameter(PARAM_METRIC,
                        METRIC_VALUE) // passing in metric as measurement unit
                .build();

        URL url = null;

        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.i(LOGGING_TAG, "buildURLForWeather: " + url);

        return url;
    }
}
