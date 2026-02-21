package com.himaja.blackrock.savings_api.service;

import com.himaja.blackrock.savings_api.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RuleEngineService {

    public Map<KPeriod, Double> applyRules(
            List<Transaction> transactions,
            List<QPeriod> qPeriods,
            List<PPeriod> pPeriods,
            List<KPeriod> kPeriods) {

        // Step 1: Apply q override
        for (Transaction tx : transactions) {

            QPeriod chosen = null;

            for (QPeriod q : qPeriods) {
                if (tx.getTimestamp() >= q.getStart()
                        && tx.getTimestamp() <= q.getEnd()) {

                    if (chosen == null ||
                            q.getStart() > chosen.getStart()) {
                        chosen = q;
                    }
                }
            }

            if (chosen != null) {
                tx.setRemanent(chosen.getFixed());
            }
        }

        // Step 2: Apply p additions
        for (Transaction tx : transactions) {

            double extra = 0;

            for (PPeriod p : pPeriods) {
                if (tx.getTimestamp() >= p.getStart()
                        && tx.getTimestamp() <= p.getEnd()) {
                    extra += p.getExtra();
                }
            }

            tx.setRemanent(tx.getRemanent() + extra);
        }

        // Step 3: Group by k
        Map<KPeriod, Double> result = new HashMap<>();

        for (KPeriod k : kPeriods) {

            double sum = 0;

            for (Transaction tx : transactions) {
                if (tx.getTimestamp() >= k.getStart()
                        && tx.getTimestamp() <= k.getEnd()) {
                    sum += tx.getRemanent();
                }
            }

            result.put(k, sum);
        }

        return result;
    }
}