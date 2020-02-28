package com.ledinh.androidstockchart.chart3.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.ledinh.androidstockchart.chart.YAxis;
import com.ledinh.androidstockchart.chart.model.Kline;
import com.ledinh.androidstockchart.chart.set.KlinesSet;

public class KLineDrawing extends ChartElementDrawer<KlinesSet> {
    private Paint paintIncreasing;
    private Paint paintDecreasing;
    private Paint paintSelectedKline;

    private float klineWidth;
    private float klineInnerLineWidth;


    public KLineDrawing() {
        this.spaceBetweenValue = 0;
        paintIncreasing = new Paint();
        paintDecreasing = new Paint();
        paintSelectedKline = new Paint();
    }

    public KLineDrawing(float spaceBetweenValue) {
        this.spaceBetweenValue = spaceBetweenValue;
    }

    public void draw(Canvas canvas, KlinesSet data, YAxis yAxis, float translateX) {
        Log.d("KLineDrawing", "KLineDrawing");
        float x = 0;

        for (int i = 0; i < data.getValues().size(); i++) {
            Kline kLine = data.getValues().get(i);
            Paint p;
            if (kLine.close > kLine.open) {
                p = paintIncreasing;
            }
            else {
                p = paintDecreasing;
            }

            double axisPosOpen = position.top + yAxis.getInvertedAxisPos(kLine.open) * getHeight();
            double axisPosClose = position.top + yAxis.getInvertedAxisPos(kLine.close) * getHeight();
            double axisPosHigh = position.top + yAxis.getInvertedAxisPos(kLine.high) * getHeight();
            double axisPosLow = position.top + yAxis.getInvertedAxisPos(kLine.low) * getHeight();

            drawKline(canvas, x, axisPosOpen, axisPosClose, axisPosHigh, axisPosLow, p);
            x += spaceBetweenValue;
        }
    }

    private void drawKline(Canvas canvas, float x, double axisPosOpen, double axisPosClose, double axisPosHigh, double axisPosLow, Paint p) {
        canvas.drawRect(x, (float) axisPosOpen, x + klineWidth, (float) axisPosClose, p);
        canvas.drawRect(x + (klineWidth / 2f) - klineInnerLineWidth / 2, (float) axisPosHigh, x + (klineWidth / 2f) + klineInnerLineWidth / 2, (float) axisPosLow, p);
    }

    public Paint getPaintIncreasing() {
        return paintIncreasing;
    }

    public void setPaintIncreasing(Paint paintIncreasing) {
        this.paintIncreasing = paintIncreasing;
    }

    public Paint getPaintDecreasing() {
        return paintDecreasing;
    }

    public void setPaintDecreasing(Paint paintDecreasing) {
        this.paintDecreasing = paintDecreasing;
    }

    public Paint getPaintSelectedKline() {
        return paintSelectedKline;
    }

    public void setPaintSelectedKline(Paint paintSelectedKline) {
        this.paintSelectedKline = paintSelectedKline;
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
}


//package com.ledinh.androidstockchart.chart3.drawing;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.util.Log;
//
//import com.ledinh.androidstockchart.chart.model.Kline;
//import com.ledinh.androidstockchart.chart3.element.KLineElement;
//
//public class KLineDrawing {
//    private Paint paintIncreasing;
//    private Paint paintDecreasing;
//    private Paint paintSelectedKline;
//
//    private float klineWidth;
//    private float klineInnerLineWidth;
//
//    public KLineDrawing() {
//        paintIncreasing = new Paint();
//        paintDecreasing = new Paint();
//        paintSelectedKline = new Paint();
//
//        klineWidth = 4;
//        klineInnerLineWidth = 1;
//    }
//
//    public void draw(Canvas canvas, KLineElement kLineElement) {
//        canvas.save();
//        canvas.translate(translateX - (1 * spaceBetweenValue), 0);
//        drawKlines(canvas, beginDrawX); //chart.getFrame().getViewport().getViewportWidth());
//        canvas.restore();
//    }
//
//    public void drawKlines(Canvas canvas, float beginDrawX) {
//        float x = beginDrawX;
//
//        for (int i = 0; i < chartData.getValues().size(); i++) {
//            Kline kLine = chartData.getValues().get(i);
//            Paint p;
//            if (kLine.close > kLine.open) {
//                p = paintIncreasing;
//            }
//            else {
//                p = paintDecreasing;
//            }
//
//            double axisPosOpen = position.top + yAxis.getInvertedAxisPos(kLine.open) * getHeight();
//            double axisPosClose = position.top + yAxis.getInvertedAxisPos(kLine.close) * getHeight();
//            double axisPosHigh = position.top + yAxis.getInvertedAxisPos(kLine.high) * getHeight();
//            double axisPosLow = position.top + yAxis.getInvertedAxisPos(kLine.low) * getHeight();
//            Log.d("KLineActivity2", "axisPosOpen = " + axisPosOpen);
//            Log.d("KLineActivity2", "axisPosClose = " + axisPosOpen);
//            Log.d("KLineActivity2", "axisPosHigh = " + axisPosOpen);
//            Log.d("KLineActivity2", "axisPosLow = " + axisPosOpen);
//
//            drawKline(canvas, x, axisPosOpen, axisPosClose, axisPosHigh, axisPosLow, p);
//            x += spaceBetweenValue;
//            Log.d("KLineActivity2", "spaceBetweenValue = " + spaceBetweenValue);
//        }
//    }
//
//    private void drawKline(Canvas canvas, float x, double axisPosOpen, double axisPosClose, double axisPosHigh, double axisPosLow, Paint p) {
//        canvas.drawRect(x, (float) axisPosOpen, x + klineWidth, (float) axisPosClose, p);
//        canvas.drawRect(x + (klineWidth / 2f) - klineInnerLineWidth / 2, (float) axisPosHigh, x + (klineWidth / 2f) + klineInnerLineWidth / 2, (float) axisPosLow, p);
//    }
//
//    public void setIncreasingColor(int color) {
//        paintIncreasing.setColor(color);
//    }
//
//    public void setDecreasingColor(int color) {
//        paintDecreasing.setColor(color);
//    }
//
//    public void setSelectedKlineColor(int color) {
//        paintSelectedKline.setColor(color);
//    }
//
//    public void setSelectedKlineLineWidth(float width) {
//        paintSelectedKline.setStrokeWidth(width);
//    }
//
//    public void setKlineWidth(float klineWidth) {
//        this.klineWidth = klineWidth;
//    }
//
//    public void setKlineInnerLineWidth(float klineInnerLineWidth) {
//        this.klineInnerLineWidth = klineInnerLineWidth;
//    }
//}
