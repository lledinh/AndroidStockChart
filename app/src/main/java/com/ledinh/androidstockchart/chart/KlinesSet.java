package com.ledinh.androidstockchart.chart;

import java.util.ArrayList;
import java.util.List;

public class KlinesSet {
    private List<Kline> values = new ArrayList<>();
    private TimeUnit unit;

    public KlinesSet(List<Kline> values, TimeUnit unit) {
        this.values = values;
        this.unit = unit;
    }

    public List<Kline> getValues() {
        return values;
    }

    public void setValues(List<Kline> values) {
        this.values = values;
    }

    public TimeUnit getUnit() {
        return unit;
    }
}
