package com.ledinh.androidstockchart.chart3.drawing;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.ledinh.androidstockchart.chart.YAxis;
import com.ledinh.androidstockchart.chart.set.ChartSet;

public abstract class ChartElementDrawer<E extends ChartSet> extends StockChartElement {
    protected float spaceBetweenValue;

    public ChartElementDrawer() {
    }

    public ChartElementDrawer(Rect position) {
        super(position);
    }

    public ChartElementDrawer(Rect position, float spaceBetweenValue) {
        super(position);
        this.spaceBetweenValue = spaceBetweenValue;
    }

    public abstract void draw(Canvas canvas, E data, YAxis yAxis, float translateX);

    public float getSpaceBetweenValue() {
        return spaceBetweenValue;
    }

    public void setSpaceBetweenValue(float spaceBetweenValue) {
        this.spaceBetweenValue = spaceBetweenValue;
    }

}
