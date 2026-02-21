package com.himaja.blackrock.savings_api.controller;

import com.himaja.blackrock.savings_api.model.*;
import com.himaja.blackrock.savings_api.service.ReturnsService;
import com.himaja.blackrock.savings_api.service.RuleEngineService;
import com.himaja.blackrock.savings_api.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blackrock/challenge/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    private final RuleEngineService ruleEngineService;

    private final ReturnsService returnsService;

    public TransactionController(
            TransactionService transactionService,
            RuleEngineService ruleEngineService,
            ReturnsService returnsService) {

        this.transactionService = transactionService;
        this.ruleEngineService = ruleEngineService;
        this.returnsService = returnsService;
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

    @PostMapping("/returns/index")
    public List<SavingsByPeriod> indexReturns(
            @RequestBody ReturnsRequest request) {

        return returnsService.calculateIndexReturns(
                request.getSavings(),
                request.getAge(),
                request.getInflation()
        );
    }


    @PostMapping("/returns/nps")
    public List<SavingsByPeriod> npsReturns(
            @RequestBody ReturnsRequest request) {

        return returnsService.calculateNpsReturns(
                request.getSavings(),
                request.getAge(),
                request.getInflation(),
                request.getWage()
        );
    }
}