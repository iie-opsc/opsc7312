package za.ac.iie.opsc.geoweather.model.location;

public class TimeZone {
    private String Code;

    private String Name;

    private float GmtOffset;

    private boolean IsDaylightSaving;

    private String NextOffsetChange;

    public void setCode(String Code){
        this.Code = Code;
    }
    public String getCode(){
        return this.Code;
    }
    public void setName(String Name){
        this.Name = Name;
    }
    public String getName(){
        return this.Name;
    }
    public void setGmtOffset(float GmtOffset){
        this.GmtOffset = GmtOffset;
    }
    public float getGmtOffset(){
        return this.GmtOffset;
    }
    public void setIsDaylightSaving(boolean IsDaylightSaving){
        this.IsDaylightSaving = IsDaylightSaving;
    }
    public boolean getIsDaylightSaving(){
        return this.IsDaylightSaving;
    }
    public void setNextOffsetChange(String NextOffsetChange){
        this.NextOffsetChange = NextOffsetChange;
    }
    public String getNextOffsetChange(){
        return this.NextOffsetChange;
    }
}
