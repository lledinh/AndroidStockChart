package com.ledinh.androidstockchart.chart;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;

public class StockChart extends View implements
        GestureDetector.OnGestureListener {

    private GestureDetectorCompat mDetector;
    private OverScroller mScroller;

    private int scrollX = 0;
    private boolean touch = false;
    private boolean longPress = false;

    private float translateX = 0;

    public StockChart(Context context) {
        super(context);
    }

    public StockChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StockChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public StockChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
        return Integer.MIN_VALUE;
    }

    private int getScrollLeftLimit() {
        return Integer.MAX_VALUE;
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

//        int klineIndexFromX = getKlineIndexFromX((int) (klinesRect.right - translateX));
//        Kline kline = klinesSet.getValues().get(klineIndexFromX);
//        xDate = kline.openTime;
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
//        selectedKlineIndex = getKlineIndexFromX((int) (klinesRect.right - translateX - (klinesRect.right - e.getX())));
        longPress = true;
    }
}
