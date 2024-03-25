package org.example.setapi.Model;

import java.util.List;

public class StockListFiveYear {

    private String symbol;

    private List<Float>  dividendLastFiveYear;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<Float> getDividendLastFiveYear() {
        return dividendLastFiveYear;
    }

    public void setDividendLastFiveYear(List<Float> dividendLastFiveYear) {
        this.dividendLastFiveYear = dividendLastFiveYear;
    }
}
