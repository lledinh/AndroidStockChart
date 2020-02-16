package com.ledinh.androidstockchart.chart2;

import android.graphics.Canvas;

import com.ledinh.androidstockchart.chart.Viewport;

public abstract class DrawingElement<E extends ChartData> {
    protected Chart chart;

    protected Viewport viewport;
    protected float spaceBetweenValue;
    protected YAxis yAxis;

    protected E chartData;

    public abstract void draw(Canvas canvas, float translateX);
    public abstract void drawValues(Canvas canvas);
    public abstract void drawTimeline(Canvas canvas);
    public abstract int getMaxIndex();

    public DrawingElement(Chart chart) {
        this.yAxis = new YAxis();
        this.chart = chart;
    }

    public Chart getChart() {
        return chart;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public float getSpaceBetweenValue() {
        return spaceBetweenValue;
    }

    public YAxis getyAxis() {
        return yAxis;
    }

    public E getChartData() {
        return chartData;
    }

    public int getIndexFromX(int x) {
        int delta = (int) ((viewport.getViewWidth() - x) / spaceBetweenValue);
        int index = getMaxIndex() - delta;

        return index;
    }

    public void setSpaceBetweenValue(float spaceBetweenValue) {
        this.spaceBetweenValue = spaceBetweenValue;
    }

}
