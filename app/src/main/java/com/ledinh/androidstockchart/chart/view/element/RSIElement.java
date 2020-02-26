package com.ledinh.androidstockchart.chart.view.element;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.ledinh.androidstockchart.chart.YAxis;
import com.ledinh.androidstockchart.chart.model.RSI;
import com.ledinh.androidstockchart.chart.set.RSISet;
import com.ledinh.androidstockchart.chart.view.container.Chart;

public class RSIElement extends ChartElement<RSISet> {
    private Paint paintLine;
    private float lastDate;


    public RSIElement(Rect position, YAxis yAxis) {
        super(position, yAxis);
        paintLine = new Paint();
        paintLine.setAntiAlias(true);
    }

    @Override
    public void draw(Canvas canvas, float beginDrawX, float translateX) {
        canvas.save();
        canvas.translate(translateX - (1 * spaceBetweenValue), 0);
        drawLines(canvas, beginDrawX);
        canvas.restore();
    }

    public void drawLines(Canvas canvas, float highestX) {
        float x = highestX;

        for (int i = chartData.getValues().size() - 1; i >= 1 ; i--) {
            RSI rsi1 = chartData.getValues().get(i);
            RSI rsi2 = chartData.getValues().get(i - 1);

            double axisPos = position.top + yAxis.getInvertedAxisPos(rsi1.rsi) * getHeight();
            double axisPos1 = position.top + yAxis.getInvertedAxisPos(rsi2.rsi) * getHeight();

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
