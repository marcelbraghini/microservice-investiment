package br.com.marcelbraghini.entities;

import io.quarkus.mongodb.panache.MongoEntity;
import org.bson.types.ObjectId;

import java.math.BigDecimal;

@MongoEntity(collection = "walletBase")
public class WalletBase {

    public ObjectId id;

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

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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
}
