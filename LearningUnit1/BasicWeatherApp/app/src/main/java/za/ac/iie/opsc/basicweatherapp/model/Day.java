package za.ac.iie.opsc.basicweatherapp.model;

public class Day {
    private int Icon;

    private String IconPhrase;

    private boolean HasPrecipitation;

    public void setIcon(int Icon){
        this.Icon = Icon;
    }
    public int getIcon(){
        return this.Icon;
    }
    public void setIconPhrase(String IconPhrase){
        this.IconPhrase = IconPhrase;
    }
    public String getIconPhrase(){
        return this.IconPhrase;
    }
    public void setHasPrecipitation(boolean HasPrecipitation){
        this.HasPrecipitation = HasPrecipitation;
    }
    public boolean getHasPrecipitation(){
        return this.HasPrecipitation;
    }
}
