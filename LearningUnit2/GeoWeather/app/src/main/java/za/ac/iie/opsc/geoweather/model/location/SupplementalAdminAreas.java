package za.ac.iie.opsc.geoweather.model.location;

public class SupplementalAdminAreas {
    private int Level;

    private String LocalizedName;

    private String EnglishName;

    public void setLevel(int Level){
        this.Level = Level;
    }
    public int getLevel(){
        return this.Level;
    }
    public void setLocalizedName(String LocalizedName){
        this.LocalizedName = LocalizedName;
    }
    public String getLocalizedName(){
        return this.LocalizedName;
    }
    public void setEnglishName(String EnglishName){
        this.EnglishName = EnglishName;
    }
    public String getEnglishName(){
        return this.EnglishName;
    }
}
