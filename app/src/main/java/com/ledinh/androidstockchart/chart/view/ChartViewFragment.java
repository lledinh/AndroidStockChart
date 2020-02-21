package com.ledinh.androidstockchart.chart.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.ledinh.androidstockchart.R;
import com.ledinh.androidstockchart.chart.util.Viewport;
import com.ledinh.androidstockchart.chart.view.element.DrawingArea;
import com.ledinh.androidstockchart.chart.view.element.DrawingElement;

import java.util.ArrayList;
import java.util.List;

public class ChartViewFragment {
    private List<DrawingElement> drawingElements;
    private DrawingArea drawingArea;

    private int gridColumns;
    private int gridRows;
    private Paint paintGridLine;
    private Paint paint;
    private boolean autoScale;

    public ChartViewFragment() {
        gridColumns = 4;
        gridRows = 4;
        paintGridLine = new Paint();
        drawingElements = new ArrayList<>();
        autoScale = false;
    }

    private void drawGrid(Canvas canvas) {
        float columnSpace = drawingArea.getViewport().getViewportWidth() / gridColumns;

        for (int i = 0; i <= gridColumns; i++) {
            canvas.drawLine(drawingArea.getViewport().getViewingPosition().top + columnSpace * i, 0, drawingArea.getViewport().getViewingPosition().top + columnSpace * i, drawingArea.getViewport().getViewportHeight(), paintGridLine);
        }
    }

    public void drawRows(Canvas canvas) {
        Viewport viewport = getDrawingArea().getViewport();
        float rowSpace = viewport.getViewportHeight() / gridRows;

        for (int i = 0; i <= gridRows; i++) {
            canvas.drawLine(viewport.getViewingPosition().left, viewport.getViewingPosition().top + (i * rowSpace), viewport.getViewingPosition().right, viewport.getViewingPosition().top + (i * rowSpace), paintGridLine);
        }
    }

    public void draw(Canvas canvas) {
        draw(canvas, 0);
    }

    public void draw(Canvas canvas, float translateX) {
        drawGrid(canvas);

        for (DrawingElement drawingElement: drawingElements) {
            drawingElement.draw(canvas, translateX);
        }

        drawRows(canvas);
        drawingArea.getLeftAxis().draw(canvas, drawingArea.getViewport());
//
//        drawTitle(canvas);
//        drawTimeline(canvas);
    }

    public void setGridLineSize(int size) {
        paintGridLine.setTextSize(size);
    }

    public void setGridLineColor(int color) {
        paintGridLine.setColor(color);
    }

    public List<DrawingElement> getDrawingElements() {
        return drawingElements;
    }

    public void setDrawingElements(List<DrawingElement> drawingElements) {
        this.drawingElements = drawingElements;
    }

    public void addDrawingElement(DrawingElement drawingElement) {
        this.drawingElements.add(drawingElement);
    }

    public DrawingArea getDrawingArea() {
        return drawingArea;
    }

    public void setDrawingArea(DrawingArea drawingArea) {
        this.drawingArea = drawingArea;
    }

    public int getGridColumns() {
        return gridColumns;
    }

    public void setGridColumns(int gridColumns) {
        this.gridColumns = gridColumns;
    }

    public boolean isAutoScale() {
        return autoScale;
    }

    public void setAutoScale(boolean autoScale) {
        this.autoScale = autoScale;
    }
}
