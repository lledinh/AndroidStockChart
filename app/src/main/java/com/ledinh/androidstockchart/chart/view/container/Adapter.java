package com.ledinh.androidstockchart.chart.view.container;

import com.ledinh.androidstockchart.chart.set.ChartSet;

public class Adapter<E extends ChartSet> {
    private E chartData;

    public Adapter() {
    }

    public Adapter(E chartData) {
        this.chartData = chartData;
    }

    public E getChartData() {
        return chartData;
    }

    public void setChartData(E chartData) {
        this.chartData = chartData;
    }
}
