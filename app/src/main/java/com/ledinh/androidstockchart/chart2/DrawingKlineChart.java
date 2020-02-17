package com.ledinh.androidstockchart.chart2;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.ledinh.androidstockchart.chart.Kline;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DrawingKlineChart extends DrawingElement<KlinesSet> {
    private Paint paintIncreasing;
    private Paint paintDecreasing;
    private Paint paintSelectedKline;

    private float klineWidth;
    private float klineInnerLineWidth;

    private float lastDate;

    public DrawingKlineChart(Chart chart) {
        super(chart);

        paintIncreasing = new Paint();
        paintDecreasing = new Paint();
        paintSelectedKline = new Paint();

        klineWidth = 4;
        klineInnerLineWidth = 1;
        lastDate = 0;
    }

    @Override
    public void draw(Canvas canvas, float translateX) {
        drawRows(canvas);
        canvas.save();
        canvas.translate(translateX - (1 * spaceBetweenValue), 0);
        drawKlines(canvas, viewport.getViewportWidth());
        canvas.restore();
        drawValues(canvas);
        drawTimeline(canvas);
    }

    @Override
    public void drawTimeline(Canvas canvas) {

    }

    @Override
    public int getMaxIndex() {
        return chartData.getDataSize() - 1;
    }

    public void drawKlines(Canvas canvas, float highestX) {
        float x = highestX;

        for (int i = chartData.getValues().size() - 1; i >= 0 ; i--) {
            Kline kLine = chartData.getValues().get(i);
            Paint p;
            if (kLine.close > kLine.open) {
                p = paintIncreasing;
            }
            else {
                p = paintDecreasing;
            }

            double axisPosOpen = viewport.getViewingPosition().top + yAxis.getInvertedAxisPos(kLine.open) * viewport.getViewportHeight();
            double axisPosClose = viewport.getViewingPosition().top + yAxis.getInvertedAxisPos(kLine.close) * viewport.getViewportHeight();
            double axisPosHigh = viewport.getViewingPosition().top + yAxis.getInvertedAxisPos(kLine.high) * viewport.getViewportHeight();
            double axisPosLow = viewport.getViewingPosition().top + yAxis.getInvertedAxisPos(kLine.low) * viewport.getViewportHeight();

            drawKline(canvas, x, axisPosOpen, axisPosClose, axisPosHigh, axisPosLow, p);
            x -= spaceBetweenValue;
        }
    }

    private void drawKline(Canvas canvas, float x, double axisPosOpen, double axisPosClose, double axisPosHigh, double axisPosLow, Paint p) {
        canvas.drawRect(x, (float) axisPosOpen, x + klineWidth, (float) axisPosClose, p);
        canvas.drawRect(x + (klineWidth / 2f) - klineInnerLineWidth / 2, (float) axisPosHigh, x + (klineWidth / 2f) + klineInnerLineWidth / 2, (float) axisPosLow, p);
    }

    public void setIncreasingColor(int color) {
        paintIncreasing.setColor(color);
    }

    public void setDecreasingColor(int color) {
        paintDecreasing.setColor(color);
    }

    public void setSelectedKlineColor(int color) {
        paintSelectedKline.setColor(color);
    }

    public void setSelectedKlineLineWidth(float width) {
        paintSelectedKline.setStrokeWidth(width);
    }

    public void setKlineWidth(float klineWidth) {
        this.klineWidth = klineWidth;
    }

    public void setKlineInnerLineWidth(float klineInnerLineWidth) {
        this.klineInnerLineWidth = klineInnerLineWidth;
    }

    public void setYAxis(YAxis yAxis) {
        this.yAxis = yAxis;
    }

    @Override
    public void setChartData(KlinesSet klinesSet) {
        this.chartData = klinesSet;
        int lastIndex = chartData.getDataSize() - 1;
        if (autoScale) {
            updateAxisRangeFromIndex((int) (lastIndex - chart.getScreenDataCount()), lastIndex);
        }
        else {
            updateAxisRangeFromIndex(0, chartData.getDataSize() - 1);
        }
        yAxis.extendRange(20);
    }

    @Override
    public void updateAxisRangeFromIndex(int firstValueIndex, int lastValueIndex) {

        Kline kline = chartData.getValues().get(lastValueIndex);
        lastDate = kline.openTime;

        double axisMin = Float.MAX_VALUE;
        double axisMax = Float.MIN_VALUE;

        for (int i = lastValueIndex; i > firstValueIndex; i--) {
            Kline kLine = chartData.getValues().get(i);

            if (kLine.close < axisMin) {
                axisMin = kLine.close;
            }
            if (kLine.open < axisMin) {
                axisMin = kLine.open;
            }
            if (kLine.high < axisMin) {
                axisMin = kLine.high;
            }
            if (kLine.low < axisMin) {
                axisMin = kLine.low;
            }

            if (kLine.close > axisMax) {
                axisMax = kLine.close;
            }
            if (kLine.open > axisMax) {
                axisMax = kLine.open;
            }
            if (kLine.high > axisMax) {
                axisMax = kLine.high;
            }
            if (kLine.low > axisMax) {
                axisMax = kLine.low;
            }
        }

        yAxis.setAxisMin(axisMin);
        yAxis.setAxisMax(axisMax);
    }
}
