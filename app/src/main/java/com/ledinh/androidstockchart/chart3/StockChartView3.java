package com.ledinh.androidstockchart.chart3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;

import com.ledinh.androidstockchart.R;
import com.ledinh.androidstockchart.chart3.drawing.ChartDrawer;
import com.ledinh.androidstockchart.chart3.drawing.ChartElementDrawer;
import com.ledinh.androidstockchart.chart3.drawing.StockChartDrawer;
import com.ledinh.androidstockchart.chart3.element.Chart;
import com.ledinh.androidstockchart.chart3.element.StockChart;

import java.util.Map;

public class StockChartView3 extends View implements GestureDetector.OnGestureListener{

    private Rect titleArea;
    private Rect chartArea;
    private Rect timelineArea;

    private GestureDetectorCompat gestureDetector;
    private OverScroller overScroller;
    private boolean touch;
    private boolean longPress;

    private int scrollX;
    private int translateX;

    private StockChart stockChart;
    private StockChartDrawer stockChartDrawer;

    public StockChartView3(Context context) {
        super(context);
        init();
    }

    public StockChartView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttrs(attrs);
    }

    public StockChartView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttrs(attrs);
    }

    public StockChartView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {

    }

    private void init() {
        gestureDetector = new GestureDetectorCompat(getContext(), this);
        overScroller = new OverScroller(getContext());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        titleArea = new Rect();
        chartArea = new Rect();
        timelineArea = new Rect();

        chartArea.left = 0;
        chartArea.top = 0;
        chartArea.right = w;
        chartArea.bottom = h - h / 6;

        titleArea.left = 0;
        titleArea.top = 0;
        titleArea.right = w;
        titleArea.bottom = h - h / 6;

        timelineArea.left = 0;
        timelineArea.top = h - h / 6;
        timelineArea.right = w;
        timelineArea.bottom = h;

        float spaceBetweenValue = (float) getWidth() / stockChartDrawer.getScreenDataCount();
        stockChartDrawer.setSpaceBetweenValue(spaceBetweenValue);

        stockChartDrawer.setPosition(new Rect(chartArea.left, chartArea.top, chartArea.right, chartArea.bottom));

        for (Map.Entry<Chart, ChartDrawer> pair: stockChartDrawer.getChartDrawers().entrySet()) {
            ChartDrawer chartDrawer = pair.getValue();
            chartDrawer.setPosition(new Rect(chartArea.left, chartArea.top, chartArea.right, chartArea.bottom));
            chartDrawer.getGridDrawing().setPosition(new Rect(chartArea.left, chartArea.top, chartArea.right, chartArea.bottom));
            chartDrawer.getyAxisLeftDrawing().setPosition(new Rect(chartArea.left, chartArea.top, chartArea.right, chartArea.bottom));
            for (ChartElementDrawer chartElementDrawer: chartDrawer.getChartElementDrawers()) {
                chartElementDrawer.setPosition(new Rect(chartArea.left, chartArea.top, chartArea.right, chartArea.bottom));
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.chart_background));

        stockChartDrawer.draw(canvas, 0);
    }

    public StockChart getStockChart() {
        return stockChart;
    }

    public void setStockChart(StockChart stockChart) {
        this.stockChart = stockChart;
    }

    public StockChartDrawer getStockChartDrawer() {
        return stockChartDrawer;
    }

    public void setStockChartDrawer(StockChartDrawer stockChartDrawer) {
        this.stockChartDrawer = stockChartDrawer;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);

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
        overScroller.fling(scrollX, 0
                , Math.round(velocityX), 0,
                Integer.MIN_VALUE, Integer.MAX_VALUE,
                0, 0);

        return true;
    }

    private void scaleAxis() {
////        int firstValueIndex = (int) (lastValueIndex - getScreenDataCount());
////        int lastValueIndex = getIndexFromX((int) (viewportChartView.getViewWidth() - translateX));
        int firstValueIndex = getIndexFromX((int) Math.abs(translateX));
        int lastValueIndex = firstValueIndex + stockChartDrawer.getScreenDataCount();
        if (lastValueIndex >= 500) {
            lastValueIndex = 499;
        }

        Log.d("StockChartView", "scaleAxis firstValueIndex = " + firstValueIndex);
        Log.d("StockChartView", "scaleAxis lastValueIndex = " + lastValueIndex);

        for (Chart chart : charts) {
            if (chart.isAutoScale()) {
                List<ChartElement> chartComponents = chart.getChartComponents();

                int min = Integer.MAX_VALUE;
                int max = Integer.MIN_VALUE;

                for (ChartElement chartComponent : chartComponents) {
                    Pair<Integer, Integer> range = chartComponent.getRange(firstValueIndex, lastValueIndex);

                    if (range.first < min) {
                        min = range.first;
                    }

                    if (range.second > max) {
                        max = range.second;
                    }
                }

                chart.getFrame().getLeftAxis().setRange(min, max);
                chart.getFrame().getLeftAxis().extendRange(10);
            }
        }
    }

    @Override
    public void computeScroll() {

        if (overScroller.computeScrollOffset()) {
            if (!touch) {
                scrollTo(overScroller.getCurrX(), overScroller.getCurrY());
            } else {
                overScroller.forceFinished(true);
            }
        }

        scaleAxis();
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

        if (scrollX >= getTranslationMaxValue()) {
            scrollX = getTranslationMaxValue();
        }

        if (scrollX <= getTranslationMinValue()) {
            scrollX = getTranslationMinValue();
        }

        translateX = scrollX;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (scrollX >= getTranslationMaxValue()) {
            scrollX = getTranslationMaxValue();
        }

        if (scrollX <= getTranslationMinValue()) {
            scrollX = getTranslationMinValue();
        }

        translateX = scrollX;
    }

    private int getTranslationMaxValue() {
        return 0;
    }

    private int getTranslationMinValue() {
//        int maxIndex = 0;
//        for (Chart chart : charts) {
//            List<ChartElement> chartComponents = chart.getChartComponents();
//            for (ChartElement chartElement: chartComponents) {
//                if (chartElement.getMaxIndex() > maxIndex) {
//                    maxIndex = chartElement.getMaxIndex();
//                }
//            }
//        }
//
//        Log.d("StockChartView", "spaceBetweenValue = " + spaceBetweenValue);
//        Log.d("StockChartView", "maxIndex = " + maxIndex);
//        Log.d("StockChartView", "viewportChartView.getViewWidth = " + viewportChartView.getViewWidth());
//        Log.d("StockChartView", "getTranslationMinValue = " + (- (maxIndex * spaceBetweenValue - viewportChartView.getViewWidth())));
//        Log.d("StockChartView", "maxIndex * spaceBetweenValue = " + (- (maxIndex * spaceBetweenValue)));
//
//        return (int) - (maxIndex * spaceBetweenValue - viewportChartView.getViewWidth() / 2);
        return 10000;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
//        selectedKlineIndex = getKlineIndexFromX((int) (klinesRect.right - translateX - (klinesRect.right - e.getX())));
        longPress = true;
    }
}
