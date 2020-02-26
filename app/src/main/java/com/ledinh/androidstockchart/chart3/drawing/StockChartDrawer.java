package com.ledinh.androidstockchart.chart3.drawing;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.ledinh.androidstockchart.chart3.element.Chart;

import java.util.HashMap;
import java.util.Map;

public class StockChartDrawer extends StockChartElement {
    private Map<Chart, ChartDrawer> chartDrawers;
    private TimelineDrawer timelineDrawer;

    public StockChartDrawer() {
        chartDrawers = new HashMap<>();
    }

    public StockChartDrawer(Rect position) {
        super(position);
    }

    public void draw(Canvas canvas, float translateX) {
        for(Map.Entry<Chart, ChartDrawer> entry : chartDrawers.entrySet()) {
            Chart chart = entry.getKey();
            ChartDrawer chartDrawer = entry.getValue();

            chartDrawer.draw(canvas, chart, translateX);
        }
    }


    public void addChartDrawer(Chart chart, ChartDrawer chartDrawer) {
        chartDrawers.put(chart, chartDrawer);
    }

    public TimelineDrawer getTimelineDrawer() {
        return timelineDrawer;
    }

    public void setTimelineDrawer(TimelineDrawer timelineDrawer) {
        this.timelineDrawer = timelineDrawer;
    }

    public Map<Chart, ChartDrawer> getChartDrawers() {
        return chartDrawers;
    }

    public void setChartDrawers(Map<Chart, ChartDrawer> chartDrawers) {
        this.chartDrawers = chartDrawers;
    }
}
