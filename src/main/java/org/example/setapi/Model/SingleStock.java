package org.example.setapi.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class SingleStock {

    private String symbol;
    private float close;
    private int pe;
    private double dividendYield;

    public String getSymbol() {
        return symbol;
    }

    public float getClose() {
        return close;
    }

    public int getPe() {
        return pe;
    }

    public double getDividendYield() {
        return dividendYield;
    }
}
