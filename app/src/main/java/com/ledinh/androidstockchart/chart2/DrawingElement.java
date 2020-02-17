package com.ledinh.androidstockchart.chart2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.ledinh.androidstockchart.chart.Viewport;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public abstract class DrawingElement<E extends ChartSet> {
    protected Chart chart;

    protected int weight;

    protected boolean autoScale;
    protected int gridRows;

    protected Viewport viewport;
    protected float spaceBetweenValue;
    protected YAxis yAxis;

    protected E chartData;

    public abstract void draw(Canvas canvas, float translateX);
    public abstract void drawTimeline(Canvas canvas);
    public abstract int getMaxIndex();
    public abstract void updateAxisRangeFromIndex(int firstValueIndex, int lastValueIndex);

    public void drawRows(Canvas canvas) {
        float rowSpace = viewport.getViewportHeight() / gridRows;

        for (int i = 0; i <= gridRows; i++) {
            Paint p;
            if (i == 0 || i == gridRows) {
                p = chart.getPaintViewSeparator();
            }
            else {
                p = chart.getPaintGridLine();
            }

            canvas.drawLine(viewport.getViewingPosition().left, viewport.getViewingPosition().top + (i * rowSpace), viewport.getViewingPosition().right, viewport.getViewingPosition().top + (i * rowSpace), p);
        }
    }

    public void drawValues(Canvas canvas) {
        Paint.FontMetrics fm = chart.getPaintTextAxis().getFontMetrics();
        float textHeight = fm.descent - fm.ascent;
        float baseLine = (textHeight - fm.bottom - fm.top) / 2;

        float rowSpace = viewport.getViewportHeight() / gridRows;
        float range = (float) ((yAxis.getAxisMax() - yAxis.getAxisMin()) / gridRows);

        for (int i = 0; i <= gridRows; i++) {
            NumberFormat formatter = new DecimalFormat("#0");
            String value = formatter.format(yAxis.getAxisMax() - (i * range));

            if (i < gridRows) {
                canvas.drawText(value, 0, viewport.getViewingPosition().top + (i * rowSpace) + baseLine, chart.getPaintTextAxis());
            }
            else {
                canvas.drawText(value, 0, viewport.getViewingPosition().top + (i * rowSpace), chart.getPaintTextAxis());
            }
        }
    }

    public DrawingElement(Chart chart) {
        this.yAxis = new YAxis();
        this.chart = chart;
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

    public void setChart(Chart chart) {
        this.chart = chart;
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
}
