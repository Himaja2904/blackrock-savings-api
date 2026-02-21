package com.himaja.blackrock.savings_api.service;

import com.himaja.blackrock.savings_api.model.Expense;
import com.himaja.blackrock.savings_api.model.Transaction;
import com.himaja.blackrock.savings_api.util.DateUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    public List<Transaction> buildTransactions(List<Expense> expenses) {

        List<Transaction> result = new ArrayList<>();

        for (Expense e : expenses) {

            double amount = e.getAmount();

            double ceiling = Math.ceil(amount / 100.0) * 100;
            double remanent = ceiling - amount;

            Transaction tx = new Transaction();
            tx.setTimestamp(DateUtil.toMillis(e.getDate()));
            tx.setAmount(amount);
            tx.setCeiling(ceiling);
            tx.setRemanent(remanent);

            result.add(tx);
        }

        return result;
    }
}