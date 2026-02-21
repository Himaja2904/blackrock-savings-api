package com.himaja.blackrock.savings_api.controller;

import com.himaja.blackrock.savings_api.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
public class TransactionControllerServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private tools.jackson.databind.ObjectMapper objectMapper;

    @Test
    void filterEndpointPerformanceTest() throws Exception {

        // ===============================
        // START SMALL FIRST
        // ===============================
        int TX_COUNT = 100_000;   // increase later
        int Q_COUNT  = 1_000;
        int P_COUNT  = 1_000;
        int K_COUNT  = 500;
        // ===============================

        System.out.println("Generating request data...");

        FilterRequest request =
                buildRequest(
                        TX_COUNT,
                        Q_COUNT,
                        P_COUNT,
                        K_COUNT
                );

        String json =
                objectMapper.writeValueAsString(request);

        System.out.println("Payload size: "
                + json.length() / 1024 + " KB");

        long start = System.currentTimeMillis();

        mockMvc.perform(
                        post("/blackrock/challenge/v1/transactions/filter")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk());

        long end = System.currentTimeMillis();

        System.out.println("=================================");
        System.out.println("Controller Time: " + (end - start) + " ms");
        System.out.println("=================================");
    }

    /* ===============================
       Request Builder
       =============================== */

    private FilterRequest buildRequest(
            int txCount,
            int qCount,
            int pCount,
            int kCount) {

        FilterRequest req = new FilterRequest();

        req.setTransactions(generateTransactions(txCount));
        req.setQ(generateQ(qCount));
        req.setP(generateP(pCount));
        req.setK(generateK(kCount));

        return req;
    }

    private List<Transaction> generateTransactions(int n) {

        List<Transaction> list = new ArrayList<>(n);

        long base = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {

            Transaction tx = new Transaction();

            tx.setTimestamp(base + i * 1000L);
            tx.setAmount(100 + (i % 500));
            tx.setCeiling(200);
            tx.setRemanent(100);

            list.add(tx);
        }

        return list;
    }

    private List<QPeriod> generateQ(int n) {

        List<QPeriod> list = new ArrayList<>(n);

        long base = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {

            QPeriod q = new QPeriod();

            long start = base + i * 5000L;

            q.setStart(start);
            q.setEnd(start + 200_000L);
            q.setFixed(50 + (i % 50));

            list.add(q);
        }

        return list;
    }

    private List<PPeriod> generateP(int n) {

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

    private List<KPeriod> generateK(int n) {

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