package com.himaja.blackrock.savings_api.model;

import lombok.Data;

@Data
public class Expense {

    private String date;   // "YYYY-MM-DD HH:mm:ss"
    private double amount;

}