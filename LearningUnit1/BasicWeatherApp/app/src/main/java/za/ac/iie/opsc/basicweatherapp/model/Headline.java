package za.ac.iie.opsc.basicweatherapp.model;

import java.util.Date;

public class Headline {
    private String EffectiveDate;

    private int EffectiveEpochDate;

    private int Severity;

    private String Text;

    private String Category;

    private String EndDate;

    private int EndEpochDate;

    private String MobileLink;

    private String Link;

    public void setEffectiveDate(String EffectiveDate){
        this.EffectiveDate = EffectiveDate;
    }
    public String getEffectiveDate(){
        return this.EffectiveDate;
    }
    public void setEffectiveEpochDate(int EffectiveEpochDate){
        this.EffectiveEpochDate = EffectiveEpochDate;
    }
    public int getEffectiveEpochDate(){
        return this.EffectiveEpochDate;
    }
    public void setSeverity(int Severity){
        this.Severity = Severity;
    }
    public int getSeverity(){
        return this.Severity;
    }
    public void setText(String Text){
        this.Text = Text;
    }
    public String getText(){
        return this.Text;
    }
    public void setCategory(String Category){
        this.Category = Category;
    }
    public String getCategory(){
        return this.Category;
    }
    public void setEndDate(String EndDate){
        this.EndDate = EndDate;
    }
    public String getEndDate(){
        return this.EndDate;
    }
    public void setEndEpochDate(int EndEpochDate){
        this.EndEpochDate = EndEpochDate;
    }
    public int getEndEpochDate(){
        return this.EndEpochDate;
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
