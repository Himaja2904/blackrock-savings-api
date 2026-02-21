package com.himaja.blackrock.savings_api.model;

import lombok.Data;

@Data
public class SavingsByPeriod {

    private long start;
    private long end;
    private double amount;

    public SavingsByPeriod(long start, long end, double amount) {
        this.start = start;
        this.end = end;
        this.amount = amount;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}