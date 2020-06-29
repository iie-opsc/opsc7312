package com.example.betterweather.Model;

import java.util.List;

public class Forecast
{
    public String cod ;
    public double message ;
    public List<fiveDayForecast> list ;
    public City city ;


    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public List<fiveDayForecast> getList() {
        return list;
    }

    public void setList(List<fiveDayForecast> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
