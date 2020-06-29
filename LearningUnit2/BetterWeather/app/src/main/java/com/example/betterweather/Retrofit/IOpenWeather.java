package com.example.betterweather.Retrofit;


import com.example.betterweather.Model.Forecast;

import com.example.betterweather.Model.WeatherResult;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;



public interface IOpenWeather
{
    @GET("weather")
    Observable<WeatherResult> getWeatherLatAndLon(@Query("lat") String lat ,
                                                  @Query("lon") String lon,
                                                  @Query("appid") String appid,
                                                  @Query("units") String units);

    @GET("weather")
    Observable<WeatherResult> getWeatherbyCity(@Query("q") String cityName ,
                                          @Query("appid") String appid,
                                          @Query("units") String units);

    @GET("forecast")
    Observable<Forecast> getForecastsLatAndLon(@Query("lat") String lat ,
                                             @Query("lon") String lon,
                                             @Query("appid") String appid,
                                             @Query("units") String units);





}
