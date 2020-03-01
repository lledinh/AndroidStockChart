package com.ledinh.androidstockchart.chart3.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.ledinh.androidstockchart.chart.YAxis;
import com.ledinh.androidstockchart.chart.model.RSI;
import com.ledinh.androidstockchart.chart.set.KlinesSet;
import com.ledinh.androidstockchart.chart.set.RSISet;

public class RSIDrawing extends ChartElementDrawer<RSISet> {
    private Paint paintLine;

    public RSIDrawing() {
        paintLine = new Paint();
        paintLine.setAntiAlias(true);
    }

    public RSIDrawing(Rect position) {
        super(position);
        paintLine = new Paint();
        paintLine.setAntiAlias(true);
    }

    @Override
    public void draw(Canvas canvas, RSISet data, YAxis yAxis, float translateX) {
        canvas.save();
        canvas.translate(translateX - (1 * spaceBetweenValue), 0);
        drawLines(canvas, data, yAxis);
        canvas.restore();
    }


    public void drawLines(Canvas canvas, RSISet data, YAxis yAxis) {
        float x = 0;

        for (int i = 1; i < data.getValues().size(); i++) {
            RSI rsi1 = data.getValues().get(i - 1);
            RSI rsi2 = data.getValues().get(i);

            double axisPos = position.top + yAxis.getInvertedAxisPos(rsi1.rsi) * getHeight();
            double axisPos1 = position.top + yAxis.getInvertedAxisPos(rsi2.rsi) * getHeight();

            canvas.drawLine(x, (float) axisPos, x + spaceBetweenValue, (float) axisPos1, paintLine);
            x += spaceBetweenValue;
        }
    }

    public void setLineColor(int color) {
        paintLine.setColor(color);
    }

    public void setLineWidth(float width) {
        paintLine.setStrokeWidth(width);
    }
}


