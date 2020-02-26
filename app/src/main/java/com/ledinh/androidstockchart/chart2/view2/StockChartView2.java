//package com.ledinh.androidstockchart.chart2.view2;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.GestureDetector;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.OverScroller;
//
//import androidx.annotation.Nullable;
//import androidx.core.content.ContextCompat;
//import androidx.core.view.GestureDetectorCompat;
//
//import com.ledinh.androidstockchart.R;
//import com.ledinh.androidstockchart.chart.util.TimeUnit;
//import com.ledinh.androidstockchart.chart.util.Viewport;
//import com.ledinh.androidstockchart.chart.view.element.ChartElement;
//import com.ledinh.androidstockchart.chart2.view2.drawing.GridDrawing;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class StockChartView2 extends View implements
//        GestureDetector.OnGestureListener {
//
//    private GestureDetectorCompat mDetector;
//    private OverScroller mScroller;
//
//    private Viewport viewportChartView;
//    private Rect titleArea;
//    private Rect chartArea;
//    private Rect timelineArea;
//
//    private List<Chart> charts;
//
//    private int scrollX = 0;
//    private boolean touch = false;
//    private boolean longPress = false;
//
//    private float translateX = 0;
//
//    private Grid grid;
//    private GridDrawing gridDrawing;
//
//    private float spaceBetweenValue;
//    private int screenDataCount;
//
//    public StockChartView2(Context context) {
//        super(context);
//        init();
//    }
//
//    public StockChartView2(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        init();
//        initAttrs(attrs);
//    }
//
//    public StockChartView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init();
//        initAttrs(attrs);
//    }
//
//    public StockChartView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init();
//        initAttrs(attrs);
//    }
//
//    private void initAttrs(AttributeSet attrs) {
//
//    }
//
//    private void init() {
//        grid = new Grid();
//        gridDrawing = new GridDrawing();
//
//        charts = new ArrayList<>();
//
//        titleArea = new Rect();
//        chartArea = new Rect();
//        timelineArea = new Rect();
//
//        mDetector = new GestureDetectorCompat(getContext(), this);
//        mScroller = new OverScroller(getContext());
//    }
//
//    public void addChartViewFragment(Chart chart) {
//        charts.add(chart);
//    }
//
//    private void setViewport(int w,int h)
//    {
//        viewportChartView.setViewWidth(w);
//        viewportChartView.setViewHeight(h);
//    }
//
//    private int getChartAreaHeight() {
//        return chartArea.bottom - chartArea.top;
//    }
//
//    private int getTimelineAreaHeight() {
//        return timelineArea.bottom - timelineArea.top;
//    }
//
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        setViewport(w,h);
//
//        int titleAreaSize = h / 12;
//
//        titleArea.top = 0;
//        titleArea.left = 0;
//        titleArea.bottom = titleAreaSize;
//        titleArea.right = w;
//
//        int timelineAreaSize = h / 12;
//        chartArea.top = titleAreaSize;
//        chartArea.left = 0;
//        chartArea.bottom = h - timelineAreaSize;
//        chartArea.right = w;
//
//        timelineArea.top = h - timelineAreaSize;
//        timelineArea.left = 0;
//        timelineArea.bottom = h;
//        timelineArea.right = w;
//
//        spaceBetweenValue = (float) viewportChartView.getViewWidth() / screenDataCount;
//
//        for (Chart chart : charts) {
//            for (ChartElement chartComponent : chart.getChartComponents()) {
//                chartComponent.setSpaceBetweenValue(spaceBetweenValue);
//            }
//        }
//    }
//
////    public int getIndexFromX(int x) {
////        int delta = (int) (x / spaceBetweenValue);
////        int index = delta;
//////        int delta = (int) ((getWidth() - x) / spaceBetweenValue);
//////        int index = maxIndex - delta;
////
////        return index;
////    }
////
////    private long calculateDate(int index) {
////        switch (timeUnit) {
////            case ONE_DAY:
////                return ((lastDate / 1000) - ((maxIndex - index) * 86400)) * 1000;
////
////            case ONE_HOUR:
////                return ((lastDate / 1000) - ((maxIndex - index) * 3600)) * 1000;
////
////            case ONE_MINUTE:
////                return ((lastDate / 1000) - ((maxIndex - index) * 60)) * 1000;
////        }
////
////        return 0;
////    }
////
////
////    private String getYear(long dateTimestamp) {
////        Calendar cal = Calendar.getInstance();
////        cal.setTimeInMillis(dateTimestamp);
////        int year = cal.get(Calendar.YEAR);
////
////        return String.valueOf(year);
////    }
////
////    private String getTimelineText(long dateTimestamp) {
////        Date date = new Date(dateTimestamp);
////        Calendar cal = Calendar.getInstance();
////        cal.setTimeInMillis(dateTimestamp);
////        int day = cal.get(Calendar.DAY_OF_MONTH);
////        int month = cal.get(Calendar.MONTH);
////
////        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
////        SimpleDateFormat formatMonth = new SimpleDateFormat("dd/MM");
////
////        String textTimeline;
////        if (day == 1 && month == 0) {
////            textTimeline = formatYear.format(date);
////        }
////        else {
////            textTimeline = formatMonth.format(date);
////        }
////
////        return textTimeline;
////    }
////
////    private void drawTimeline(Canvas canvas) {
////        float columnSpace = getWidth() / gridColumns;
////        int dist = getWidth();
////
////        Paint.FontMetrics fm = paintTextAxis.getFontMetrics();
////        float textHeight = fm.descent - fm.ascent;
////
////        long timestampMid = 0;
////        for (int i = 0; i <= gridColumns; i++) {
////            int indexFromX = getIndexFromX((int) (viewportChartView.getViewWidth() - translateX - dist));
////            if (indexFromX < 0) indexFromX = 0;
////
////            long dateTimestamp = calculateDate(indexFromX);
////            String timelineText = getTimelineText(dateTimestamp);
////
////            float textWidth = paintTextAxis.measureText(timelineText);
////
////            if (i == 0) {
////                canvas.drawText(timelineText, columnSpace * i, timelineArea.top + textHeight, paintTextAxis);
////            }
////            else if (i == gridColumns) {
////                canvas.drawText(timelineText, columnSpace * i - textWidth, timelineArea.top + textHeight, paintTextAxis);
////            }
////            else {
////                canvas.drawText(timelineText, columnSpace * i - textWidth / 2, timelineArea.top + textHeight, paintTextAxis);
////            }
////
////            if (i == gridColumns / 2) {
////                timestampMid = dateTimestamp;
////            }
////
////            dist -= columnSpace;
////        }
////
////        String year = getYear(timestampMid);
////        float textWidth = paintTextYear.measureText(year);
////        canvas.drawText(year, (viewportChartView.getViewWidth() / 2f) - (textWidth / 2), timelineArea.bottom - ((timelineArea.bottom - timelineArea.top) / 3), paintTextYear);
////
////    }
//
////    private void drawTitle(Canvas canvas) {
////        float textWidth = paintTextTitle.measureText("BTC / USD");
////        canvas.drawText("BTC / USD", (viewportChartView.getViewWidth() / 2f) - (textWidth / 2), (titleArea.bottom - titleArea.top) / 2, paintTextAxis);
////    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.chart_background));
//
//        gridDrawing.draw(canvas, grid);
//
//
//        for (Chart chart : charts) {
//            chart.draw(canvas, 0, translateX);
//        }
////
//////        drawGrid(canvas);
//////
//////        for (int i = 0; i < drawingElementList.size(); i++) {
//////            drawingElementList.get(i).draw(canvas, null, translateX);
//////        }
//////
//////        drawTitle(canvas);
////        drawTimeline(canvas);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        this.mDetector.onTouchEvent(event);
//
//        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN:
//                touch = true;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (longPress) {
//                    invalidate();
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                touch = false;
//                longPress = false;
//                invalidate();
//                break;
//        }
//
//        return true;
//    }
//
//    @Override
//    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        scrollBy(Math.round(distanceX), 0);
//
//        return true;
//    }
//
//    @Override
//    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        mScroller.fling(scrollX, 0
//                , Math.round(velocityX), 0,
//                Integer.MIN_VALUE, Integer.MAX_VALUE,
//                0, 0);
//
//        return true;
//    }
//
//    private void scaleAxis() {
//////        int firstValueIndex = (int) (lastValueIndex - getScreenDataCount());
//////        int lastValueIndex = getIndexFromX((int) (viewportChartView.getViewWidth() - translateX));
////        int firstValueIndex = getIndexFromX((int) Math.abs(translateX));
////        int lastValueIndex = (int) (firstValueIndex + getScreenDataCount());
////        if (lastValueIndex >= 500) {
////            lastValueIndex = 499;
////        }
////
////        Log.d("StockChartView", "scaleAxis firstValueIndex = " + firstValueIndex);
////        Log.d("StockChartView", "scaleAxis lastValueIndex = " + lastValueIndex);
////
////        for (Chart chart : charts) {
////            if (chart.isAutoScale()) {
////                List<ChartElement> chartComponents = chart.getChartComponents();
////
////                int min = Integer.MAX_VALUE;
////                int max = Integer.MIN_VALUE;
////
////                for (ChartElement chartComponent : chartComponents) {
////                    Pair<Integer, Integer> range = chartComponent.getRange(firstValueIndex, lastValueIndex);
////
////                    if (range.first < min) {
////                        min = range.first;
////                    }
////
////                    if (range.second > max) {
////                        max = range.second;
////                    }
////                }
////
////                chart.getFrame().getLeftAxis().setRange(min, max);
////                chart.getFrame().getLeftAxis().extendRange(10);
////            }
////        }
//    }
//
//    @Override
//    public void computeScroll() {
//        Log.d("KLineChartView", "computeScroll");
//
//        if (mScroller.computeScrollOffset()) {
//            if (!touch) {
//                scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
//            } else {
//                mScroller.forceFinished(true);
//            }
//        }
//
//        scaleAxis();
//    }
//
//    @Override
//    public void scrollBy(int x, int y) {
//        scrollTo(scrollX - Math.round(x), 0);
//    }
//
//    @Override
//    public void scrollTo(int x, int y) {
//        int oldX = scrollX;
//        scrollX = x;
//
//        onScrollChanged(scrollX, 0, oldX, 0);
//        invalidate();
//
//        if (scrollX >= getTranslationMaxValue()) {
//            scrollX = getTranslationMaxValue();
//        }
//
//        if (scrollX <= getTranslationMinValue()) {
//            scrollX = getTranslationMinValue();
//        }
//
//        Log.d("ChartView2", "getTranslationMinValue = " + getTranslationMinValue());
//        Log.d("ChartView2", "getTranslationMaxValue = " + getTranslationMaxValue());
//        Log.d("ChartView2", "translateX = " + translateX);
//        translateX = scrollX;
//    }
//
//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        super.onScrollChanged(l, t, oldl, oldt);
//        Log.d("KLineChartView", "before scrollX = " + scrollX);
//
//        if (scrollX >= getTranslationMaxValue()) {
//            scrollX = getTranslationMaxValue();
//        }
//
//        if (scrollX <= getTranslationMinValue()) {
//            scrollX = getTranslationMinValue();
//        }
//
//        Log.d("KLineChartView", "scrollX = " + scrollX);
//        translateX = scrollX;
//        Log.d("KLineChartView", "translateX = " + translateX);
//
////        for (Chart chart : charts) {
////            chart.t
////        }
//    }
//
//    private int getTranslationMaxValue() {
//        return 0;
//    }
//
//    private int getTranslationMinValue() {
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
//    }
//
////
////    private int getScrollRightLimit() {
////        return 0;
////    }
////
////    private int getScrollLeftLimit() {
////        int maxIndex = Integer.MIN_VALUE;
////
////        for(Chart chart : charts) {
////            for (ChartElement chartComponent : chart.getChartComponents()) {
////                if (chartComponent.getMaxIndex() > maxIndex) {
////                    maxIndex = (int) (chartComponent.getChartData().getDataSize() * spaceBetweenValue - viewportChartView.getViewWidth());
////                }
////            }
////        }
////
////        return maxIndex;
////    }
//
//
//    @Override
//    public boolean onDown(MotionEvent e) {
//        Log.d("KLineChartView", "onDown");
//        return true;
//    }
//
//    @Override
//    public void onShowPress(MotionEvent e) {
//        Log.d("KLineChartView", "onShowPress");
//    }
//
//    @Override
//    public boolean onSingleTapUp(MotionEvent e) {
//        Log.d("KLineChartView", "onSingleTapUp");
//
//        return true;
//    }
//
//    @Override
//    public void onLongPress(MotionEvent e) {
//        Log.d("KLineChartView", "onLongPress");
////        selectedKlineIndex = getKlineIndexFromX((int) (klinesRect.right - translateX - (klinesRect.right - e.getX())));
//        longPress = true;
//    }
//
//    public Paint getPaintGridLine() {
//        return paintGridLine;
//    }
//
//    public Paint getPaintTextAxis() {
//        return paintTextAxis;
//    }
//
//    public Paint getPaintViewSeparator() {
//        return paintViewSeparator;
//    }
//
//    public void setTextYearColor(int color) {
//        paintTextYear.setColor(color);
//    }
//
//    public void setTextYearSize(float size) {
//        paintTextYear.setTextSize(size);
//    }
//
//    public void setTextTitleColor(int color) {
//        paintTextTitle.setColor(color);
//    }
//
//    public void setTextTitleSize(float size) {
//        paintTextTitle.setTextSize(size);
//    }
//
//    public void setScreenDataCount(int count) {
//        screenDataCount = count;
//    }
//
//    public void setSpaceBetweenValue(float spaceBetweenValue) {
//        this.spaceBetweenValue = spaceBetweenValue;
//    }
//
//    public void setMaxIndex(int maxIndex) {
//        this.maxIndex = maxIndex;
//    }
//
//    public void setLastDate(long lastDate) {
//        this.lastDate = lastDate;
//    }
//
//    public void setTimeUnit(TimeUnit timeUnit) {
//        this.timeUnit = timeUnit;
//    }
//
//    public void setViewSeparatorColor(int color) {
//        paintViewSeparator.setColor(color);
//    }
//
//    public void setTextAxisColor(int color) {
//        paintTextAxis.setColor(color);
//    }
//
//    public void setTextAxisSize(float size) {
//        paintTextAxis.setTextSize(size);
//    }
//
//    public float getScreenDataCount() {
//        return screenDataCount;
//    }
//
//    public Viewport getViewportChartView() {
//        return viewportChartView;
//    }
//
//    public void setViewportChartView(Viewport viewportChartView) {
//        this.viewportChartView = viewportChartView;
//    }
//
//    public void setGridColumns(int gridColumns) {
//        grid.setGridColumns(gridColumns);
//    }
//
//    public void setGridRows(int gridRows) {
//        grid.setGridRows(gridRows);
//    }
//
//    public void setGridLineColor(int color) {
//        gridDrawing.setGridLineColor(color);
//    }
//
//    public void setGridLineSize(int size) {
//        gridDrawing.setGridLineSize(size);
//    }
//
//
//}
