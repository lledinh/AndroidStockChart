package com.ledinh.androidstockchart.chart2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.ledinh.androidstockchart.chart.Kline;
import com.ledinh.androidstockchart.chart.RSI;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DrawingRSIChart extends DrawingElement<RSISet> {
    private Paint paintLine;
    private float lastDate;


    public DrawingRSIChart(Chart chart) {
        super(chart);
        paintLine = new Paint();
        paintLine.setAntiAlias(true);
    }

    @Override
    public void draw(Canvas canvas, float translateX) {
        drawRows(canvas);
        canvas.save();
        canvas.translate(translateX - (1 * spaceBetweenValue), 0);
        drawLines(canvas, viewport.getViewportWidth());
        canvas.restore();
        drawValues(canvas);
        drawTimeline(canvas);
    }

    public void drawLines(Canvas canvas, float highestX) {
        float x = highestX;

        for (int i = chartData.values.size() - 1; i >= 1 ; i--) {
            RSI rsi1 = chartData.values.get(i);
            RSI rsi2 = chartData.values.get(i - 1);

            double axisPos = viewport.getViewingPosition().top + yAxis.getInvertedAxisPos(rsi1.rsi) * viewport.getViewportHeight();
            double axisPos1 = viewport.getViewingPosition().top + yAxis.getInvertedAxisPos(rsi2.rsi) * viewport.getViewportHeight();

            canvas.drawLine(x, (float) axisPos, x - spaceBetweenValue, (float) axisPos1, paintLine);
            x -= spaceBetweenValue;
        }
    }

    @Override
    public void drawTimeline(Canvas canvas) {

    }

    @Override
    public int getMaxIndex() {
        return chartData.getDataSize() - 1;
    }

    @Override
    public void updateAxisRangeFromIndex(int firstValueIndex, int lastValueIndex) {
        double axisMin = Float.MAX_VALUE;
        double axisMax = Float.MIN_VALUE;

        for (int i = lastValueIndex; i > firstValueIndex; i--) {
            RSI rsi2 = chartData.getValues().get(i);

            if (rsi2.rsi < axisMin) {
                axisMin = rsi2.rsi;
            }

            if (rsi2.rsi > axisMax) {
                axisMax = rsi2.rsi;
            }
        }

        yAxis.setAxisMin(axisMin);
        yAxis.setAxisMax(axisMax);
    }

    public void setLineColor(int color) {
        paintLine.setColor(color);
    }

    public void setLineWidth(float width) {
        paintLine.setStrokeWidth(width);
    }
}
