package br.com.marcelbraghini.infrastructure.brasilbitcoin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Coin {

    private String last;

    private String max;

    private String min;

    private String buy;

    private String sell;

    private String open;

    private String vol;

    private String trade;

    private String trades;

    private String vwap;

    private String money;

    public String getLast() {
        return last;
    }

    public String getMax() {
        return max;
    }

    public String getMin() {
        return min;
    }

    public String getBuy() {
        return buy;
    }

    public String getSell() {
        return sell;
    }

    public String getOpen() {
        return open;
    }

    public String getVol() {
        return vol;
    }

    public String getTrade() {
        return trade;
    }

    public String getTrades() {
        return trades;
    }

    public String getVwap() {
        return vwap;
    }

    public String getMoney() {
        return money;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public void setTrades(String trades) {
        this.trades = trades;
    }

    public void setVwap(String vwap) {
        this.vwap = vwap;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "last='" + last + '\'' +
                ", max='" + max + '\'' +
                ", min='" + min + '\'' +
                ", buy='" + buy + '\'' +
                ", sell='" + sell + '\'' +
                ", open='" + open + '\'' +
                ", vol='" + vol + '\'' +
                ", trade='" + trade + '\'' +
                ", trades='" + trades + '\'' +
                ", vwap='" + vwap + '\'' +
                ", money='" + money + '\'' +
                '}';
    }
}
