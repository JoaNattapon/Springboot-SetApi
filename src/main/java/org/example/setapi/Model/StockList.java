package org.example.setapi.Model;

public class StockList {

    private String symbol;
    private float dividendYield;
    private double pbv;
    private double marketCap;
    private double totalVolume;

    public float getDividendYield() {
        return dividendYield;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setDividendYield(float dividendYield) {
        this.dividendYield = dividendYield;
    }
}
