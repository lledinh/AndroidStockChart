package com.ledinh.androidstockchart.chart2;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.ledinh.androidstockchart.chart.Viewport;

//public class MultipleDrawingElement {
//    protected List<DrawingElement>DrawingElement drawingElement;
//    protected Chart chart;
//
//    protected boolean autoScale;
//    protected int gridRows;
//
//    protected Viewport viewport;
//    protected float spaceBetweenValue;
//    protected YAxis yAxis;
//
//    protected E chartData;
//
//    public abstract void draw(Canvas canvas, float translateX);
//    public abstract void drawValues(Canvas canvas);
//    public abstract void drawTimeline(Canvas canvas);
//    public abstract int getMaxIndex();
//    public abstract void updateAxisRangeFromIndex(int firstValueIndex, int lastValueIndex);
//
//    public DrawingElement(Chart chart) {
//        this.yAxis = new YAxis();
//        this.chart = chart;
//        autoScale = true;
//        gridRows = 4;
//    }
//
//
//    public void setViewportPosition(int left, int top, int right, int bottom) {
//        viewport.setViewingPosition(new Rect(left, top, right, bottom));
//    }
//
//    public void setAxisMin(double min) {
//        yAxis.setAxisMin(min);
//    }
//
//    public void setAxisMax(double max) {
//        yAxis.setAxisMax(max);
//    }
//
//    public Chart getChart() {
//        return chart;
//    }
//
//    public Viewport getViewport() {
//        return viewport;
//    }
//
//    public float getSpaceBetweenValue() {
//        return spaceBetweenValue;
//    }
//
//    public YAxis getyAxis() {
//        return yAxis;
//    }
//
//    public E getChartData() {
//        return chartData;
//    }
//
//    public void setChartData(E chartData) {
//        this.chartData = chartData;
//    }
//
//    public void setSpaceBetweenValue(float spaceBetweenValue) {
//        this.spaceBetweenValue = spaceBetweenValue;
//    }
//
//    public boolean isAutoScale() {
//        return autoScale;
//    }
//
//    public void setAutoScale(boolean autoScale) {
//        this.autoScale = autoScale;
//    }
//
//    public void setChart(Chart chart) {
//        this.chart = chart;
//    }
//
//    public void setGridRows(int gridRows) {
//        this.gridRows = gridRows;
//    }
//
//    public void setViewport(Viewport viewport) {
//        this.viewport = viewport;
//    }
//
//    public void setyAxis(YAxis yAxis) {
//        this.yAxis = yAxis;
//    }
//
//    public int getGridRows() {
//        return gridRows;
//    }
//}
