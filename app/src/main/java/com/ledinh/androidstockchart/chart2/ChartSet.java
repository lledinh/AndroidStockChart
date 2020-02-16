package com.ledinh.androidstockchart.chart2;

import java.util.List;

public abstract class ChartSet<Data> {
    protected List<Data> values;
    protected TimeUnit timeUnit;

    public ChartSet() {

    }

    public ChartSet(List<Data> values, TimeUnit timeUnit) {
        this.values = values;
        this.timeUnit = timeUnit;
    }

    public int getDataSize() {
        return values.size();
    }

    public List<Data> getValues() {
        return values;
    }

    public void setValues(List<Data> values) {
        this.values = values;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}
