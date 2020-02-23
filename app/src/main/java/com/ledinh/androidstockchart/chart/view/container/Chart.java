package com.ledinh.androidstockchart.chart.view.container;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.ledinh.androidstockchart.chart.YAxis;
import com.ledinh.androidstockchart.chart.view.UIViewGroup;
import com.ledinh.androidstockchart.chart.view.element.ChartElement;

import java.util.ArrayList;
import java.util.List;

public class Chart extends UIViewGroup {
    private List<ChartElement> chartComponents;
    private Frame frame;

    private Paint paintGridLine;
    private Paint paint;
    private boolean autoScale;

    private YAxis leftAxis;
    private YAxis rightAxis;
    private int gridColumns;
    private int gridRows;


    public Chart() {
        paintGridLine = new Paint();
        chartComponents = new ArrayList<>();
        autoScale = false;
    }

    private void drawGrid(Canvas canvas) {
        float columnSpace = getWidth() / gridColumns;
        float rowSpace = getHeight() / gridRows;

        for (int i = 0; i <= gridColumns; i++) {
            canvas.drawLine(position.top + columnSpace * i, 0, position.top + columnSpace * i, getHeight(), paintGridLine);
        }

        for (int i = 0; i <= gridRows; i++) {
            canvas.drawLine(position.left, position.top + (i * rowSpace), position.right, position.top + (i * rowSpace), paintGridLine);
        }
    }

    public void draw(Canvas canvas, float beginX) {
        draw(canvas, frame.getLeftAxis(), beginX, 0);
    }

    public void draw(Canvas canvas, float beginX, float translateX) {

        drawGrid(canvas);

        for (ChartElement chartComponent : chartComponents) {
            chartComponent.draw(canvas, frame.getLeftAxis(), beginX, translateX);
        }

        frame.getLeftAxis().draw(canvas, frame.getViewport());
    }

    public void setGridLineSize(int size) {
        paintGridLine.setTextSize(size);
    }

    public void setGridLineColor(int color) {
        paintGridLine.setColor(color);
    }

    public List<ChartElement> getChartComponents() {
        return chartComponents;
    }

    public void setChartComponents(List<ChartElement> chartComponents) {
        this.chartComponents = chartComponents;
    }

    public void addDrawingElement(ChartElement chartComponent) {
        this.chartComponents.add(chartComponent);
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }


    public boolean isAutoScale() {
        return autoScale;
    }

    public void setAutoScale(boolean autoScale) {
        this.autoScale = autoScale;
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
}
