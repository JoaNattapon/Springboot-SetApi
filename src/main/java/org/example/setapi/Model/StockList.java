package org.example.setapi.Model;

public record StockList(String symbol, float dividendYield, double pbv,
                        double marketCap, double totalVolume) {

}
