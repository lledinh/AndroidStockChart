package com.ledinh.androidstockchart.chart.view.element;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.ledinh.androidstockchart.chart.YAxis;
import com.ledinh.androidstockchart.chart.model.Kline;
import com.ledinh.androidstockchart.chart.set.KlinesSet;
import com.ledinh.androidstockchart.chart.view.container.Chart;

public class KLineElement extends ChartElement<KlinesSet> {
    private Paint paintIncreasing;
    private Paint paintDecreasing;
    private Paint paintSelectedKline;

    private float klineWidth;
    private float klineInnerLineWidth;

    private float lastDate;

    public KLineElement(Chart chart) {
        super(chart);
        paintIncreasing = new Paint();
        paintDecreasing = new Paint();
        paintSelectedKline = new Paint();

        klineWidth = 4;
        klineInnerLineWidth = 1;
        lastDate = 0;
    }

    @Override
    public void draw(Canvas canvas, YAxis yAxis, float beginDrawX, float translateX) {
        canvas.save();
        canvas.translate(translateX - (1 * spaceBetweenValue), 0);
        drawKlines(canvas, yAxis, beginDrawX); //chart.getFrame().getViewport().getViewportWidth());
        canvas.restore();
    }

    public void drawKlines(Canvas canvas, YAxis yAxis, float beginDrawX) {
        float x = beginDrawX;

        // The chart is drawn from right to left, beginning with the most recent value (last value of chartData)
        for (int i = 0; i < chartData.getValues().size(); i++) {
            Kline kLine = chartData.getValues().get(i);
            Paint p;
            if (kLine.close > kLine.open) {
                p = paintIncreasing;
            }
            else {
                p = paintDecreasing;
            }

            double axisPosOpen = chart.getFrame().getViewport().getViewingPosition().top + yAxis.getInvertedAxisPos(kLine.open) * chart.getFrame().getViewport().getViewportHeight();
            double axisPosClose = chart.getFrame().getViewport().getViewingPosition().top + yAxis.getInvertedAxisPos(kLine.close) * chart.getFrame().getViewport().getViewportHeight();
            double axisPosHigh = chart.getFrame().getViewport().getViewingPosition().top + yAxis.getInvertedAxisPos(kLine.high) * chart.getFrame().getViewport().getViewportHeight();
            double axisPosLow = chart.getFrame().getViewport().getViewingPosition().top + yAxis.getInvertedAxisPos(kLine.low) * chart.getFrame().getViewport().getViewportHeight();

            drawKline(canvas, x, axisPosOpen, axisPosClose, axisPosHigh, axisPosLow, p);
            x += spaceBetweenValue;
        }
    }

//    public void drawKlines(Canvas canvas, YAxis yAxis, float highestX) {
//        float x = highestX;
//
//        // The chart is drawn from right to left, beginning with the most recent value (last value of chartData)
//        for (int i = chartData.getValues().size() - 1; i >= 0 ; i--) {
//            Kline kLine = chartData.getValues().get(i);
//            Paint p;
//            if (kLine.close > kLine.open) {
//                p = paintIncreasing;
//            }
//            else {
//                p = paintDecreasing;
//            }
//
//            double axisPosOpen = chart.getFrame().getViewport().getViewingPosition().top + yAxis.getInvertedAxisPos(kLine.open) * chart.getFrame().getViewport().getViewportHeight();
//            double axisPosClose = chart.getFrame().getViewport().getViewingPosition().top + yAxis.getInvertedAxisPos(kLine.close) * chart.getFrame().getViewport().getViewportHeight();
//            double axisPosHigh = chart.getFrame().getViewport().getViewingPosition().top + yAxis.getInvertedAxisPos(kLine.high) * chart.getFrame().getViewport().getViewportHeight();
//            double axisPosLow = chart.getFrame().getViewport().getViewingPosition().top + yAxis.getInvertedAxisPos(kLine.low) * chart.getFrame().getViewport().getViewportHeight();
//
//            drawKline(canvas, x, axisPosOpen, axisPosClose, axisPosHigh, axisPosLow, p);
//            x -= spaceBetweenValue;
//        }
//    }

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

}
