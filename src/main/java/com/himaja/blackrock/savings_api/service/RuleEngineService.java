package com.himaja.blackrock.savings_api.service;

import com.himaja.blackrock.savings_api.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RuleEngineService {

    public List<SavingsByPeriod> applyRules(
            List<Transaction> transactions,
            List<QPeriod> qPeriods,
            List<PPeriod> pPeriods,
            List<KPeriod> kPeriods) {

        // 1. Apply Q rules (override)
        for (Transaction tx : transactions) {

            QPeriod selected = null;

            for (QPeriod q : qPeriods) {
                if (tx.getTimestamp() >= q.getStart()
                        && tx.getTimestamp() <= q.getEnd()) {

                    // Pick the one with latest start
                    if (selected == null ||
                            q.getStart() > selected.getStart()) {
                        selected = q;
                    }
                }
            }

            if (selected != null) {
                tx.setRemanent(selected.getFixed());
            }
        }

        // 2. Apply P rules (add extras)
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

        // 3. Group by K periods
        List<SavingsByPeriod> result = new ArrayList<>();

        for (KPeriod k : kPeriods) {

            double sum = 0;

            for (Transaction tx : transactions) {
                if (tx.getTimestamp() >= k.getStart()
                        && tx.getTimestamp() <= k.getEnd()) {
                    sum += tx.getRemanent();
                }
            }

            result.add(new SavingsByPeriod(
                    k.getStart(),
                    k.getEnd(),
                    sum
            ));
        }

        return result;
    }
}