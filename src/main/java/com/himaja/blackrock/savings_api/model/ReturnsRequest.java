package com.himaja.blackrock.savings_api.model;

import lombok.Data;

import java.util.List;

@Data
public class ReturnsRequest {

    private int age;
    private double wage;
    private double inflation;

    private List<SavingsByPeriod> savings;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWage() {
        return wage;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }

    public double getInflation() {
        return inflation;
    }

    public void setInflation(double inflation) {
        this.inflation = inflation;
    }

    public List<SavingsByPeriod> getSavings() {
        return savings;
    }

    public void setSavings(List<SavingsByPeriod> savings) {
        this.savings = savings;
    }
}