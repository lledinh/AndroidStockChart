package com.ledinh.androidstockchart.chart;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.ledinh.androidstockchart.chart2.KlinesSet;

public class KlineSetDrawing {
    private Paint paintIncreasing;
    private Paint paintDecreasing;
    private Paint paintTextAxis;
    private Paint paintSelectedKline;

    private float klineWidth;
    private float klineInnerLineWidth;
    private float klineSpace;

    private Viewport viewport;
    private Axis axis;
    private KlinesSet klinesSet;


    public KlineSetDrawing(KlinesSet klinesSet, Viewport viewport, Axis axis, float klineWidth, float klineInnerLineWidth, float klineSpace) {
        this.klinesSet = klinesSet;
        this.viewport = viewport;
        this.axis = axis;
        this.klineWidth = klineWidth;
        this.klineInnerLineWidth = klineInnerLineWidth;
        this.klineSpace = klineSpace;

        paintIncreasing = new Paint();
        paintDecreasing = new Paint();
        paintTextAxis = new Paint();
        paintSelectedKline = new Paint();
    }

    public void drawKlines(Canvas canvas, float highestX) {
        float x = highestX;
        for (int i = klinesSet.getValues().size() - 1; i >= 0 ; i--) {
            Kline kLine = klinesSet.getValues().get(i);
            Paint p;
            if (kLine.close > kLine.open) {
                p = paintIncreasing;
            }
            else {
                p = paintDecreasing;
            }

            double axisPosOpen = axis.getInvertedAxisPos(kLine.open) * viewport.getViewHeight();
            double axisPosClose = axis.getInvertedAxisPos(kLine.close) * viewport.getViewHeight();
            double axisPosHigh = axis.getInvertedAxisPos(kLine.high) * viewport.getViewHeight();
            double axisPosLow = axis.getInvertedAxisPos(kLine.low) * viewport.getViewHeight();

            drawKline(canvas, x, axisPosOpen, axisPosClose, axisPosHigh, axisPosLow, p);
            x -= klineWidth + klineSpace;
        }
    }

    private void drawKline(Canvas canvas, float x, double axisPosOpen, double axisPosClose, double axisPosHigh, double axisPosLow, Paint p) {
        canvas.drawRect(x, (float) axisPosOpen, x + klineWidth, (float) axisPosClose, p);
        canvas.drawRect(x + (klineWidth / 2f) - klineInnerLineWidth / 2, (float) axisPosHigh, x + (klineWidth / 2f) + klineInnerLineWidth / 2, (float) axisPosLow, p);
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public Axis getAxis() {
        return axis;
    }

    public void setAxis(Axis axis) {
        this.axis = axis;
    }

    public KlinesSet getKlinesSet() {
        return klinesSet;
    }

    public void setKlinesSet(KlinesSet klinesSet) {
        this.klinesSet = klinesSet;
    }

    public void setIncreasingColor(int color) {
        paintIncreasing.setColor(color);
    }

    public void setDecreasingColor(int color) {
        paintDecreasing.setColor(color);
    }

    public void setTextAxisColor(int color) {
        paintTextAxis.setColor(color);
    }

    public void setTextAxisSize(float size) {
        paintTextAxis.setTextSize(size);
    }

    public void setSelectedKlineSize(float lineWidth) {
        paintTextAxis.setStrokeWidth(lineWidth);
    }

    public void setSelectedKlineColor(int color) {
        paintSelectedKline.setColor(color);
    }

    public float getKlineWidth() {
        return klineWidth;
    }

    public void setKlineWidth(float klineWidth) {
        this.klineWidth = klineWidth;
    }

    public float getKlineInnerLineWidth() {
        return klineInnerLineWidth;
    }

    public void setKlineInnerLineWidth(float klineInnerLineWidth) {
        this.klineInnerLineWidth = klineInnerLineWidth;
    }

    public float getKlineSpace() {
        return klineSpace;
    }

    public void setKlineSpace(float klineSpace) {
        this.klineSpace = klineSpace;
    }
}
