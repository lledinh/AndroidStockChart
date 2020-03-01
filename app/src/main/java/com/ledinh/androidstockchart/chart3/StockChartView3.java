package com.ledinh.androidstockchart.chart3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;

import com.ledinh.androidstockchart.R;
import com.ledinh.androidstockchart.chart3.event.OnScrollEndListener;
import com.ledinh.androidstockchart.chart3.event.OnScrollMinListener;
import com.ledinh.androidstockchart.chart3.drawing.ChartDrawer;
import com.ledinh.androidstockchart.chart3.drawing.StockChartDrawer;
import com.ledinh.androidstockchart.chart3.element.Chart;
import com.ledinh.androidstockchart.chart3.element.ChartElement;
import com.ledinh.androidstockchart.chart3.element.StockChart;

import java.util.ArrayList;
import java.util.List;

public class StockChartView3 extends View implements GestureDetector.OnGestureListener {

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

    private List<OnScrollMinListener> onScrollMinListeners;
    private List<OnScrollEndListener> onScrollEndListeners;

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
        onScrollEndListeners = new ArrayList<>();
        onScrollMinListeners = new ArrayList<>();

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

        timelineArea.left = 0;
        timelineArea.top = h - h / 6;
        timelineArea.right = w;
        timelineArea.bottom = h;

        stockChartDrawer.getTimelineDrawer().setPosition(new Rect(timelineArea.left, timelineArea.top, timelineArea.right, timelineArea.bottom));

        float spaceBetweenValue = (float) getWidth() / stockChartDrawer.getScreenDataCount();
        stockChartDrawer.setSpaceBetweenValue(spaceBetweenValue);
        stockChartDrawer.setPosition(new Rect(chartArea.left, chartArea.top, chartArea.right, chartArea.bottom));

        int weightSum = stockChartDrawer.getWeightSum();
        int oneUnit = (chartArea.bottom - chartArea.top) / weightSum;

        int left = chartArea.left;
        int right = chartArea.right;
        int top = chartArea.top;
        int bottom = chartArea.top;

        for(Chart chart: stockChartDrawer.getChartDrawersOrders()) {
            ChartDrawer chartDrawer = stockChartDrawer.getChartDrawers().get(chart);

            if (chartDrawer != null) {
                int weight = chartDrawer.getWeight();
                bottom += weight * oneUnit;

                Rect position = new Rect(left, top, right, bottom);
                chartDrawer.setPosition(position);
                chartDrawer.getGridDrawing().setPosition(position);
                chartDrawer.getyAxisLeftDrawing().setPosition(position);

                top += weight * oneUnit;
            }
        }

//        for(Map.Entry<Chart, ChartDrawer> entry : stockChartDrawer.getChartDrawers().entrySet()) {
//            Chart chart = entry.getKey();
//            ChartDrawer chartDrawer = entry.getValue();
//
//            int weight = chartDrawer.getWeight();
//            bottom += weight * oneUnit;
//
//            Rect position = new Rect(left, top, right, bottom);
//            chartDrawer.setPosition(position);
//            chartDrawer.getGridDrawing().setPosition(position);
//            chartDrawer.getyAxisLeftDrawing().setPosition(position);
//
//            top += weight * oneUnit;
//        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.chart_background));

        stockChartDrawer.draw(canvas, stockChart.getTimeline(), translateX);
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

    public int getIndexFromX(int x) {
        return (int) (x / stockChartDrawer.getSpaceBetweenValue());
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

        int firstValueIndex = getIndexFromX(Math.abs(translateX));
        int lastValueIndex = firstValueIndex + stockChartDrawer.getScreenDataCount();

        for (Chart chart: stockChart.getCharts()) {
            chart.onRangeChange(firstValueIndex, lastValueIndex);
        }

        stockChart.getTimeline().onRangeChange(firstValueIndex, lastValueIndex);
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

        if (scrollX >= getTranslationMaxValue()) {
            scrollX = getTranslationMaxValue();
        }

        if (scrollX <= getTranslationMinValue()) {
            scrollX = getTranslationMinValue();
        }

        translateX = scrollX;

        invalidate();
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
        int maxIndex = 0;
        for (Chart chart : stockChart.getCharts()) {
            List<ChartElement> chartComponents = chart.getChartElements();
            for (ChartElement chartElement: chartComponents) {
                if (chartElement.getData().getDataSize() > maxIndex) {
                    maxIndex = chartElement.getData().getDataSize();
                }
            }
        }

        return (int) - (maxIndex * stockChartDrawer.getSpaceBetweenValue() - getWidth() / 2);
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
