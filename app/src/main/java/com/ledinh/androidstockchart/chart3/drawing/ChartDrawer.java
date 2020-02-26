package com.ledinh.androidstockchart.chart3.drawing;

import android.graphics.Canvas;

import com.ledinh.androidstockchart.chart3.element.Chart;
import com.ledinh.androidstockchart.chart3.element.ChartElement;

import java.util.List;

public class ChartDrawer extends StockChartElement {
    private GridDrawing gridDrawing;
    private YAxisDrawing yAxisLeftDrawing;
    private YAxisDrawing yAxisRightDrawing;
    private List<ChartElement> chartElements;

    public ChartDrawer() {
    }

    public void draw(Canvas canvas, Chart chart, float translateX) {
        gridDrawing.draw(canvas, chart.getGrid());
        yAxisLeftDrawing.draw(canvas, chart.getGrid(), chart.getyAxisLeft());
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

    public List<ChartElement> getChartElements() {
        return chartElements;
    }

    public void setChartElements(List<ChartElement> chartElements) {
        this.chartElements = chartElements;
    }
}
