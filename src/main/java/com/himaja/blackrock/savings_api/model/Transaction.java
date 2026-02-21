package com.himaja.blackrock.savings_api.model;

import lombok.Data;

@Data
public class Transaction {

    private long timestamp; // epoch millis
    private double amount;
    private double ceiling;
    private double remanent;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getCeiling() {
        return ceiling;
    }

    public void setCeiling(double ceiling) {
        this.ceiling = ceiling;
    }

    public double getRemanent() {
        return remanent;
    }

    public void setRemanent(double remanent) {
        this.remanent = remanent;
    }
}