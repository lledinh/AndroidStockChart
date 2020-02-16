package com.ledinh.androidstockchart.chart2;

import com.ledinh.androidstockchart.chart.RSI;

import java.util.ArrayList;
import java.util.List;

public class RSISet extends ChartSet<RSI> {
    private int rsiValue;

    public RSISet(TimeUnit timeUnit, int rsiValue) {
        this.timeUnit = timeUnit;
        this.rsiValue = rsiValue;
        values = new ArrayList<>();
    }

    public RSISet(List<RSI> values, TimeUnit timeUnit, int rsiValue) {
        this.values = values;
        this.timeUnit = timeUnit;
        this.rsiValue = rsiValue;
    }

    public int getRsiValue() {
        return rsiValue;
    }

    public void setRsiValue(int rsiValue) {
        this.rsiValue = rsiValue;
    }
}
