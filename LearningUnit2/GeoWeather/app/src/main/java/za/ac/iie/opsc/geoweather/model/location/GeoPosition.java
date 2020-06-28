package za.ac.iie.opsc.geoweather.model.location;

public class GeoPosition {
    private double Latitude;

    private double Longitude;

    private Elevation Elevation;

    public void setLatitude(double Latitude){
        this.Latitude = Latitude;
    }
    public double getLatitude(){
        return this.Latitude;
    }
    public void setLongitude(double Longitude){
        this.Longitude = Longitude;
    }
    public double getLongitude(){
        return this.Longitude;
    }
    public void setElevation(Elevation Elevation){
        this.Elevation = Elevation;
    }
    public Elevation getElevation(){
        return this.Elevation;
    }
}
