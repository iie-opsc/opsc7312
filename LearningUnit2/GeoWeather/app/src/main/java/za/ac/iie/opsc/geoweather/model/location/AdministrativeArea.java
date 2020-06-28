package za.ac.iie.opsc.geoweather.model.location;

public class AdministrativeArea {
    private String ID;

    private String LocalizedName;

    private String EnglishName;

    private int Level;

    private String LocalizedType;

    private String EnglishType;

    private String CountryID;

    public void setID(String ID){
        this.ID = ID;
    }
    public String getID(){
        return this.ID;
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
    public void setLevel(int Level){
        this.Level = Level;
    }
    public int getLevel(){
        return this.Level;
    }
    public void setLocalizedType(String LocalizedType){
        this.LocalizedType = LocalizedType;
    }
    public String getLocalizedType(){
        return this.LocalizedType;
    }
    public void setEnglishType(String EnglishType){
        this.EnglishType = EnglishType;
    }
    public String getEnglishType(){
        return this.EnglishType;
    }
    public void setCountryID(String CountryID){
        this.CountryID = CountryID;
    }
    public String getCountryID(){
        return this.CountryID;
    }
}
