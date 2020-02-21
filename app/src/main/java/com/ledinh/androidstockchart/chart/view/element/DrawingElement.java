package com.ledinh.androidstockchart.chart.view.element;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Pair;

import androidx.core.content.ContextCompat;

import com.ledinh.androidstockchart.R;
import com.ledinh.androidstockchart.chart.util.Viewport;
import com.ledinh.androidstockchart.chart.YAxis;
import com.ledinh.androidstockchart.chart.set.ChartSet;
import com.ledinh.androidstockchart.chart.view.ChartViewFragment;

import java.util.List;

public abstract class DrawingElement<E extends ChartSet> {

    protected int weight;

    protected boolean autoScale;
    protected int gridRows;

    protected float spaceBetweenValue;
    protected YAxis yAxis;

    protected E chartData;

    protected DrawingArea drawingArea;
    protected Paint paint;
    protected ChartViewFragment chartViewFragment;

    public abstract void draw(Canvas canvas, float translateX);
    public abstract void drawTimeline(Canvas canvas);
    public abstract void updateAxisRangeFromIndex(int firstValueIndex, int lastValueIndex);

    public DrawingElement(ChartViewFragment chartViewFragment) {
        this.yAxis = new YAxis();
        autoScale = true;
        gridRows = 4;
        weight = 1;
        this.chartViewFragment = chartViewFragment;
    }

    public int getMaxIndex() {
        return chartData.getDataSize() - 1;
    }

    public Pair<Integer, Integer> getRange(int firstValueIndex, int lastValueIndex) {
        return (Pair<Integer, Integer>) chartData.getRange(firstValueIndex, lastValueIndex);
    }

    public void setViewportPosition(int left, int top, int right, int bottom) {
        chartViewFragment.getDrawingArea().getViewport().setViewingPosition(new Rect(left, top, right, bottom));
    }

    public void setAxisMin(double min) {
        yAxis.setAxisMin(min);
    }

    public void setAxisMax(double max) {
        yAxis.setAxisMax(max);
    }


    public Viewport getViewport() {
        return chartViewFragment.getDrawingArea().getViewport();
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
//        this.drawingArea.setViewport(viewport);
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


    public DrawingArea getDrawingArea() {
        return drawingArea;
    }

    public void setDrawingArea(DrawingArea drawingArea) {
        this.drawingArea = drawingArea;
    }
}
