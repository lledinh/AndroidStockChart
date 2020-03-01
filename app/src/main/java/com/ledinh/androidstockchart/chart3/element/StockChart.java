package com.ledinh.androidstockchart.chart3.element;

import com.ledinh.androidstockchart.chart3.element.simple.StaticTimeline;

import java.util.ArrayList;
import java.util.List;

public class StockChart {
    private List<Chart> charts;
    private StaticTimeline staticTimeline;

    public StockChart() {
        charts = new ArrayList<>();
        staticTimeline = new StaticTimeline();
    }

    public void addChart(Chart chart) {
        charts.add(chart);
    }

    public List<Chart> getCharts() {
        return charts;
    }

    public void setCharts(List<Chart> charts) {
        this.charts = charts;
    }

    public StaticTimeline getStaticTimeline() {
        return staticTimeline;
    }

    public void setStaticTimeline(StaticTimeline staticTimeline) {
        this.staticTimeline = staticTimeline;
    }
}
