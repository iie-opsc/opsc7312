package za.ac.iie.opsc.basicweatherapp.model;

import java.util.List;

public class Root {
    private Headline Headline;

    private List<DailyForecasts> DailyForecasts;

    public void setHeadline(Headline Headline){
        this.Headline = Headline;
    }
    public Headline getHeadline(){
        return this.Headline;
    }
    public void setDailyForecasts(List<DailyForecasts> DailyForecasts){
        this.DailyForecasts = DailyForecasts;
    }
    public List<DailyForecasts> getDailyForecasts(){
        return this.DailyForecasts;
    }
}
