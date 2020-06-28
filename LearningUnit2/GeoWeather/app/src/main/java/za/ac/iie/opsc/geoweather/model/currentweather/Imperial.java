package za.ac.iie.opsc.geoweather.model.currentweather;

public class Imperial {
    private int Value;

    private String Unit;

    private int UnitType;

    public void setValue(int Value){
        this.Value = Value;
    }
    public int getValue(){
        return this.Value;
    }
    public void setUnit(String Unit){
        this.Unit = Unit;
    }
    public String getUnit(){
        return this.Unit;
    }
    public void setUnitType(int UnitType){
        this.UnitType = UnitType;
    }
    public int getUnitType(){
        return this.UnitType;
    }
}
