package com.ledinh.androidstockchart.chart.view.container;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.ledinh.androidstockchart.chart.util.Viewport;
import com.ledinh.androidstockchart.chart.YAxis;
import com.ledinh.androidstockchart.chart.view.element.ChartElement;

public class Frame {
    private Viewport viewport;
    private YAxis leftAxis;
    private YAxis rightAxis;
    private int gridColumns;
    private int gridRows;
    private Paint paintGridLine;

    private ChartElement chartComponent;

    public Frame() {

    }

    public Frame(YAxis leftAxis, YAxis rightAxis) {
        this.leftAxis = leftAxis;
        this.rightAxis = rightAxis;
    }

    private void drawGrid(Canvas canvas) {
        float columnSpace = viewport.getViewportWidth() / gridColumns;
        float rowSpace = viewport.getViewportHeight() / gridRows;

        for (int i = 0; i <= gridColumns; i++) {
            canvas.drawLine(viewport.getViewingPosition().top + columnSpace * i, 0, viewport.getViewingPosition().top + columnSpace * i, viewport.getViewportHeight(), paintGridLine);
        }

        for (int i = 0; i <= gridRows; i++) {
            canvas.drawLine(viewport.getViewingPosition().left, viewport.getViewingPosition().top + (i * rowSpace), viewport.getViewingPosition().right, viewport.getViewingPosition().top + (i * rowSpace), paintGridLine);
        }
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public YAxis getLeftAxis() {
        return leftAxis;
    }

    public void setLeftAxis(YAxis leftAxis) {
        this.leftAxis = leftAxis;
    }

    public YAxis getRightAxis() {
        return rightAxis;
    }

    public void setRightAxis(YAxis rightAxis) {
        this.rightAxis = rightAxis;
    }

    public int getGridColumns() {
        return gridColumns;
    }

    public void setGridColumns(int gridColumns) {
        this.gridColumns = gridColumns;
    }

    public int getGridRows() {
        return gridRows;
    }

    public void setGridRows(int gridRows) {
        this.gridRows = gridRows;
    }

    public ChartElement getChartComponent() {
        return chartComponent;
    }

    public void setChartComponent(ChartElement chartComponent) {
        this.chartComponent = chartComponent;
    }
}
