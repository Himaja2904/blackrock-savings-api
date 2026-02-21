package com.himaja.blackrock.savings_api.controller;

import com.himaja.blackrock.savings_api.model.Expense;
import com.himaja.blackrock.savings_api.model.Transaction;
import com.himaja.blackrock.savings_api.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blackrock/challenge/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/parse")
    public List<Transaction> parse(@RequestBody List<Expense> expenses) {
        return transactionService.buildTransactions(expenses);
    }
}