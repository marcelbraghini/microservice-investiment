package br.com.marcelbraghini.adapters.resources.domain;

import io.quarkus.mongodb.panache.MongoEntity;

import java.math.BigDecimal;

@MongoEntity(collection = "walletBase")
public class WalletBase {

    private Type type;

    private CoinAcronym coinAcronym;

    private BigDecimal quantity;

    public WalletBase() {
    }

    public WalletBase(Type type, CoinAcronym coinAcronym, BigDecimal quantity) {
        this.type = type;
        this.coinAcronym = coinAcronym;
        this.quantity = quantity;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public CoinAcronym getCoinAcronym() {
        return coinAcronym;
    }

    public void setCoinAcronym(CoinAcronym coinAcronym) {
        this.coinAcronym = coinAcronym;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public static class Builder {

        private Type type;

        private CoinAcronym coinAcronym;

        private BigDecimal quantity;

        public WalletBase.Builder withType(final Type type) {
            this.type = type;
            return this;
        }

        public WalletBase.Builder withCoinAcronym(final CoinAcronym coinAcronym) {
            this.coinAcronym = coinAcronym;
            return this;
        }

        public WalletBase.Builder withQuantity(final BigDecimal quantity) {
            this.quantity = quantity;
            return this;
        }

        public WalletBase build() {
            WalletBase walletBase = new WalletBase();
            walletBase.type = this.type;
            walletBase.coinAcronym = this.coinAcronym;
            walletBase.quantity = this.quantity;
            return walletBase;
        }
    }
}
