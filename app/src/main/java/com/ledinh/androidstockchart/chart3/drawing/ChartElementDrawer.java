package com.ledinh.androidstockchart.chart3.drawing;

import android.graphics.Canvas;

import com.ledinh.androidstockchart.chart.set.ChartSet;

public abstract class ChartElementDrawer<E extends ChartSet> extends StockChartElement {

    public abstract void draw(Canvas canvas, E data, float translateX);
}
