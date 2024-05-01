package org.example.setapi.Model;

public class WishListStock {

    private Long id;
    private String symbol;
    private String business_describe;
    private int netProfit_Quarter_ML;
    private Situation situation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getBusiness_describe() {
        return business_describe;
    }

    public void setBusiness_describe(String business_describe) {
        this.business_describe = business_describe;
    }

    public int getNetProfit_Quarter_ML() {
        return netProfit_Quarter_ML;
    }

    public void setNetProfit_Quarter_ML(int netProfit_Quarter_ML) {
        this.netProfit_Quarter_ML = netProfit_Quarter_ML;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }
}
