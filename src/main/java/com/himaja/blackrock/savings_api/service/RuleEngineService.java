package com.himaja.blackrock.savings_api.service;

import com.himaja.blackrock.savings_api.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RuleEngineService {

    public List<SavingsByPeriod> applyRules(
            List<Transaction> transactions,
            List<QPeriod> qPeriods,
            List<PPeriod> pPeriods,
            List<KPeriod> kPeriods) {

        if (transactions.isEmpty()) {
            return Collections.emptyList();
        }

        /* -------------------------------
           1. Sort Transactions
        -------------------------------- */
        transactions.sort(
                Comparator.comparingLong(Transaction::getTimestamp)
        );

        int n = transactions.size();

        /* -------------------------------
           2. Apply Q Rules (Override)
        -------------------------------- */
        for (Transaction tx : transactions) {

            QPeriod best = null;

            for (QPeriod q : qPeriods) {

                if (tx.getTimestamp() >= q.getStart()
                        && tx.getTimestamp() <= q.getEnd()) {

                    if (best == null ||
                            q.getStart() > best.getStart()) {
                        best = q;
                    }
                }
            }

            if (best != null) {
                tx.setRemanent(best.getFixed());
            }
        }

        /* -------------------------------
           3. Preprocess P (Sweep Line)
        -------------------------------- */
        TreeMap<Long, Double> pEvents = new TreeMap<>();

        for (PPeriod p : pPeriods) {

            pEvents.put(
                    p.getStart(),
                    pEvents.getOrDefault(p.getStart(), 0.0)
                            + p.getExtra()
            );

            pEvents.put(
                    p.getEnd() + 1,
                    pEvents.getOrDefault(p.getEnd() + 1, 0.0)
                            - p.getExtra()
            );
        }

        double runningExtra = 0;
        Iterator<Map.Entry<Long, Double>> it =
                pEvents.entrySet().iterator();

        Map.Entry<Long, Double> current =
                it.hasNext() ? it.next() : null;

        for (Transaction tx : transactions) {

            while (current != null &&
                    current.getKey() <= tx.getTimestamp()) {

                runningExtra += current.getValue();
                current = it.hasNext() ? it.next() : null;
            }

            tx.setRemanent(
                    tx.getRemanent() + runningExtra
            );
        }

        /* -------------------------------
           4. Build Prefix Sum of Remanent
        -------------------------------- */
        double[] prefix = new double[n];
        long[] times = new long[n];

        for (int i = 0; i < n; i++) {

            times[i] = transactions
                    .get(i)
                    .getTimestamp();

            prefix[i] = transactions
                    .get(i)
                    .getRemanent();

            if (i > 0) {
                prefix[i] += prefix[i - 1];
            }
        }

        /* -------------------------------
           5. Process K using Binary Search
        -------------------------------- */
        List<SavingsByPeriod> result =
                new ArrayList<>();

        for (KPeriod k : kPeriods) {

            int left = lowerBound(
                    times,
                    k.getStart()
            );

            int right = upperBound(
                    times,
                    k.getEnd()
            ) - 1;

            double sum = 0;

            if (left <= right &&
                    left < n &&
                    right >= 0) {

                sum = prefix[right]
                        - (left > 0 ? prefix[left - 1] : 0);
            }

            result.add(
                    new SavingsByPeriod(
                            k.getStart(),
                            k.getEnd(),
                            sum
                    )
            );
        }

        return result;
    }

    /* -------------------------------
       Binary Search Helpers
    -------------------------------- */

    private int lowerBound(long[] arr, long val) {

        int l = 0, r = arr.length;

        while (l < r) {

            int mid = (l + r) / 2;

            if (arr[mid] < val) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        return l;
    }

    private int upperBound(long[] arr, long val) {

        int l = 0, r = arr.length;

        while (l < r) {

            int mid = (l + r) / 2;

            if (arr[mid] <= val) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        return l;
    }
}