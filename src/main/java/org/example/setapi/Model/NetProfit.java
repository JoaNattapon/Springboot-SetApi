package org.example.setapi.Model;

public class NetProfit {

    private Long id;

    private int quarter;
    private float millions;
    private int year;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public float getMillions() {
        return millions;
    }

    public void setMillions(float millions) {
        this.millions = millions;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
