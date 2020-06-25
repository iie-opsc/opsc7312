package za.ac.iie.opsc.basicweatherapp.model;

public class Minimum {
    private float Value;

    private String Unit;

    private int UnitType;

    public void setValue(float Value){
        this.Value = Value;
    }
    public float getValue(){
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
