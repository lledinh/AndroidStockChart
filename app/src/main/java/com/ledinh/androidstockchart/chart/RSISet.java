package com.ledinh.androidstockchart.chart;

import java.util.ArrayList;
import java.util.List;

public class RSISet {
    private List<RSI> values = new ArrayList<>();
    private TimeUnit unit;
    private int value;

    public RSISet(TimeUnit unit, int value) {
        this.unit = unit;
        this.value = value;
        values = new ArrayList<>();
    }

    public RSISet(List<RSI> values, TimeUnit unit, int value) {
        this.values = values;
        this.unit = unit;
        this.value = value;
    }

    public List<RSI> getValues() {
        return values;
    }

    public void setValues(List<RSI> values) {
        this.values = values;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public int getValue() {
        return value;
    }
}
