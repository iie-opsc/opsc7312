package za.ac.iie.opsc.basicweatherapp.model;

public class Temperature {
    private Minimum Minimum;

    private Maximum Maximum;

    public void setMinimum(Minimum Minimum){
        this.Minimum = Minimum;
    }
    public Minimum getMinimum(){
        return this.Minimum;
    }
    public void setMaximum(Maximum Maximum){
        this.Maximum = Maximum;
    }
    public Maximum getMaximum(){
        return this.Maximum;
    }
}
