package com.ledinh.androidstockchart.chart.view.element;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.ledinh.androidstockchart.chart.util.Viewport;
import com.ledinh.androidstockchart.chart.YAxis;
import com.ledinh.androidstockchart.chart.set.ChartSet;
import com.ledinh.androidstockchart.chart.view.ChartView;

public abstract class DrawingElement<E extends ChartSet> {
    protected ChartView chartView;

    protected int weight;

    protected boolean autoScale;
    protected int gridRows;

    protected Viewport viewport;
    protected float spaceBetweenValue;
    protected YAxis yAxis;

    protected E chartData;

    public abstract void draw(Canvas canvas, DrawingArea drawingArea, float translateX);
    public abstract void drawTimeline(Canvas canvas);
    public abstract int getMaxIndex();
    public abstract void updateAxisRangeFromIndex(int firstValueIndex, int lastValueIndex);

    public void drawRows(Canvas canvas) {
        float rowSpace = viewport.getViewportHeight() / gridRows;

        for (int i = 0; i <= gridRows; i++) {
            Paint p;
            if (i == 0 || i == gridRows) {
                p = chartView.getPaintViewSeparator();
            }
            else {
                p = chartView.getPaintGridLine();
            }

            canvas.drawLine(viewport.getViewingPosition().left, viewport.getViewingPosition().top + (i * rowSpace), viewport.getViewingPosition().right, viewport.getViewingPosition().top + (i * rowSpace), p);
        }
    }

    public void drawAxis(Canvas canvas) {
        yAxis.draw(canvas, viewport);
    }

//    public void drawValues(Canvas canvas) {
//        Paint.FontMetrics fm = chart.getPaintTextAxis().getFontMetrics();
//        float textHeight = fm.descent - fm.ascent;
//        float baseLine = (textHeight - fm.bottom - fm.top) / 2;
//
//        float rowSpace = viewport.getViewportHeight() / gridRows;
//        float range = (float) ((yAxis.getAxisMax() - yAxis.getAxisMin()) / gridRows);
//
//        for (int i = 0; i <= gridRows; i++) {
//            NumberFormat formatter = new DecimalFormat("#0");
//            String value = formatter.format(yAxis.getAxisMax() - (i * range));
//
//            if (i < gridRows) {
//                canvas.drawText(value, 0, viewport.getViewingPosition().top + (i * rowSpace) + baseLine, chart.getPaintTextAxis());
//            }
//            else {
//                canvas.drawText(value, 0, viewport.getViewingPosition().top + (i * rowSpace), chart.getPaintTextAxis());
//            }
//        }
//    }

    public DrawingElement(ChartView chartView) {
        this.yAxis = new YAxis();
        this.chartView = chartView;
        autoScale = true;
        gridRows = 4;
        weight = 1;
    }

    public void setViewportPosition(int left, int top, int right, int bottom) {
        viewport.setViewingPosition(new Rect(left, top, right, bottom));
    }

    public void setAxisMin(double min) {
        yAxis.setAxisMin(min);
    }

    public void setAxisMax(double max) {
        yAxis.setAxisMax(max);
    }

    public ChartView getChartView() {
        return chartView;
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

    public void setChartView(ChartView chartView) {
        this.chartView = chartView;
    }

    public void setGridRows(int gridRows) {
        this.gridRows = gridRows;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

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




}
