package com.ledinh.androidstockchart.chart3.drawing;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.ledinh.androidstockchart.chart3.element.Chart;
import com.ledinh.androidstockchart.chart3.element.ChartElement;

import java.util.ArrayList;
import java.util.List;

public class ChartDrawer extends StockChartElement {
    private GridDrawing gridDrawing;
    private YAxisDrawing yAxisLeftDrawing;
    private YAxisDrawing yAxisRightDrawing;
    private List<ChartElementDrawer> chartElementDrawers;

    public ChartDrawer() {
        chartElementDrawers = new ArrayList<>();
    }

    public void draw(Canvas canvas, Chart chart, float translateX) {
        gridDrawing.draw(canvas, chart.getGrid());
        yAxisLeftDrawing.draw(canvas, chart.getGrid(), chart.getyAxisLeft());

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

    public GridDrawing getGridDrawing() {
        return gridDrawing;
    }

    public void setGridDrawing(GridDrawing gridDrawing) {
        this.gridDrawing = gridDrawing;
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
