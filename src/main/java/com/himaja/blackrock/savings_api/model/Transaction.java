package com.himaja.blackrock.savings_api.model;

import lombok.Data;

@Data
public class Transaction {

    private long timestamp; // epoch millis
    private double amount;
    private double ceiling;
    private double remanent;

}