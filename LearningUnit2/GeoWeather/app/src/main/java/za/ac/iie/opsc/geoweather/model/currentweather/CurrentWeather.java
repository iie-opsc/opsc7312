package za.ac.iie.opsc.geoweather.model.currentweather;

public class CurrentWeather {
    private String LocalObservationDateTime;

    private int EpochTime;

    private String WeatherText;

    private int WeatherIcon;

    private boolean HasPrecipitation;

    private String PrecipitationType;

    private boolean IsDayTime;

    private Temperature Temperature;

    private String MobileLink;

    private String Link;

    public void setLocalObservationDateTime(String LocalObservationDateTime){
        this.LocalObservationDateTime = LocalObservationDateTime;
    }
    public String getLocalObservationDateTime(){
        return this.LocalObservationDateTime;
    }
    public void setEpochTime(int EpochTime){
        this.EpochTime = EpochTime;
    }
    public int getEpochTime(){
        return this.EpochTime;
    }
    public void setWeatherText(String WeatherText){
        this.WeatherText = WeatherText;
    }
    public String getWeatherText(){
        return this.WeatherText;
    }
    public void setWeatherIcon(int WeatherIcon){
        this.WeatherIcon = WeatherIcon;
    }
    public int getWeatherIcon(){
        return this.WeatherIcon;
    }
    public void setHasPrecipitation(boolean HasPrecipitation){
        this.HasPrecipitation = HasPrecipitation;
    }
    public boolean getHasPrecipitation(){
        return this.HasPrecipitation;
    }
    public void setPrecipitationType(String PrecipitationType){
        this.PrecipitationType = PrecipitationType;
    }
    public String getPrecipitationType(){
        return this.PrecipitationType;
    }
    public void setIsDayTime(boolean IsDayTime){
        this.IsDayTime = IsDayTime;
    }
    public boolean getIsDayTime(){
        return this.IsDayTime;
    }
    public void setTemperature(Temperature Temperature){
        this.Temperature = Temperature;
    }
    public Temperature getTemperature(){
        return this.Temperature;
    }
    public void setMobileLink(String MobileLink){
        this.MobileLink = MobileLink;
    }
    public String getMobileLink(){
        return this.MobileLink;
    }
    public void setLink(String Link){
        this.Link = Link;
    }
    public String getLink(){
        return this.Link;
    }
}
