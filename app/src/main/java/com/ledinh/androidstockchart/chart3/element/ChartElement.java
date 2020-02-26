package com.ledinh.androidstockchart.chart3.element;

import com.ledinh.androidstockchart.chart.set.ChartSet;
import com.ledinh.androidstockchart.chart3.drawing.StockChartElement;

public abstract class ChartElement<E extends ChartSet> {
    protected E data;
}
