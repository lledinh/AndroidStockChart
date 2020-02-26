package com.ledinh.androidstockchart.chart3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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
import com.ledinh.androidstockchart.chart.util.TimeUnit;
import com.ledinh.androidstockchart.chart.util.Viewport;
import com.ledinh.androidstockchart.chart.view.element.ChartElement;
import com.ledinh.androidstockchart.chart2.view2.Chart;
import com.ledinh.androidstockchart.chart2.view2.Grid;
import com.ledinh.androidstockchart.chart2.view2.drawing.GridDrawing;
import com.ledinh.androidstockchart.chart3.drawing.StockChartDrawer;
import com.ledinh.androidstockchart.chart3.element.StockChart;

import java.util.ArrayList;
import java.util.List;

public class StockChartView3 extends View {

    private Rect titleArea;
    private Rect chartArea;
    private Rect timelineArea;

    private float spaceBetweenValue;
    private int screenDataCount;

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

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int titleAreaSize = h / 12;

        chartArea = new Rect();
        titleArea = new Rect();
        timelineArea = new Rect();

        stockChartDrawer.setPosition(new Rect(0, 0, w, h));
        for (:
             ) {
            
        }

        titleArea.top = 0;
        titleArea.left = 0;
        titleArea.bottom = titleAreaSize;
        titleArea.right = w;

        int timelineAreaSize = h / 12;
        chartArea.top = titleAreaSize;
        chartArea.left = 0;
        chartArea.bottom = h - timelineAreaSize;
        chartArea.right = w;

        timelineArea.top = h - timelineAreaSize;
        timelineArea.left = 0;
        timelineArea.bottom = h;
        timelineArea.right = w;

        spaceBetweenValue = (float) getWidth() / screenDataCount;

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
}
