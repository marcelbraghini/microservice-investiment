package br.com.marcelbraghini.adapters.resources.domain;

import io.quarkus.mongodb.panache.MongoEntity;

import java.math.BigDecimal;
import java.util.List;

@MongoEntity(collection="wallet")
public class Wallet {

    private Type type;

    private BigDecimal totalValue;

    private List<Coin> coins;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }

    public static class Builder {

        private Type type;

        private BigDecimal totalValue;

        private List<Coin> coins;

        public Wallet.Builder withType(final Type type) {
            this.type = type;
            return this;
        }

        public Wallet.Builder withTotalValue(final BigDecimal totalValue) {
            this.totalValue = totalValue;
            return this;
        }

        public Wallet.Builder withCoins(final List<Coin> coins) {
            this.coins = coins;
            return this;
        }

        public Wallet build() {
            Wallet wallet = new Wallet();
            wallet.type = this.type;
            wallet.totalValue = this.totalValue;
            wallet.coins = this.coins;
            return wallet;
        }
    }
}
