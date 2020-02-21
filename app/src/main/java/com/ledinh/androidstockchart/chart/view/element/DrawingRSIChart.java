package com.ledinh.androidstockchart.chart.view.element;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.ledinh.androidstockchart.chart.model.RSI;
import com.ledinh.androidstockchart.chart.set.RSISet;
import com.ledinh.androidstockchart.chart.view.ChartViewFragment;

public class DrawingRSIChart extends DrawingElement<RSISet> {
    private Paint paintLine;
    private float lastDate;


    public DrawingRSIChart(ChartViewFragment chartViewFragment) {
        super(chartViewFragment);
        paintLine = new Paint();
        paintLine.setAntiAlias(true);
    }

    @Override
    public void draw(Canvas canvas, float translateX) {
        canvas.save();
        canvas.translate(translateX - (1 * spaceBetweenValue), 0);
        drawLines(canvas, chartViewFragment.getDrawingArea().getViewport().getViewportWidth());
        canvas.restore();
        drawTimeline(canvas);
    }

    public void drawLines(Canvas canvas, float highestX) {
        float x = highestX;

        for (int i = chartData.getValues().size() - 1; i >= 1 ; i--) {
            RSI rsi1 = chartData.getValues().get(i);
            RSI rsi2 = chartData.getValues().get(i - 1);

            double axisPos = chartViewFragment.getDrawingArea().getViewport().getViewingPosition().top + chartViewFragment.getDrawingArea().getLeftAxis().getInvertedAxisPos(rsi1.rsi) * chartViewFragment.getDrawingArea().getViewport().getViewportHeight();
            double axisPos1 = chartViewFragment.getDrawingArea().getViewport().getViewingPosition().top + chartViewFragment.getDrawingArea().getLeftAxis().getInvertedAxisPos(rsi2.rsi) * chartViewFragment.getDrawingArea().getViewport().getViewportHeight();

            canvas.drawLine(x, (float) axisPos, x - spaceBetweenValue, (float) axisPos1, paintLine);
            x -= spaceBetweenValue;
        }
    }

    @Override
    public void drawTimeline(Canvas canvas) {

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
