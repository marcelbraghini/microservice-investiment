package br.com.marcelbraghini.entities;

import java.math.BigDecimal;

public class Coin {

    private BigDecimal price;
    
    private BigDecimal fraction;
    
    private CoinAcronym coinAcronym;

    private BigDecimal totalValue;

    public Coin() {
    }

    public Coin(BigDecimal price, BigDecimal fraction, CoinAcronym coinAcronym, BigDecimal totalValue) {
        this.price = price;
        this.fraction = fraction;
        this.coinAcronym = coinAcronym;
        this.totalValue = totalValue;
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

    public CoinAcronym getCoinAcronym() {
        return coinAcronym;
    }

    public void setCoinAcronym(CoinAcronym coinAcronym) {
        this.coinAcronym = coinAcronym;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "price=" + price +
                ", fraction=" + fraction +
                ", coinAcronym=" + coinAcronym +
                ", totalValue=" + totalValue +
                '}';
    }

    public static class Builder {

        private BigDecimal price;

        private BigDecimal fraction;

        private CoinAcronym coinAcronym;

        private BigDecimal totalValue;

        public Builder withPrice(final BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder withFraction(final BigDecimal fraction) {
            this.fraction = fraction;
            return this;
        }

        public Builder withCoinAcronym(final CoinAcronym coinAcronym) {
            this.coinAcronym = coinAcronym;
            return this;
        }

        public Builder withTotalValue(final BigDecimal totalValue) {
            this.totalValue = totalValue;
            return this;
        }

        public Coin build() {
            Coin coin = new Coin();
            coin.price = this.price;
            coin.fraction = this.fraction;
            coin.coinAcronym = this.coinAcronym;
            coin.totalValue = this.totalValue;
            return coin;
        }
    }
}
