package br.com.marcelbraghini.entities;

import io.quarkus.mongodb.panache.MongoEntity;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.List;

@MongoEntity(collection="wallet")
public class Wallet {

    private ObjectId id;

    private List<Coin> coins;

    private BigDecimal totalValue;

    private Type type;

    public Wallet() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public Type getType() {
        return type;
    }

    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                "coins=" + coins +
                ", totalValue=" + totalValue +
                ", type=" + type +
                '}';
    }

    public void updateWallet(final List<Coin> coins, final BigDecimal totalValue) {
        this.coins = coins;
        this.totalValue = totalValue;
    }

    public static class Builder {

        private Type type;

        private BigDecimal totalValue;

        private List<Coin> coins;

        public Builder withType(final Type type) {
            this.type = type;
            return this;
        }

        public Builder withTotalValue(final BigDecimal totalValue) {
            this.totalValue = totalValue;
            return this;
        }

        public Builder withCoins(final List<Coin> coins) {
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
