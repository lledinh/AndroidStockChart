package com.ledinh.androidstockchart.chart3.drawing;

import android.graphics.Canvas;

import com.ledinh.androidstockchart.chart.YAxis;
import com.ledinh.androidstockchart.chart.set.ChartSet;

public abstract class ChartElementDrawer<E extends ChartSet> extends StockChartElement {
    protected float spaceBetweenValue;

    public abstract void draw(Canvas canvas, E data, YAxis yAxis, float translateX);


    public float getSpaceBetweenValue() {
        return spaceBetweenValue;
    }

    public void setSpaceBetweenValue(float spaceBetweenValue) {
        this.spaceBetweenValue = spaceBetweenValue;
    }

}
