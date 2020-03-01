package com.ledinh.androidstockchart.chart3.drawing;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.ledinh.androidstockchart.chart3.element.Chart;
import com.ledinh.androidstockchart.chart3.element.Timeline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockChartDrawer extends StockChartElement {
    private Map<Chart, ChartDrawer> chartDrawers;
    private List<Chart> chartDrawersOrders;
    private TimelineDrawer timelineDrawer;

    private float spaceBetweenValue;
    private int screenDataCount;

    public StockChartDrawer() {
        chartDrawers = new HashMap<>();
        chartDrawersOrders = new ArrayList<>();
        timelineDrawer = new TimelineDrawer();
    }

    public StockChartDrawer(Rect position) {
        super(position);
    }

    public void draw(Canvas canvas, Timeline timeline, float translateX) {
        for (Chart chart : chartDrawersOrders) {
            ChartDrawer chartDrawer = chartDrawers.get(chart);

            if (chartDrawer != null) {
                chartDrawer.draw(canvas, chart, translateX);
            }
        }

        timelineDrawer.draw(canvas, 5, timeline);
    }

    public int getWeightSum() {
        int sum = 0;

        for(Map.Entry<Chart, ChartDrawer> entry : chartDrawers.entrySet()) {
            int weight = entry.getValue().getWeight();
            sum += weight;
        }

        return sum;
    }

    public void addChartDrawer(Chart chart, ChartDrawer chartDrawer) {
        chartDrawers.put(chart, chartDrawer);
        chartDrawersOrders.add(chart);
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

    public int getScreenDataCount() {
        return screenDataCount;
    }

    public void setScreenDataCount(int screenDataCount) {
        this.screenDataCount = screenDataCount;
    }

    public float getSpaceBetweenValue() {
        return spaceBetweenValue;
    }

    public void setSpaceBetweenValue(float spaceBetweenValue) {
        this.spaceBetweenValue = spaceBetweenValue;

        for (Map.Entry<Chart, ChartDrawer> pair: chartDrawers.entrySet()) {
            ChartDrawer chartDrawer = pair.getValue();
            for (ChartElementDrawer chartElementDrawer: chartDrawer.getChartElementDrawers()) {
                chartElementDrawer.setSpaceBetweenValue(spaceBetweenValue);
            }
        }
    }

    public List<Chart> getChartDrawersOrders() {
        return chartDrawersOrders;
    }

    public void setChartDrawersOrders(List<Chart> chartDrawersOrders) {
        this.chartDrawersOrders = chartDrawersOrders;
    }
}
