package za.ac.iie.opsc.geoweather.retrofit;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import za.ac.iie.opsc.geoweather.model.FiveDayForecast;
import za.ac.iie.opsc.geoweather.model.currentweather.CurrentWeather;
import za.ac.iie.opsc.geoweather.model.location.AccuWeatherLocation;

public interface IAccuWeather {
    /**
     * Get the five-day forecast for a specific location key
     * @param locationKey The key for the location
     * @param apiKey The api key to use
     * @param metric Whether to get the data in metric units of measurement
     * @return The five-day forecast
     */
    @GET("forecasts/v1/daily/5day/{locationKey}")
    Observable<FiveDayForecast> getFiveDayForecast(
            @Path("locationKey") String locationKey,
            @Query("apikey") String apiKey,
            @Query("metric") boolean metric);

    /**
     * Get the current conditions at a location.
     * @param locationKey The key for the location
     * @param apiKey The api key to use
     * @return The current conditions at the location
     */
    @GET("currentconditions/v1/{locationKey}")
    Observable<List<CurrentWeather>> getCurrentConditions(
            @Path("locationKey") String locationKey,
            @Query("apikey") String apiKey);

    /**
     * Gets the location data based on the geoposition.
     * @param geoposition The geoposition as latitude,longitude
     * @param apiKey The api key to use
     * @return The location data for the geoposition
     */
    @GET("locations/v1/cities/geoposition/search")
    Observable<AccuWeatherLocation> getLocationByPosition(
            @Query("q") String geoposition,
            @Query("apikey") String apiKey);

    /**
     * Get the location information for the top 150 cities worldide.
     * @param apiKey The API key to use.
     * @return A list of 150 top cities.
     */
    @GET("locations/v1/topcities/150")
    Observable<List<AccuWeatherLocation>> getTop150Cities(
            @Query("apikey") String apiKey);
}
