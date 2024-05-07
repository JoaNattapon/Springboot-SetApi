package org.example.setapi.Model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.util.Map;

public class WishListStock {

    private Long id;
    private String symbol;
    private String business_overview;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "net_profit_id", referencedColumnName = "id")
    private NetProfit netProfit;
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
        return business_overview;
    }

    public void setBusiness_describe(String business_overview) {
        this.business_overview = business_overview;
    }

    public NetProfit getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(NetProfit netProfit) {
        this.netProfit = netProfit;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }
}
