package com.ledinh.androidstockchart.chart3.element;

import java.util.ArrayList;
import java.util.List;

public class StockChart {
    private List<Chart> charts;
    private Timeline timeline;
    private int columns;

    public StockChart() {
        charts = new ArrayList<>();
        timeline = new Timeline();
        columns = 5;
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

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }
}
