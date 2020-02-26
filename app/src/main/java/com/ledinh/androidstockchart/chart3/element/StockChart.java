package com.ledinh.androidstockchart.chart3.element;

import com.ledinh.androidstockchart.chart2.view2.Timeline;

import java.util.ArrayList;
import java.util.List;

public class StockChart {
    private List<Chart> charts;
    private Timeline timeline;

    public StockChart() {
        charts = new ArrayList<>();
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

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }
}
