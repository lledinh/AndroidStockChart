package com.ledinh.androidstockchart.chart.set;

import android.util.Pair;

import com.ledinh.androidstockchart.chart.model.Kline;
import com.ledinh.androidstockchart.chart.util.TimeUnit;
import com.ledinh.androidstockchart.chart.view.element.DrawingElement;

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

    public abstract Pair<Integer, Integer> getRange(int firstValueIndex, int lastValueIndex);
    public Pair<Integer, Integer> getRange() {
        return getRange(0, values.size());
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
