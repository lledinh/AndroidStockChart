package com.ledinh.androidstockchart.chart3.element;

import android.util.Pair;

import com.ledinh.androidstockchart.chart.set.ChartSet;
import com.ledinh.androidstockchart.chart3.drawing.StockChartElement;

public abstract class ChartElement<E extends ChartSet> {
    protected E data;

    public ChartElement() {

    }

    public Pair<Integer, Integer> getRange(int firstValueIndex, int lastValueIndex) {
        return (Pair<Integer, Integer>) data.getRange(firstValueIndex, lastValueIndex);
    }

    public ChartElement(E data) {
        this.data = data;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
