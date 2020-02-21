package com.ledinh.androidstockchart.chart.view.element;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.ledinh.androidstockchart.chart.YAxis;
import com.ledinh.androidstockchart.chart.model.Kline;
import com.ledinh.androidstockchart.chart.set.KlinesSet;
import com.ledinh.androidstockchart.chart.view.ChartViewFragment;

public class DrawingKlineChart extends DrawingElement<KlinesSet> {
    private Paint paintIncreasing;
    private Paint paintDecreasing;
    private Paint paintSelectedKline;

    private float klineWidth;
    private float klineInnerLineWidth;

    private float lastDate;

    public DrawingKlineChart(ChartViewFragment chartViewFragment) {
        super(chartViewFragment);
        paintIncreasing = new Paint();
        paintDecreasing = new Paint();
        paintSelectedKline = new Paint();

        klineWidth = 4;
        klineInnerLineWidth = 1;
        lastDate = 0;
    }

    @Override
    public void draw(Canvas canvas, float translateX) {
        canvas.save();
        canvas.translate(translateX - (1 * spaceBetweenValue), 0);
        drawKlines(canvas, chartViewFragment.getDrawingArea().getViewport().getViewportWidth());
        canvas.restore();
        drawTimeline(canvas);
    }

    @Override
    public void drawTimeline(Canvas canvas) {

    }

    public void drawKlines(Canvas canvas, float highestX) {
        float x = highestX;

        // The chart is drawn from right to left, beginning with the most recent value (last value of chartData)
        for (int i = chartData.getValues().size() - 1; i >= 0 ; i--) {
            Kline kLine = chartData.getValues().get(i);
            Paint p;
            if (kLine.close > kLine.open) {
                p = paintIncreasing;
            }
            else {
                p = paintDecreasing;
            }

//            double axisPosOpen = chartViewFragment.getDrawingArea().getViewport().getViewingPosition().top + yAxis.getInvertedAxisPos(kLine.open) * chartViewFragment.getDrawingArea().getViewport().getViewportHeight();
//            double axisPosClose = chartViewFragment.getDrawingArea().getViewport().getViewingPosition().top + yAxis.getInvertedAxisPos(kLine.close) * chartViewFragment.getDrawingArea().getViewport().getViewportHeight();
//            double axisPosHigh = chartViewFragment.getDrawingArea().getViewport().getViewingPosition().top + yAxis.getInvertedAxisPos(kLine.high) * chartViewFragment.getDrawingArea().getViewport().getViewportHeight();
//            double axisPosLow = chartViewFragment.getDrawingArea().getViewport().getViewingPosition().top + yAxis.getInvertedAxisPos(kLine.low) * chartViewFragment.getDrawingArea().getViewport().getViewportHeight();

            double axisPosOpen = chartViewFragment.getDrawingArea().getViewport().getViewingPosition().top + chartViewFragment.getDrawingArea().getLeftAxis().getInvertedAxisPos(kLine.open) * chartViewFragment.getDrawingArea().getViewport().getViewportHeight();
            double axisPosClose = chartViewFragment.getDrawingArea().getViewport().getViewingPosition().top + chartViewFragment.getDrawingArea().getLeftAxis().getInvertedAxisPos(kLine.close) * chartViewFragment.getDrawingArea().getViewport().getViewportHeight();
            double axisPosHigh = chartViewFragment.getDrawingArea().getViewport().getViewingPosition().top + chartViewFragment.getDrawingArea().getLeftAxis().getInvertedAxisPos(kLine.high) * chartViewFragment.getDrawingArea().getViewport().getViewportHeight();
            double axisPosLow = chartViewFragment.getDrawingArea().getViewport().getViewingPosition().top + chartViewFragment.getDrawingArea().getLeftAxis().getInvertedAxisPos(kLine.low) * chartViewFragment.getDrawingArea().getViewport().getViewportHeight();

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

//    @Override
//    public void setChartData(KlinesSet klinesSet) {
//        this.chartData = klinesSet;
//        int lastIndex = chartData.getDataSize() - 1;
//        if (autoScale) {
//            updateAxisRangeFromIndex((int) (lastIndex - chartView.getScreenDataCount()), lastIndex);
//        }
//        else {
//            updateAxisRangeFromIndex(0, chartData.getDataSize() - 1);
//        }
//        yAxis.extendRange(20);
//    }

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
