package com.ledinh.androidstockchart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;

import com.ledinh.androidstockchart.R;
import com.ledinh.androidstockchart.chart2.RSISet;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RSIChartView extends View implements
        GestureDetector.OnGestureListener {

    private GestureDetectorCompat mDetector;
    private OverScroller mScroller;

    private List<RSISet> rsiSets;

    private Paint paint;
    private Paint paintGridLine;
    private Paint paintTextAxis;

    private float lineWidth;
    private float lineSpace;

    private int gridRows = 5;
    private int gridColumns = 4;

    private double axisMin = Integer.MAX_VALUE;
    private double axisMax = Integer.MIN_VALUE;

    private long xDate = 0;

    public RSIChartView(Context context) {
        super(context);
        init();
    }

    public RSIChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RSIChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public RSIChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public List<RSISet> getRsiSets() {
        return rsiSets;
    }

    public void addRsiSet(RSISet rsiSet) {
        rsiSets.add(rsiSet);
    }

    public void init() {
        paint = new Paint();
        paintGridLine = new Paint();
        paintTextAxis = new Paint();

        paint.setColor(Color.rgb(0xDF, 0x35, 0x36));
        paint.setAntiAlias(true);

        paintGridLine.setColor(Color.rgb(0x35, 0x39, 0x41));

        paintTextAxis.setColor(Color.rgb(0xB1, 0xB2, 0xB6));
        paintTextAxis.setAntiAlias(true);
        paintTextAxis.setTextSize(getResources().getDimension(R.dimen.chart_text_size));

        lineWidth = getResources().getDimension(R.dimen.chart_kline_width);
        lineSpace = lineWidth / 2;

        mDetector = new GestureDetectorCompat(getContext(), this);
        mScroller = new OverScroller(getContext());

        rsiSets = new ArrayList<>();

        setAxisRange();
    }

    public void setAxisRange() {
        axisMin = 0;
        axisMax = 100;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.chart_background));

        drawGrid(canvas);

        if (rsiSets != null) {
            canvas.save();
//            canvas.translate(translateX - 1 * (lineWidth + lineWidth / 2f), 0);
            for (RSISet rsiSet : rsiSets) {
                drawRSI(canvas, rsiSet);
            }
            canvas.restore();
        }

        drawText(canvas);
    }



    private void drawGrid(Canvas canvas) {
        float rowSpace = getHeight() / gridRows;
        float columnSpace = getWidth() / gridColumns;

        for (int i = 0; i <= gridRows; i++) {
            canvas.drawLine(0, rowSpace * i, getWidth(), rowSpace * i, paintGridLine);
        }

        for (int i = 0; i <= gridColumns; i++) {
            canvas.drawLine(columnSpace * i, 0, columnSpace * i, getHeight(), paintGridLine);
        }
    }

    private double getAxisPos(double value) {
        double range = axisMax - axisMin;
        value -= axisMin;
        double ratio = value / range;

        double pos = (1 - ratio) * (getHeight());

        return pos;
    }

    private void drawRSI(Canvas canvas, RSISet rsiSet) {
        float x = getWidth();
        for (int i = rsiSet.getValues().size() - 1; i >= 1 ; i--) {
            RSI rsi1 = rsiSet.getValues().get(i);
            RSI rsi2 = rsiSet.getValues().get(i - 1);

            double axisPos = getAxisPos(rsi1.rsi);
            double axisPos1 = getAxisPos(rsi2.rsi);

            canvas.drawLine(x, (float) axisPos, x - (lineWidth + lineSpace), (float) axisPos1, paint);
            x -= lineWidth + lineSpace;
        }
    }


    private int getDisplayedRSICount() {
        return (int) (getWidth() / (lineWidth + lineSpace));
    }

    private int getRSIIndexFromX(int x) {
        int delta = (int) ((getWidth() - x) / (lineWidth + lineSpace));

        RSISet rsiSet = rsiSets.get(0);
        int index = (rsiSet.getValues().size() - 1) - delta;

        return index;
    }

    private void drawText(Canvas canvas) {
        Paint.FontMetrics fm = paintTextAxis.getFontMetrics();
        float textHeight = fm.descent - fm.ascent;
        float baseLine = (textHeight - fm.bottom - fm.top) / 2;

        float rowSpace = getHeight() / gridRows;
        float columnSpace = getWidth() / gridColumns;
        float range = (float) ((axisMax - axisMin) / gridRows);

        for (int i = 0; i <= gridRows; i++) {
            NumberFormat formatter = new DecimalFormat("#0.00");
            String value = formatter.format(axisMax - (i * range));
            canvas.drawText(value, 0, (i * rowSpace) + baseLine, paintTextAxis);
        }

        int dist = (int) (columnSpace * gridColumns);

        int translateX = 0;
        for (int i = 0; i <= gridColumns; i++) {
            int klineIndexFromX = getRSIIndexFromX(getWidth() - translateX - dist);
            if (klineIndexFromX < 0) klineIndexFromX = 0;

            RSISet rsiSet = rsiSets.get(0);
            RSI rsi = rsiSet.getValues().get(klineIndexFromX);
            xDate = rsi.openTime;

            Timestamp ts = new Timestamp(xDate);
            Date date = new Date(ts.getTime());
            String sDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
            float textWidth = paintTextAxis.measureText(sDate);

            if (i == 0) {
                canvas.drawText(sDate, columnSpace * i, getHeight(), paintTextAxis);
            }
            else if (i == gridColumns) {
                canvas.drawText(sDate, columnSpace * i - textWidth, getHeight(), paintTextAxis);
            }
            else {
                canvas.drawText(sDate, columnSpace * i - textWidth / 2, getHeight(), paintTextAxis);
            }

            dist -= columnSpace;
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}