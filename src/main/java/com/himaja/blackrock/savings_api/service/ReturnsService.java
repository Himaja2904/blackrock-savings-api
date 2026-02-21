package com.himaja.blackrock.savings_api.service;

import com.himaja.blackrock.savings_api.model.SavingsByPeriod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReturnsService {

    private static final double NPS_RATE = 0.0711;
    private static final double INDEX_RATE = 0.1449;

    public List<SavingsByPeriod> calculateIndexReturns(
            List<SavingsByPeriod> savings,
            int age,
            double inflation) {

        int years = Math.max(60 - age, 5);

        List<SavingsByPeriod> result = new ArrayList<>();

        for (SavingsByPeriod s : savings) {

            double future =
                    s.getAmount() *
                            Math.pow(1 + INDEX_RATE, years);

            double real =
                    future /
                            Math.pow(1 + inflation, years);

            result.add(new SavingsByPeriod(
                    s.getStart(),
                    s.getEnd(),
                    round(real)
            ));
        }

        return result;
    }

    public List<SavingsByPeriod> calculateNpsReturns(
            List<SavingsByPeriod> savings,
            int age,
            double inflation,
            double yearlySalary) {

        int years = Math.max(60 - age, 5);

        List<SavingsByPeriod> result = new ArrayList<>();

        for (SavingsByPeriod s : savings) {

            double invested = s.getAmount();

            double future =
                    invested *
                            Math.pow(1 + NPS_RATE, years);

            double real =
                    future /
                            Math.pow(1 + inflation, years);

            double deduction =
                    Math.min(
                            invested,
                            Math.min(yearlySalary * 0.1, 200000)
                    );

            double taxBenefit =
                    calculateTax(yearlySalary)
                            - calculateTax(yearlySalary - deduction);

            result.add(new SavingsByPeriod(
                    s.getStart(),
                    s.getEnd(),
                    round(real + taxBenefit)
            ));
        }

        return result;
    }

    // Simplified tax slabs
    private double calculateTax(double income) {

        double tax = 0;

        if (income > 1500000) {
            tax += (income - 1500000) * 0.30;
            income = 1500000;
        }

        if (income > 1200000) {
            tax += (income - 1200000) * 0.20;
            income = 1200000;
        }

        if (income > 1000000) {
            tax += (income - 1000000) * 0.15;
            income = 1000000;
        }

        if (income > 700000) {
            tax += (income - 700000) * 0.10;
        }

        return tax;
    }

    private double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}