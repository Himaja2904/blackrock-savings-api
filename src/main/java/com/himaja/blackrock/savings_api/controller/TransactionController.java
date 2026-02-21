package com.himaja.blackrock.savings_api.controller;

import com.himaja.blackrock.savings_api.model.Expense;
import com.himaja.blackrock.savings_api.model.FilterRequest;
import com.himaja.blackrock.savings_api.model.SavingsByPeriod;
import com.himaja.blackrock.savings_api.model.Transaction;
import com.himaja.blackrock.savings_api.service.RuleEngineService;
import com.himaja.blackrock.savings_api.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blackrock/challenge/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    private final RuleEngineService ruleEngineService;

    public TransactionController(TransactionService transactionService,
                                 RuleEngineService ruleEngineService) {
        this.transactionService = transactionService;
        this.ruleEngineService = ruleEngineService;
    }

    @PostMapping("/parse")
    public List<Transaction> parse(@RequestBody List<Expense> expenses) {
        return transactionService.buildTransactions(expenses);
    }

    @PostMapping("/filter")
    public List<SavingsByPeriod> filter(@RequestBody FilterRequest request) {

        return ruleEngineService.applyRules(
                request.getTransactions(),
                request.getQ(),
                request.getP(),
                request.getK()
        );
    }
}