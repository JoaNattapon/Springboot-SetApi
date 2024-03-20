package org.example.setapi.Model;

public class StockListFiveYear {

    private String symbol;
    private float thisYear;
    private float lastYear;
    private float lastTwoYear;
    private float lastThreeYear;
    private float lastFourYear;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public float getThisYear() {
        return thisYear;
    }

    public void setThisYear(float thisYear) {
        this.thisYear = thisYear;
    }

    public float getLastYear() {
        return lastYear;
    }

    public void setLastYear(float lastYear) {
        this.lastYear = lastYear;
    }

    public float getLastTwoYear() {
        return lastTwoYear;
    }

    public void setLastTwoYear(float lastTwoYear) {
        this.lastTwoYear = lastTwoYear;
    }

    public float getLastThreeYear() {
        return lastThreeYear;
    }

    public void setLastThreeYear(float lastThreeYear) {
        this.lastThreeYear = lastThreeYear;
    }

    public float getLastFourYear() {
        return lastFourYear;
    }

    public void setLastFourYear(float lastFourYear) {
        this.lastFourYear = lastFourYear;
    }
}
