package com.ledinh.androidstockchart.chart3.drawing;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.ledinh.androidstockchart.chart3.drawing.simple.StaticGridDrawing;
import com.ledinh.androidstockchart.chart3.element.Chart;
import com.ledinh.androidstockchart.chart3.element.ChartElement;

import java.util.ArrayList;
import java.util.List;

public class ChartDrawer extends StockChartElement {
    private StaticGridDrawing staticGridDrawing;
    private YAxisDrawing yAxisLeftDrawing;
    private YAxisDrawing yAxisRightDrawing;
    private List<ChartElementDrawer> chartElementDrawers;

    public ChartDrawer() {
        chartElementDrawers = new ArrayList<>();
    }

    public void draw(Canvas canvas, Chart chart, float translateX) {
        staticGridDrawing.draw(canvas, chart.getStaticGrid());
        yAxisLeftDrawing.draw(canvas, chart.getStaticGrid(), chart.getyAxisLeft());

        for (int i = 0; i < chart.getChartElements().size(); i++) {
            ChartElement chartElement = chart.getChartElements().get(i);
            ChartElementDrawer chartElementDrawer = chartElementDrawers.get(i);

            chartElementDrawer.draw(canvas, chartElement.getData(), chart.getyAxisLeft(), translateX);
        }
    }

    @Override
    public void setPosition(Rect position) {
        super.setPosition(position);

        for (ChartElementDrawer chartElementDrawer: chartElementDrawers) {
            chartElementDrawer.setPosition(position);
        }
    }

    public StaticGridDrawing getStaticGridDrawing() {
        return staticGridDrawing;
    }

    public void setStaticGridDrawing(StaticGridDrawing staticGridDrawing) {
        this.staticGridDrawing = staticGridDrawing;
    }

    public YAxisDrawing getyAxisLeftDrawing() {
        return yAxisLeftDrawing;
    }

    public void setyAxisLeftDrawing(YAxisDrawing yAxisLeftDrawing) {
        this.yAxisLeftDrawing = yAxisLeftDrawing;
    }

    public YAxisDrawing getyAxisRightDrawing() {
        return yAxisRightDrawing;
    }

    public void setyAxisRightDrawing(YAxisDrawing yAxisRightDrawing) {
        this.yAxisRightDrawing = yAxisRightDrawing;
    }

    public void addChartElementDrawer(ChartElementDrawer chartElementDrawer) {
        chartElementDrawers.add(chartElementDrawer);
    }

    public List<ChartElementDrawer> getChartElementDrawers() {
        return chartElementDrawers;
    }

    public void setChartElementDrawers(List<ChartElementDrawer> chartElementDrawers) {
        this.chartElementDrawers = chartElementDrawers;
    }
}
