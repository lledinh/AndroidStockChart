package com.ledinh.androidstockchart.chart3.element;

import android.util.Log;
import android.util.Pair;

import com.ledinh.androidstockchart.chart.YAxis;
import com.ledinh.androidstockchart.chart3.event.OnRangeChangeListener;

import java.util.ArrayList;
import java.util.List;

public class Chart implements OnRangeChangeListener {
    private Grid grid;
    private YAxis yAxisLeft;
    private YAxis yAxisRight;
    private List<ChartElement> chartElements;

    private OnRangeChangeListener onRangeChangeListener;

    private boolean autoScale;

    public Chart() {
        chartElements = new ArrayList<>();
        autoScale = false;
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

    @Override
    public void onRangeChange(int firstValueIndex, int lastValueIndex) {
        Log.d("StockChartView3", "chart " + firstValueIndex + " " + lastValueIndex);
        scaleAxis(firstValueIndex, lastValueIndex);
    }

    private void scaleAxis(int firstValueIndex, int lastValueIndex) {
        int maxDataSize = 0;
        for (ChartElement chartElement : chartElements) {
            int dataSize = chartElement.getData().getDataSize();
            if (dataSize > maxDataSize) {
                maxDataSize = dataSize;
            }
        }

        if (lastValueIndex >= maxDataSize) {
            lastValueIndex = maxDataSize - 1;
        }

        if (autoScale) {
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;

            for (ChartElement chartElement : chartElements) {
                Pair<Integer, Integer> range = chartElement.getRange(firstValueIndex, lastValueIndex);

                if (range.first < min) {
                    min = range.first;
                }

                if (range.second > max) {
                    max = range.second;
                }
                Log.d("StockChartView3", "scaleAxis min = " + min);
                Log.d("StockChartView3", "scaleAxis max = " + max);
            }

            yAxisLeft.setRange(min, max);
            yAxisLeft.extendRange(10);
        }
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

    public void addChartElement(ChartElement chartElement) {
        chartElements.add(chartElement);
    }

    public boolean isAutoScale() {
        return autoScale;
    }

    public void setAutoScale(boolean autoScale) {
        this.autoScale = autoScale;
    }
}
