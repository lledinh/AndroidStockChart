package com.ledinh.androidstockchart.chart3.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.ledinh.androidstockchart.chart.YAxis;
import com.ledinh.androidstockchart.chart3.element.simple.StaticGrid;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class YAxisDrawing extends StockChartElement {
    private Paint paintTextAxis;
    private Paint paintTextAxisBackground;
    private Paint paintTextUnit;
    private int leftPadding;


    public YAxisDrawing() {
        this.paintTextAxis = new Paint();
        this.paintTextAxis.setAntiAlias(true);
        this.paintTextAxisBackground = new Paint();
        this.paintTextUnit = new Paint();
        this.leftPadding = 10;
    }

    public void draw(Canvas canvas, StaticGrid staticGrid, YAxis yAxis) {
        drawYAxis(canvas, staticGrid, yAxis);
    }

    public void drawYAxis(Canvas canvas, StaticGrid staticGrid, YAxis yAxis) {
        drawAxisValues(canvas, staticGrid, yAxis);
        drawAxisUnit(canvas, staticGrid, yAxis);
    }

    public void drawAxisUnit(Canvas canvas, StaticGrid staticGrid, YAxis yAxis) {
        Paint.FontMetrics fm = paintTextUnit.getFontMetrics();
        float textHeight = fm.descent - fm.ascent;
        float baseLine = (textHeight - fm.bottom - fm.top) / 2;

        float textWidth = paintTextUnit.measureText(yAxis.getUnit());

        canvas.drawRoundRect(position.right - textWidth - 5, (float) position.top + 5, (float) position.right - 5, position.top + baseLine + 5, 5, 5, paintTextAxisBackground);
        canvas.drawText(yAxis.getUnit(), position.right - textWidth - 5, position.top + textHeight + 5, paintTextUnit);
    }

    public void drawAxisValues(Canvas canvas, StaticGrid staticGrid, YAxis yAxis) {
        Paint.FontMetrics fm = paintTextAxis.getFontMetrics();
        float textHeight = fm.descent - fm.ascent;
        float baseLine = (textHeight - fm.bottom - fm.top) / 2;

        int gridRows = staticGrid.getGridRows();

        float rowSpace = getHeight() / gridRows;
        float axisMax = (float) yAxis.getAxisMax();
        float axisMin = (float) yAxis.getAxisMin();

        float range = (axisMax - axisMin) / staticGrid.getGridRows();

        for (int i = 0; i <= gridRows; i++) {
            NumberFormat formatter = new DecimalFormat("#0");
            String value = formatter.format(axisMax - (i * range));

            float textWidth = paintTextAxis.measureText(value);

            if (i == 0) {
                canvas.drawRect(leftPadding, position.top + (i * rowSpace), leftPadding + textWidth, position.top + (i * rowSpace) + baseLine, paintTextAxisBackground);
                canvas.drawText(value, leftPadding, position.top + (i * rowSpace) + baseLine, paintTextAxis);
            }
            else if (i < gridRows) {
                canvas.drawRect(leftPadding, position.top + (i * rowSpace) - textHeight / 2, leftPadding + textWidth, position.top + (i * rowSpace) + textHeight / 2, paintTextAxisBackground);
                canvas.drawText(value, leftPadding, position.top + (i * rowSpace) + textHeight / 2, paintTextAxis);
            }
            else {
                canvas.drawRect(leftPadding, position.top + (i * rowSpace) - textHeight, leftPadding + textWidth, position.top + (i * rowSpace), paintTextAxisBackground);
                canvas.drawText(value, leftPadding, position.top + (i * rowSpace), paintTextAxis);
            }
        }
    }

    public void setTextAxisColor(int color) {
        paintTextAxis.setColor(color);
    }

    public void setTextAxisSize(float size) {
        paintTextAxis.setTextSize(size);
    }

    public void setTextAxisBackgroundColor(int color) {
        paintTextAxisBackground.setColor(color);
    }

    public void setTextUnitSize(float size) {
        paintTextUnit.setTextSize(size);
    }

    public void setTextUnitColor(int color) {
        paintTextUnit.setColor(color);
    }

    public int getLeftPadding() {
        return leftPadding;
    }

    public void setLeftPadding(int leftPadding) {
        this.leftPadding = leftPadding;
    }

}