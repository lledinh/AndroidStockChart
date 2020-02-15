package com.ledinh.androidstockchart.chart;

import android.graphics.Paint;

public class RSISetDrawing {
    private Paint paint;
    private float lineSpace;

    public RSISetDrawing(float lineSpace) {
        this.lineSpace = lineSpace;
        paint = new Paint();
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public int getColor() {
        return paint.getColor();
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public float getLineWidth() {
        return paint.getStrokeWidth();
    }

    public void setLineWidth(float lineWidth) {
        paint.setStrokeWidth(lineWidth);
    }

    public float getLineSpace() {
        return lineSpace;
    }

    public void setLineSpace(float lineSpace) {
        this.lineSpace = lineSpace;
    }
}
