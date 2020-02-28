package com.ledinh.androidstockchart.chart3.element;

import com.ledinh.androidstockchart.chart.set.ChartSet;
import com.ledinh.androidstockchart.chart3.drawing.StockChartElement;

public abstract class ChartElement<E extends ChartSet> {
    protected E data;

    public ChartElement() {

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
