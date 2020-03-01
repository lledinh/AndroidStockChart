package com.ledinh.androidstockchart.chart3.drawing.expanded;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.ledinh.androidstockchart.chart3.drawing.StockChartElement;
import com.ledinh.androidstockchart.chart3.element.simple.StaticGrid;

public class ExpandedGridDrawing extends StockChartElement {
    private Paint paintGridLine;

    public ExpandedGridDrawing() {
        this.paintGridLine = new Paint();
    }

    public void draw(Canvas canvas, StaticGrid staticGrid, float translateX) {
        drawGrid(canvas, staticGrid, translateX);
    }

    private void drawGrid(Canvas canvas, StaticGrid staticGrid, float translateX) {
        canvas.save();
        canvas.translate(translateX, 0);
        drawGridColumns(canvas, staticGrid);
        canvas.restore();

        drawGridRows(canvas, staticGrid);
    }

    private void drawGridColumns(Canvas canvas, StaticGrid staticGrid) {
        float columnSpace = (getWidth() * 10) / staticGrid.getGridColumns();

        for (int i = 0; i <= staticGrid.getGridColumns(); i++) {
            canvas.drawLine(position.left + columnSpace * i, position.top, position.left + columnSpace * i, position.bottom, paintGridLine);
        }
    }

    private void drawGridRows(Canvas canvas, StaticGrid staticGrid) {
        float rowSpace = getHeight() / staticGrid.getGridRows();

        for (int i = 0; i <= staticGrid.getGridRows(); i++) {
            canvas.drawLine(position.left, position.top + (i * rowSpace), position.right, position.top + (i * rowSpace), paintGridLine);
        }
    }


    public void setGridLineColor(int color) {
        paintGridLine.setColor(color);
    }

    public void setGridLineSize(float size) {
        paintGridLine.setStrokeWidth(size);
    }
}