package com.himaja.blackrock.savings_api.model;

import lombok.Data;

@Data
public class QPeriod {

    private long start;
    private long end;
    private double fixed;

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public double getFixed() {
        return fixed;
    }

    public void setFixed(double fixed) {
        this.fixed = fixed;
    }
}