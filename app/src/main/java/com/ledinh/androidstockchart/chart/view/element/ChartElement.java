package com.ledinh.androidstockchart.chart.view.element;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Pair;

import com.ledinh.androidstockchart.chart.util.Viewport;
import com.ledinh.androidstockchart.chart.YAxis;
import com.ledinh.androidstockchart.chart.set.ChartSet;
import com.ledinh.androidstockchart.chart.view.UIElement;
import com.ledinh.androidstockchart.chart.view.container.Chart;
import com.ledinh.androidstockchart.chart.view.container.Frame;

public abstract class ChartElement<E extends ChartSet> extends UIElement {

    protected int weight;

    protected boolean autoScale;
    protected int gridRows;

    protected float spaceBetweenValue;
    protected YAxis yAxis;

    protected E chartData;

    protected Frame frame;
    protected Paint paint;
    protected Chart chart;

    public ChartElement(Chart chart) {
        this.yAxis = new YAxis();
        autoScale = true;
        gridRows = 4;
        weight = 1;
        this.chart = chart;
    }

    public int getMaxIndex() {
        return chartData.getDataSize() - 1;
    }

    public Pair<Integer, Integer> getRange(int firstValueIndex, int lastValueIndex) {
        return (Pair<Integer, Integer>) chartData.getRange(firstValueIndex, lastValueIndex);
    }

    public void setViewportPosition(int left, int top, int right, int bottom) {
        chart.getFrame().getViewport().setViewingPosition(new Rect(left, top, right, bottom));
    }

    public void setAxisMin(double min) {
        yAxis.setAxisMin(min);
    }

    public void setAxisMax(double max) {
        yAxis.setAxisMax(max);
    }


    public Viewport getViewport() {
        return chart.getFrame().getViewport();
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

    public void setChartData(E chartData) {
        this.chartData = chartData;
    }

    public void setSpaceBetweenValue(float spaceBetweenValue) {
        this.spaceBetweenValue = spaceBetweenValue;
    }

    public boolean isAutoScale() {
        return autoScale;
    }

    public void setAutoScale(boolean autoScale) {
        this.autoScale = autoScale;
    }


    public void setGridRows(int gridRows) {
        this.gridRows = gridRows;
    }

//    public void setViewport(Viewport viewport) {
//        this.frame.setViewport(viewport);
//    }

    public void setyAxis(YAxis yAxis) {
        this.yAxis = yAxis;
    }

    public int getGridRows() {
        return gridRows;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setTextAxisColor(int color) {
        yAxis.setTextAxisColor(color);
    }

    public void setTextAxisSize(float size) {
        yAxis.setTextAxisSize(size);
    }

    public void setTextAxisBackgroundColor(int color) {
        yAxis.setTextAxisBackgroundColor(color);
    }

    public void setTextAxisLeftPadding(int size) {
        yAxis.setLeftPadding(size);
    }

    public void setUnit(String unit) {
        yAxis.setUnit(unit);
    }


    public void setTextUnitColor(int color) {
        yAxis.setTextUnitColor(color);
    }

    public void setTextUnitSize(int size) {
        yAxis.setTextUnitSize(size);
    }


    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }
}
