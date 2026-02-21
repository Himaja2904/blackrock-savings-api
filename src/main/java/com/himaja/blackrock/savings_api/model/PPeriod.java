package com.himaja.blackrock.savings_api.model;

import lombok.Data;

@Data
public class PPeriod {

    private long start;
    private long end;
    private double extra;

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

    public double getExtra() {
        return extra;
    }

    public void setExtra(double extra) {
        this.extra = extra;
    }
}