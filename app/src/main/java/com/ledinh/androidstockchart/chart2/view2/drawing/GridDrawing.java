package com.ledinh.androidstockchart.chart2.view2.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.ledinh.androidstockchart.chart2.view2.Grid;

public class GridDrawing extends UIElement {
    private Paint paintGridLine;

    public GridDrawing() {
        this.paintGridLine = new Paint();
    }

    public void draw(Canvas canvas, Grid grid) {
        drawGrid(canvas, grid);
    }

    private void drawGrid(Canvas canvas, Grid grid) {
        drawGridColumns(canvas, grid);
        drawGridRows(canvas, grid);
    }

    private void drawGridColumns(Canvas canvas, Grid grid) {
        float columnSpace = getWidth() / grid.getGridColumns();

        for (int i = 0; i <= grid.getGridColumns(); i++) {
            canvas.drawLine(position.top + columnSpace * i, 0, position.top + columnSpace * i, getHeight(), paintGridLine);
        }
    }
    private void drawGridRows(Canvas canvas, Grid grid) {
        float rowSpace = getHeight() / grid.getGridRows();

        for (int i = 0; i <= grid.getGridRows(); i++) {
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
