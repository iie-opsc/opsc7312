package za.ac.iie.opsc.geoweather.model.location;

import java.util.List;

public class AccuWeatherLocation {
    private int Version;

    private String Key;

    private String Type;

    private int Rank;

    private String LocalizedName;

    private String EnglishName;

    private String PrimaryPostalCode;

    private Region Region;

    private Country Country;

    private AdministrativeArea AdministrativeArea;

    private TimeZone TimeZone;

    private GeoPosition GeoPosition;

    private boolean IsAlias;

    private List<SupplementalAdminAreas> SupplementalAdminAreas;

    private List<String> DataSets;

    public void setVersion(int Version){
        this.Version = Version;
    }
    public int getVersion(){
        return this.Version;
    }
    public void setKey(String Key){
        this.Key = Key;
    }
    public String getKey(){
        return this.Key;
    }
    public void setType(String Type){
        this.Type = Type;
    }
    public String getType(){
        return this.Type;
    }
    public void setRank(int Rank){
        this.Rank = Rank;
    }
    public int getRank(){
        return this.Rank;
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
    public void setPrimaryPostalCode(String PrimaryPostalCode){
        this.PrimaryPostalCode = PrimaryPostalCode;
    }
    public String getPrimaryPostalCode(){
        return this.PrimaryPostalCode;
    }
    public void setRegion(Region Region){
        this.Region = Region;
    }
    public Region getRegion(){
        return this.Region;
    }
    public void setCountry(Country Country){
        this.Country = Country;
    }
    public Country getCountry(){
        return this.Country;
    }
    public void setAdministrativeArea(AdministrativeArea AdministrativeArea){
        this.AdministrativeArea = AdministrativeArea;
    }
    public AdministrativeArea getAdministrativeArea(){
        return this.AdministrativeArea;
    }
    public void setTimeZone(TimeZone TimeZone){
        this.TimeZone = TimeZone;
    }
    public TimeZone getTimeZone(){
        return this.TimeZone;
    }
    public void setGeoPosition(GeoPosition GeoPosition){
        this.GeoPosition = GeoPosition;
    }
    public GeoPosition getGeoPosition(){
        return this.GeoPosition;
    }
    public void setIsAlias(boolean IsAlias){
        this.IsAlias = IsAlias;
    }
    public boolean getIsAlias(){
        return this.IsAlias;
    }
    public void setSupplementalAdminAreas(List<SupplementalAdminAreas> SupplementalAdminAreas){
        this.SupplementalAdminAreas = SupplementalAdminAreas;
    }
    public List<SupplementalAdminAreas> getSupplementalAdminAreas(){
        return this.SupplementalAdminAreas;
    }
    public void setDataSets(List<String> DataSets){
        this.DataSets = DataSets;
    }
    public List<String> getDataSets(){
        return this.DataSets;
    }
}
