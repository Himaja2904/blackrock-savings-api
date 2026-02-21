package com.himaja.blackrock.savings_api.service;

import com.himaja.blackrock.savings_api.model.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class RuleEngineServiceTest {

    public static void main(String[] args) {

        // ===============================
        // CHANGE THESE FOR TESTING
        // ===============================
        int TX_COUNT = 1_000_000;   // 1M transactions
        int Q_COUNT  = 2_000;
        int P_COUNT  = 2_000;
        int K_COUNT  = 1_000;
        // ===============================

        System.out.println("=================================");
        System.out.println("Starting Load Test");
        System.out.println("Transactions: " + TX_COUNT);
        System.out.println("Q Periods    : " + Q_COUNT);
        System.out.println("P Periods    : " + P_COUNT);
        System.out.println("K Periods    : " + K_COUNT);
        System.out.println("=================================");

        System.out.println("Generating data...");

        List<Transaction> transactions =
                generateTransactions(TX_COUNT);

        List<QPeriod> qPeriods =
                generateQ(Q_COUNT);

        List<PPeriod> pPeriods =
                generateP(P_COUNT);

        List<KPeriod> kPeriods =
                generateK(K_COUNT);

        RuleEngineService service =
                new RuleEngineService();

        System.out.println("Data ready.");
        System.out.println("Running rule engine...");

        long start = System.currentTimeMillis();

        List<SavingsByPeriod> result =
                service.applyRules(
                        transactions,
                        qPeriods,
                        pPeriods,
                        kPeriods
                );

        long end = System.currentTimeMillis();

        System.out.println("=================================");
        System.out.println("Finished.");
        System.out.println("Result size: " + result.size());
        System.out.println("Time taken: " + (end - start) + " ms");
        System.out.println("=================================");
    }

    /* ===============================
       Data Generators
       =============================== */

    private static List<Transaction> generateTransactions(int n) {

        List<Transaction> list = new ArrayList<>(n);

        long base = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {

            Transaction tx = new Transaction();

            tx.setTimestamp(base + i * 1000L); // every second
            tx.setAmount(100 + (i % 500));
            tx.setCeiling(200);
            tx.setRemanent(100);

            list.add(tx);
        }

        return list;
    }

    private static List<QPeriod> generateQ(int n) {

        List<QPeriod> list = new ArrayList<>(n);

        long base = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {

            QPeriod q = new QPeriod();

            long start = base + i * 5000L;

            q.setStart(start);
            q.setEnd(start + 200_000L); // overlaps
            q.setFixed(50 + (i % 50));

            list.add(q);
        }

        return list;
    }

    private static List<PPeriod> generateP(int n) {

        List<PPeriod> list = new ArrayList<>(n);

        long base = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {

            PPeriod p = new PPeriod();

            long start = base + i * 4000L;

            p.setStart(start);
            p.setEnd(start + 150_000L);
            p.setExtra(5);

            list.add(p);
        }

        return list;
    }

    private static List<KPeriod> generateK(int n) {

        List<KPeriod> list = new ArrayList<>(n);

        long base = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {

            KPeriod k = new KPeriod();

            long start = base + i * 10_000L;

            k.setStart(start);
            k.setEnd(start + 300_000L);

            list.add(k);
        }

        return list;
    }
}