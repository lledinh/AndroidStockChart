package com.ledinh.androidstockchart.chart3.element;

import com.ledinh.androidstockchart.chart.YAxis;
import com.ledinh.androidstockchart.chart3.drawing.StockChartElement;

import java.util.ArrayList;
import java.util.List;

public class Chart extends StockChartElement {
    private Grid grid;
    private YAxis yAxisLeft;
    private YAxis yAxisRight;
    private List<ChartElement> chartElements;

    public Chart() {

    }

    public Chart(Grid grid, YAxis yAxisLeft, YAxis yAxisRight) {
        this.grid = grid;
        this.yAxisLeft = yAxisLeft;
        this.yAxisRight = yAxisRight;
        chartElements = new ArrayList<>();
    }

    public Chart(Grid grid, YAxis yAxisLeft, YAxis yAxisRight, List<ChartElement> chartElements) {
        this.grid = grid;
        this.yAxisLeft = yAxisLeft;
        this.yAxisRight = yAxisRight;
        this.chartElements = chartElements;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public YAxis getyAxisLeft() {
        return yAxisLeft;
    }

    public void setyAxisLeft(YAxis yAxisLeft) {
        this.yAxisLeft = yAxisLeft;
    }

    public YAxis getyAxisRight() {
        return yAxisRight;
    }

    public void setyAxisRight(YAxis yAxisRight) {
        this.yAxisRight = yAxisRight;
    }

    public List<ChartElement> getChartElements() {
        return chartElements;
    }

    public void setChartElements(List<ChartElement> chartElements) {
        this.chartElements = chartElements;
    }
}
