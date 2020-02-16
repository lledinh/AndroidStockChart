package com.ledinh.androidstockchart.chart2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.ledinh.androidstockchart.R;
import com.ledinh.androidstockchart.chart.Kline;
import com.ledinh.androidstockchart.chart.KlinesSet;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DrawingKlineChart extends DrawingElement<KlinesSet> {
    private Paint paintIncreasing;
    private Paint paintDecreasing;
    private Paint paintSelectedKline;

    private float klineWidth;
    private float klineInnerLineWidth;

    private float lastDate;
    private int gridRows;

    public DrawingKlineChart(Chart chart) {
        super(chart);

        paintIncreasing = new Paint();
        paintDecreasing = new Paint();
        paintSelectedKline = new Paint();

        klineWidth = 4;
        klineInnerLineWidth = 1;
        gridRows = 4;
        lastDate = 0;
    }

    @Override
    public void draw(Canvas canvas, float translateX) {
        canvas.save();
        canvas.translate(translateX - (1 * spaceBetweenValue), 0);
        drawKlines(canvas, viewport.getViewWidth());
        canvas.restore();

        drawValues(canvas);
        drawTimeline(canvas);
    }

    @Override
    public void drawValues(Canvas canvas) {
        Paint.FontMetrics fm = chart.getPaintTextAxis().getFontMetrics();
        float textHeight = fm.descent - fm.ascent;
        float baseLine = (textHeight - fm.bottom - fm.top) / 2;

        float rowSpace = viewport.getViewHeight() / gridRows;
        float range = (float) ((yAxis.getAxisMax() - yAxis.getAxisMin()) / gridRows);

        for (int i = 0; i <= gridRows; i++) {
            NumberFormat formatter = new DecimalFormat("#0.00");
            String value = formatter.format(yAxis.getAxisMax() - (i * range));
            canvas.drawText(value, 0, viewport.getViewingPosition().top + (i * rowSpace) + baseLine, chart.getPaintTextAxis());
        }
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

            double axisPosOpen = yAxis.getInvertedAxisPos(kLine.open) * viewport.getViewHeight();
            double axisPosClose = yAxis.getInvertedAxisPos(kLine.close) * viewport.getViewHeight();
            double axisPosHigh = yAxis.getInvertedAxisPos(kLine.high) * viewport.getViewHeight();
            double axisPosLow = yAxis.getInvertedAxisPos(kLine.low) * viewport.getViewHeight();

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

    public void setKlinesSet(KlinesSet klinesSet) {
        this.chartData = klinesSet;
        List<Kline> klines = klinesSet.getValues();
        setAxisRange(klines.size() - 1);
        yAxis.extendRange(20);
//        int klineIndexFromX = chart.getIndexFromX((int) (viewport.getViewWidth() - translateX));
    }

    public void setAxisRange(int lastIndex) {

        Kline kline = chartData.getValues().get(lastIndex);
        lastDate = kline.openTime;

        double axisMin = Float.MAX_VALUE;
        double axisMax = Float.MIN_VALUE;

        for (int i = lastIndex; i > lastIndex - chart.getScreenDataCount(); i--) {
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

        Log.d("KLineChartView", "setAxisRange axisMax = " + axisMax + " axisMin = " + axisMin);
    }
}
