package com.himaja.blackrock.savings_api.model;

import lombok.Data;
import lombok.Getter;

@Data
public class Expense {

    private String date;   // "YYYY-MM-DD HH:mm:ss"
    private double amount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}