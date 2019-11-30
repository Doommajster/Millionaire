package com.example.domin.tamz_ukol_4;



public class PolozkyDb {

    long id;
    String datum;
    int score;

    public PolozkyDb(long id, String datum, int score) {

        this.id = id;
        this.datum = datum;
        this.score = score;

    }

    public int getScore() {
        return score;
    }

    public String getDatum() {
        return datum;
    }
}
