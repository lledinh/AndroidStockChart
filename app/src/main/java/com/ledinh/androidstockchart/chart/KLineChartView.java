package com.ledinh.androidstockchart.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.OverScroller;
import android.widget.RelativeLayout;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;

import com.ledinh.androidstockchart.R;
import com.ledinh.androidstockchart.chart2.KlinesSet;
import com.ledinh.androidstockchart.chart2.TimeUnit;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KLineChartView extends RelativeLayout implements
        GestureDetector.OnGestureListener {

    private GestureDetectorCompat mDetector;
    private OverScroller mScroller;

    private Paint paintGridLine;
    private Paint paintTextAxis;
    private Paint paintSelectedKline;

    private KlinesSet klinesSet;
    private int gridRows = 5;
    private int gridColumns = 4;
    private int selectedKlineIndex;

    private long xDate = 0;

    private float translateX = 0;

    private int scrollX = 0;
    private boolean touch = false;
    private boolean longPress = false;

    private Viewport viewport;
    private Axis yAxis;
    private KlineSetDrawing klineSetDrawing;

    public KLineChartView(Context context) {
        super(context);
        init();
    }

    public KLineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttrs(attrs);
    }

    public KLineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttrs(attrs);
    }

    public KLineChartView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.KLineChartView);
        try {
            paintGridLine.setColor(array.getColor(R.styleable.KLineChartView_kc_grid_line_color, getColor(R.color.chart_grid_line)));
            paintGridLine.setStrokeWidth(array.getDimension(R.styleable.KLineChartView_kc_grid_line_width, (int) getDimension(R.dimen.chart_grid_line_width)));
            paintSelectedKline.setColor(array.getColor(R.styleable.KLineChartView_kc_selected_line_color, getColor(R.color.chart_selector)));
            paintSelectedKline.setStrokeWidth(array.getDimension(R.styleable.KLineChartView_kc_selected_line_color, (int) getDimension(R.dimen.chart_selector_line_width)));

            setBackgroundColor(array.getColor(R.styleable.KLineChartView_kc_background_color,getColor(R.color.chart_background)));

            klineSetDrawing.setDecreasingColor(array.getColor(R.styleable.KLineChartView_kc_kline_decreasing_color,getColor(R.color.chart_red)));
            klineSetDrawing.setIncreasingColor(array.getColor(R.styleable.KLineChartView_kc_kline_increasing_color,getColor(R.color.chart_green)));

            klineSetDrawing.setKlineWidth(array.getDimension(R.styleable.KLineChartView_kc_kline_width, (int) getDimension(R.dimen.chart_kline_width)));
            klineSetDrawing.setKlineInnerLineWidth(array.getDimension(R.styleable.KLineChartView_kc_kline_inner_width, getDimension(R.dimen.chart_kline_inner_width)));
            klineSetDrawing.setKlineSpace(klineSetDrawing.getKlineWidth() / 2);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            array.recycle();
        }
    }

    private float getDimension(@DimenRes int resId) {
        return getResources().getDimension(resId);
    }

    private int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(getContext(),resId);
    }

    public KlinesSet getKlinesSet() {
        return klinesSet;
    }

    public void setKlinesSet(KlinesSet klinesSet) {
        this.klineSetDrawing.setKlinesSet(klinesSet);
        this.klinesSet = klinesSet;
        List<Kline> klines = klinesSet.getValues();
        setAxisRange(klines.size() - 1);
        yAxis.extendRange(20);
        translateX = 0;
        invalidate();
    }

    public void init() {
        paintGridLine = new Paint();
        paintTextAxis = new Paint();
        paintSelectedKline = new Paint();

        paintTextAxis.setColor(Color.rgb(0xB1, 0xB2, 0xB6));
        paintTextAxis.setAntiAlias(true);
        paintTextAxis.setTextSize(getResources().getDimension(R.dimen.chart_text_size));

        mDetector = new GestureDetectorCompat(getContext(), this);
        mScroller = new OverScroller(getContext());

        klinesSet = new KlinesSet(new ArrayList<Kline>(), TimeUnit.ONE_DAY);
        yAxis = new Axis(Float.MIN_VALUE, Float.MAX_VALUE);
        viewport = new Viewport();

        klineSetDrawing = new KlineSetDrawing(klinesSet, viewport, yAxis, 0, 0, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                touch = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (longPress) {
                    selectedKlineIndex = getKlineIndexFromX((int) (viewport.getViewWidth() - translateX - (viewport.getViewWidth() - event.getX())));
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                touch = false;
                longPress = false;
                invalidate();
                break;
        }

        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setViewport(w,h);
    }

    private void setViewport(int w,int h)
    {
        viewport.setViewWidth(w);
        viewport.setViewHeight(h);
    }

    public void setAxisRange(int lastIndex) {
        int getDisplayedKlineCount = getDisplayedKlineCount();
        Kline kline = klinesSet.getValues().get(lastIndex);
        xDate = kline.openTime;

        double axisMin = Float.MAX_VALUE;
        double axisMax = Float.MIN_VALUE;

        for (int i = lastIndex; i > lastIndex - getDisplayedKlineCount; i--) {
            Kline kLine = klinesSet.getValues().get(i);

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

        Log.d("KLineChartView", "updateAxisRangeFromIndex axisMax = " + axisMax + " axisMin = " + axisMin);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.chart_background));

        drawGrid(canvas);

        if (klinesSet.getValues() != null) {
            canvas.save();

            Log.d("KLineChartView", "translateX = " + translateX);
            canvas.translate(translateX - 1 * (klineSetDrawing.getKlineWidth() + klineSetDrawing.getKlineWidth() / 2f), 0);

            klineSetDrawing.drawKlines(canvas, viewport.getViewWidth());
//            drawKlines(canvas);
            canvas.restore();
        }

        canvas.save();
        canvas.translate(translateX - 1 * (klineSetDrawing.getKlineWidth() + klineSetDrawing.getKlineWidth() / 2f), 0);
        drawSelectedKline(canvas);
        canvas.restore();
        drawText(canvas);
    }

    private void drawGrid(Canvas canvas) {
        float rowSpace = viewport.getViewHeight() / gridRows;
        float columnSpace = viewport.getViewWidth() / gridColumns;

        for (int i = 0; i <= gridRows; i++) {
            canvas.drawLine(0, rowSpace * i, viewport.getViewWidth(), rowSpace * i, paintGridLine);
        }

        for (int i = 0; i <= gridColumns; i++) {
            canvas.drawLine(columnSpace * i, 0, columnSpace * i, viewport.getViewHeight(), paintGridLine);
        }
    }

    private int getDisplayedKlineCount() {
        return (int) (viewport.getViewWidth() / (klineSetDrawing.getKlineWidth() + klineSetDrawing.getKlineSpace()));
    }

    private int getKlineIndexFromX(int x) {
        int delta = (int) ((viewport.getViewWidth() - x) / (klineSetDrawing.getKlineWidth() + klineSetDrawing.getKlineSpace()));
        int index = (klinesSet.getValues().size() - 1) - delta;

        return index;
    }

    private void drawText(Canvas canvas) {
        Paint.FontMetrics fm = paintTextAxis.getFontMetrics();
        float textHeight = fm.descent - fm.ascent;
        float baseLine = (textHeight - fm.bottom - fm.top) / 2;

        float rowSpace = viewport.getViewHeight() / gridRows;
        float columnSpace = viewport.getViewWidth() / gridColumns;
        float range = (float) ((yAxis.getAxisMax() - yAxis.getAxisMin()) / gridRows);

        for (int i = 0; i <= gridRows; i++) {
            NumberFormat formatter = new DecimalFormat("#0.00");
            String value = formatter.format(yAxis.getAxisMax() - (i * range));
            canvas.drawText(value, 0, viewport.getViewingPosition().top + (i * rowSpace) + baseLine, paintTextAxis);
        }

        int dist = (int) (columnSpace * gridColumns);

        for (int i = 0; i <= gridColumns; i++) {
            int klineIndexFromX = getKlineIndexFromX((int) (viewport.getViewWidth() - translateX - dist));
            if (klineIndexFromX < 0) klineIndexFromX = 0;

            Kline kline = klinesSet.getValues().get(klineIndexFromX);
            xDate = kline.openTime;

            Timestamp ts = new Timestamp(xDate);
            Date date = new Date(ts.getTime());
            String sDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
            float textWidth = paintTextAxis.measureText(sDate);

            if (i == 0) {
                canvas.drawText(sDate, columnSpace * i, viewport.getViewHeight(), paintTextAxis);
            }
            else if (i == gridColumns) {
                canvas.drawText(sDate, columnSpace * i - textWidth, viewport.getViewHeight(), paintTextAxis);
            }
            else {
                canvas.drawText(sDate, columnSpace * i - textWidth / 2, viewport.getViewHeight(), paintTextAxis);
            }

            dist -= columnSpace;
        }

    }

//
//    // Return the y axis position of a value
//    // The axis values range from 0 to viewHeight
//    // So if value = 400 axisMax = 600 axisMin = 200
//    // then value is located in the middle of the axis,
//    // so it will returns 0.5f * viewHeight;
//    private double getAxisPos(double value) {
//        double range = axisMax - axisMin;
//        value -= axisMin;
//
//        // Ratio between 0 and 1
//        double ratio = value / range;
//
//        // Position on the screen
//        double pos = (1 - ratio) * (klinesRect.bottom - klinesRect.top);
//
//        return pos;
//    }

    private void drawSelectedKline(Canvas canvas) {
        float x = viewport.getViewWidth();
        for (int i = klinesSet.getValues().size() - 1; i >= 0 ; i--) {
            if (i == selectedKlineIndex) {
                Kline kLine = klinesSet.getValues().get(i);
                double axisPosOpen = yAxis.getInvertedAxisPos(kLine.open);

                canvas.drawLine(-translateX, (float) axisPosOpen, viewport.getViewWidth() + 10, (float) axisPosOpen, paintSelectedKline);
                canvas.drawLine(x + klineSetDrawing.getKlineWidth() / 2 - klineSetDrawing.getKlineInnerLineWidth() / 2, 0f, x + klineSetDrawing.getKlineWidth() / 2 - klineSetDrawing.getKlineInnerLineWidth() / 2, viewport.getViewHeight(), paintSelectedKline);
            }
            x -= klineSetDrawing.getKlineWidth() + klineSetDrawing.getKlineSpace();
        }

    }

//    private void drawKlines(Canvas canvas) {
//        float x = klinesRect.right;
//        for (int i = klinesSet.getValues().size() - 1; i >= 0 ; i--) {
//            Kline kLine = klinesSet.getValues().get(i);
//            Paint p;
//            if (kLine.close > kLine.open) {
//                p = paintIncreasing;
//            }
//            else {
//                p = paintDecreasing;
//            }
//
//            double axisPosOpen = getAxisPos(kLine.open);
//            double axisPosClose = getAxisPos(kLine.close);
//            double axisPosHigh = getAxisPos(kLine.high);
//            double axisPosLow = getAxisPos(kLine.low);
//
//            drawKline(canvas, x, axisPosOpen, axisPosClose, axisPosHigh, axisPosLow, p);
//            x -= klineWidth + klineSpace;
//        }
//    }
//
//    private void drawKline(Canvas canvas, float x, double axisPosOpen, double axisPosClose, double axisPosHigh, double axisPosLow, Paint p) {
//        canvas.drawRect(x, (float) axisPosOpen, x + klineWidth, (float) axisPosClose, p);
//        canvas.drawRect(x + (klineWidth / 2f) - klineInnerLineWidth / 2, (float) axisPosHigh, x + (klineWidth / 2f) + klineInnerLineWidth / 2, (float) axisPosLow, p);
//    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        scrollBy(Math.round(distanceX), 0);

        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mScroller.fling(scrollX, 0
                , Math.round(velocityX), 0,
                Integer.MIN_VALUE, Integer.MAX_VALUE,
                0, 0);

        return true;
    }

    @Override
    public void computeScroll() {
        Log.d("KLineChartView", "computeScroll");

        if (mScroller.computeScrollOffset()) {
            if (!touch) {
                scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            } else {
                mScroller.forceFinished(true);
            }
        }

        if (klinesSet.getValues() != null) {
            int klineIndexFromX = getKlineIndexFromX((int) (viewport.getViewWidth() - translateX));

            setAxisRange(klineIndexFromX);
            yAxis.extendRange(20);
        }
    }

    @Override
    public void scrollBy(int x, int y) {
        scrollTo(scrollX - Math.round(x), 0);
    }

    @Override
    public void scrollTo(int x, int y) {
        int oldX = scrollX;
        scrollX = x;

        onScrollChanged(scrollX, 0, oldX, 0);
        invalidate();
    }

    private int getScrollRightLimit() {
        return 0;
    }

    private int getScrollLeftLimit() {
        return (int) (klinesSet.getValues().size() * klineSetDrawing.getKlineWidth() + klinesSet.getValues().size() * (klineSetDrawing.getKlineWidth() / 2f)) - viewport.getViewWidth();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollX < getScrollRightLimit()) {
            scrollX = 0;
        }

        if (scrollX > getScrollLeftLimit()) {
            scrollX = getScrollLeftLimit();
        }

        translateX = scrollX;

        int klineIndexFromX = getKlineIndexFromX((int) (viewport.getViewWidth() - translateX));
        Kline kline = klinesSet.getValues().get(klineIndexFromX);
        xDate = kline.openTime;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d("KLineChartView", "onDown");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d("KLineChartView", "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d("KLineChartView", "onSingleTapUp");

        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d("KLineChartView", "onLongPress");
        selectedKlineIndex = getKlineIndexFromX((int) (viewport.getViewWidth() - translateX - (viewport.getViewWidth() - e.getX())));
        longPress = true;
    }

}