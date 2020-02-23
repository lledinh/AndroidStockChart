package com.ledinh.androidstockchart.chart.view.element;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.ledinh.androidstockchart.chart.YAxis;
import com.ledinh.androidstockchart.chart.model.RSI;
import com.ledinh.androidstockchart.chart.set.RSISet;
import com.ledinh.androidstockchart.chart.view.container.Chart;

public class RSIElement extends ChartElement<RSISet> {
    private Paint paintLine;
    private float lastDate;


    public RSIElement(Chart chart) {
        super(chart);
        paintLine = new Paint();
        paintLine.setAntiAlias(true);
    }

    @Override
    public void draw(Canvas canvas, YAxis yAxis, float beginDrawX, float translateX) {
        canvas.save();
        canvas.translate(translateX - (1 * spaceBetweenValue), 0);
        drawLines(canvas, yAxis, beginDrawX);
        canvas.restore();
    }

    public void drawLines(Canvas canvas, YAxis yAxis, float highestX) {
        float x = highestX;

        for (int i = chartData.getValues().size() - 1; i >= 1 ; i--) {
            RSI rsi1 = chartData.getValues().get(i);
            RSI rsi2 = chartData.getValues().get(i - 1);

            double axisPos = chart.getFrame().getViewport().getViewingPosition().top + chart.getFrame().getLeftAxis().getInvertedAxisPos(rsi1.rsi) * chart.getFrame().getViewport().getViewportHeight();
            double axisPos1 = chart.getFrame().getViewport().getViewingPosition().top + chart.getFrame().getLeftAxis().getInvertedAxisPos(rsi2.rsi) * chart.getFrame().getViewport().getViewportHeight();

            canvas.drawLine(x, (float) axisPos, x - spaceBetweenValue, (float) axisPos1, paintLine);
            x -= spaceBetweenValue;
        }
    }

    public void setLineColor(int color) {
        paintLine.setColor(color);
    }

    public void setLineWidth(float width) {
        paintLine.setStrokeWidth(width);
    }
}
