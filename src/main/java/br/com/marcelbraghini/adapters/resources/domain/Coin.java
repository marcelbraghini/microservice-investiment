package br.com.marcelbraghini.adapters.resources.domain;

import java.math.BigDecimal;

public class Coin {

    private CoinAcronym coinAcronym;

    private BigDecimal price;

    private BigDecimal fraction;

    private BigDecimal totalValue;

    public CoinAcronym getCoinAcronym() {
        return coinAcronym;
    }

    public void setCoinAcronym(CoinAcronym coinAcronym) {
        this.coinAcronym = coinAcronym;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getFraction() {
        return fraction;
    }

    public void setFraction(BigDecimal fraction) {
        this.fraction = fraction;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public static class Builder {

        private CoinAcronym coinAcronym;

        private BigDecimal price;

        private BigDecimal fraction;

        private BigDecimal totalValue;

        public Coin.Builder withCoinAcronym(final CoinAcronym coinAcronym) {
            this.coinAcronym = coinAcronym;
            return this;
        }

        public Coin.Builder withPrice(final BigDecimal price) {
            this.price = price;
            return this;
        }

        public Coin.Builder withFraction(final BigDecimal fraction) {
            this.fraction = fraction;
            return this;
        }

        public Coin.Builder withTotalValue(final BigDecimal totalValue) {
            this.totalValue = totalValue;
            return this;
        }

        public Coin build() {
            Coin coin = new Coin();
            coin.coinAcronym = this.coinAcronym;
            coin.price = this.price;
            coin.fraction = this.fraction;
            coin.totalValue = this.totalValue;
            return coin;
        }
    }
}
