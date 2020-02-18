package com.ledinh.androidstockchart.chart;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.ledinh.androidstockchart.chart.util.Viewport;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class YAxis {
    private double axisMin = Integer.MAX_VALUE;
    private double axisMax = Integer.MIN_VALUE;

    private Paint paintTextAxis;
    private Paint paintTextAxisBackground;
    private Paint paintTextUnit;
    private int gridRows;

    private int leftPadding;
    private String unit;

    public YAxis() {
        this.paintTextAxis = new Paint();
        this.paintTextAxis.setAntiAlias(true);
        this.paintTextAxisBackground = new Paint();
        this.paintTextUnit = new Paint();
        this.gridRows = 4;
        this.unit = "";
    }

    public YAxis(double axisMin, double axisMax) {
        this.axisMin = axisMin;
        this.axisMax = axisMax;
        this.gridRows = 4;
    }

    public void setGridRows(int gridRows) {
        this.gridRows = gridRows;
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



    public void draw(Canvas canvas, Viewport viewport) {
        drawAxisValues(canvas, viewport);
        drawAxisUnit(canvas, viewport);
    }

    public void drawAxisUnit(Canvas canvas, Viewport viewport) {
        Paint.FontMetrics fm = paintTextUnit.getFontMetrics();
        float textHeight = fm.descent - fm.ascent;
        float baseLine = (textHeight - fm.bottom - fm.top) / 2;

        float textWidth = paintTextUnit.measureText(unit);

        canvas.drawRoundRect(viewport.getViewingPosition().right - textWidth - 5, (float) viewport.getViewingPosition().top + 5, (float) viewport.getViewingPosition().right - 5, viewport.getViewingPosition().top + baseLine + 5, 5, 5, paintTextAxisBackground);
        canvas.drawText(unit, viewport.getViewingPosition().right - textWidth - 5, viewport.getViewingPosition().top + textHeight + 5, paintTextUnit);
    }

    public void drawAxisValues(Canvas canvas, Viewport viewport) {
        Paint.FontMetrics fm = paintTextAxis.getFontMetrics();
        float textHeight = fm.descent - fm.ascent;
        float baseLine = (textHeight - fm.bottom - fm.top) / 2;

        float rowSpace = viewport.getViewportHeight() / gridRows;
        float range = (float) ((axisMax - axisMin) / gridRows);

        for (int i = 0; i <= gridRows; i++) {
            NumberFormat formatter = new DecimalFormat("#0");
            String value = formatter.format(axisMax - (i * range));

            float textWidth = paintTextAxis.measureText(value);

            if (i == 0) {
                canvas.drawRect(leftPadding, viewport.getViewingPosition().top + (i * rowSpace), leftPadding + textWidth, viewport.getViewingPosition().top + (i * rowSpace) + baseLine, paintTextAxisBackground);
                canvas.drawText(value, leftPadding, viewport.getViewingPosition().top + (i * rowSpace) + baseLine, paintTextAxis);
            }
            else if (i < gridRows) {
                canvas.drawRect(leftPadding, viewport.getViewingPosition().top + (i * rowSpace) - textHeight / 2, leftPadding + textWidth, viewport.getViewingPosition().top + (i * rowSpace) + textHeight / 2, paintTextAxisBackground);
                canvas.drawText(value, leftPadding, viewport.getViewingPosition().top + (i * rowSpace) + textHeight / 2, paintTextAxis);
            }
            else {
                canvas.drawRect(leftPadding, viewport.getViewingPosition().top + (i * rowSpace) - textHeight, leftPadding + textWidth, viewport.getViewingPosition().top + (i * rowSpace), paintTextAxisBackground);
                canvas.drawText(value, leftPadding, viewport.getViewingPosition().top + (i * rowSpace), paintTextAxis);
            }
        }
    }

    public void extendRange(float ratio) {
        double range = axisMax - axisMin;
        axisMax += (range / ratio);
        axisMin -= (range / ratio);
    }

    /**
     * Return a value between 0 and 1
     * 0.5 means that the position is located at half the axis
     * @param value
     * @return
     */
    public double getAxisPos(double value) {
        double range = axisMax - axisMin;
        value -= axisMin;

        return (value / range);
    }

    /**
     * Return a value between 0 and 1
     * 0.5 means that the position is located at half the axis
     * @param value
     * @return
     */
    public double getInvertedAxisPos(double value) {
        double range = axisMax - axisMin;
        value -= axisMin;

        return 1 - (value / range);
    }

    public double getAxisMin() {
        return axisMin;
    }

    public void setAxisMin(double axisMin) {
        this.axisMin = axisMin;
    }

    public double getAxisMax() {
        return axisMax;
    }

    public void setAxisMax(double axisMax) {
        this.axisMax = axisMax;
    }

    public int getGridRows() {
        return gridRows;
    }

    public int getLeftPadding() {
        return leftPadding;
    }

    public void setLeftPadding(int leftPadding) {
        this.leftPadding = leftPadding;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}