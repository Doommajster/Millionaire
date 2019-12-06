package com.example.domin.tamz_ukol_4;

public class otazky {
    public static otazky otazky = new otazky();
    public String[] otazky_easy={};
    public Integer[] odpovedi_easy={};
    public Integer[] odpovedi_medium={};
    public Integer[] odpovedi_hard={};
    public String[] otazky_medium={};
    public String[] otazky_hard={};

    public String[] getOtazky_easy() {
        return otazky_easy;
    }

    public void setOtazky_easy(String[] otazky){
        this.otazky_easy=otazky;
    }
    public Integer[] getOdpovedi_easy() {
        return odpovedi_easy;
    }

    public void setOdpovedi_easy(Integer[] odpovedi){
        this.odpovedi_easy=odpovedi;
    }

    public String[] getOtazky_hard() {
        return otazky_hard;
    }

    public void setOtazky_hard(String[] otazky){
        this.otazky_hard=otazky;
    }
    public Integer[] getOdpovedi_hard() {
        return odpovedi_hard;
    }

    public void setOdpovedi_hard(Integer[] odpovedi){
        this.odpovedi_hard=odpovedi;
    }

    public String[] getOtazky_medium() {
        return otazky_medium;
    }

    public void setOtazky_medium(String[] otazky){
        this.otazky_medium=otazky;
    }
    public Integer[] getOdpovedi_medium() {
        return odpovedi_medium;
    }

    public void setOdpovedi_medium(Integer[] odpovedi){
        this.odpovedi_medium=odpovedi;
    }
}
