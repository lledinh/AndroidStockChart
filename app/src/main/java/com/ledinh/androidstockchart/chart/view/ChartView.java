package com.ledinh.androidstockchart.chart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
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
import com.ledinh.androidstockchart.chart.view.element.DrawingArea;
import com.ledinh.androidstockchart.chart.view.element.DrawingElement;
import com.ledinh.androidstockchart.chart.util.TimeUnit;
import com.ledinh.androidstockchart.chart.util.Viewport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ChartView extends View implements
        GestureDetector.OnGestureListener {

    private GestureDetectorCompat mDetector;
    private OverScroller mScroller;

    private Viewport viewportChartView;
    private Rect titleArea;
    private Rect chartArea;
    private Rect timelineArea;

    private DrawingArea drawingAreaTitle;
    private DrawingArea drawingAreaChart;
    private DrawingArea drawingAreaTimeline;

    private int scrollX = 0;
    private boolean touch = false;
    private boolean longPress = false;

    private float translateX = 0;
    private int gridColumns = 4;

    private Paint paintGridLine;
    private Paint paintViewSeparator;
    private Paint paintTextAxis;
    private Paint paintTextYear;
    private Paint paintTextTitle;

    private TimeUnit timeUnit;
    private long lastDate;
    private int maxIndex;

    private float spaceBetweenValue;
    private int screenDataCount;

    private List<DrawingElement> drawingElementList;
    private Map<Integer, DrawingElement> positionDrawingElement;

    public ChartView(Context context) {
        super(context);
        init();
    }

    public ChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttrs(attrs);
    }

    public ChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttrs(attrs);
    }

    public ChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {

    }

    private void init() {
        viewportChartView = new Viewport();
        drawingElementList = new ArrayList<>();

        paintGridLine = new Paint();
        paintTextAxis = new Paint();
        paintTextAxis.setAntiAlias(true);
        paintTextTitle = new Paint();
        paintTextTitle.setAntiAlias(true);
        paintTextYear = new Paint();
        paintTextYear.setAntiAlias(true);
        paintTextYear.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        paintViewSeparator = new Paint();

        titleArea = new Rect();
        chartArea = new Rect();
        timelineArea = new Rect();

        mDetector = new GestureDetectorCompat(getContext(), this);
        mScroller = new OverScroller(getContext());
    }

    public void addDrawingElement(DrawingElement drawingElement) {

        int weightSum = 0;
        for (int i = 0; i < drawingElementList.size(); i++) {
            DrawingElement drawingElement1 = drawingElementList.get(i);
            weightSum += drawingElement1.getWeight();
        }

        weightSum += drawingElement.getWeight();
        int oneUnit = getChartAreaHeight() / weightSum;

        if (drawingElementList != null) {
            int drawingElementHeight = oneUnit * drawingElement.getWeight();
            int drawingElementWidth = viewportChartView.getViewWidth();

            Viewport drawingElementViewport = new Viewport(drawingElementWidth, drawingElementHeight);
            drawingElement.setViewport(drawingElementViewport);
            drawingElement.setSpaceBetweenValue(spaceBetweenValue);
            drawingElementList.add(drawingElement);

            // Maj des autres vues
            int height = 0;
            for (int i = 0; i < drawingElementList.size(); i++) {
                DrawingElement drawingElement1 = drawingElementList.get(i);
                drawingElementHeight = oneUnit * drawingElement1.getWeight();
                drawingElement1.setViewportPosition(0, drawingElementHeight, viewportChartView.getViewWidth(), height + drawingElementHeight);
                height += drawingElementHeight;
            }
        }
    }

    private void drawGrid(Canvas canvas) {
        float columnSpace = getWidth() / gridColumns;

        for (int i = 0; i <= gridColumns; i++) {
            canvas.drawLine(columnSpace * i, 0, columnSpace * i, getChartAreaHeight(), paintGridLine);
        }
    }

    private void setViewport(int w,int h)
    {
        viewportChartView.setViewWidth(w);
        viewportChartView.setViewHeight(h);
    }

    private int getChartAreaHeight() {
        return chartArea.bottom - chartArea.top;
    }

    private int getTimelineAreaHeight() {
        return timelineArea.bottom - timelineArea.top;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setViewport(w,h);

        int titleAreaSize = h / 12;

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

        spaceBetweenValue = (float) viewportChartView.getViewWidth() / screenDataCount;

        int weightSum = 0;
        for (int i = 0; i < drawingElementList.size(); i++) {
            DrawingElement drawingElement1 = drawingElementList.get(i);
            weightSum += drawingElement1.getWeight();
        }

        int oneUnit = getChartAreaHeight() / weightSum;

        int height = 0;
        for (int i = 0; i < drawingElementList.size(); i++) {
            DrawingElement drawingElement1 = drawingElementList.get(i);
            int drawingElementHeight = oneUnit * drawingElement1.getWeight();
            drawingElement1.getViewport().setViewWidth(getChartAreaHeight());
            drawingElement1.getViewport().setViewHeight(getChartAreaHeight());
            drawingElement1.setViewportPosition(0, height, viewportChartView.getViewWidth(), height + drawingElementHeight);
            height += drawingElementHeight;

            drawingElement1.setSpaceBetweenValue(spaceBetweenValue);
        }
    }

    public int getIndexFromX(int x) {
        int delta = (int) ((getWidth() - x) / spaceBetweenValue);
        int index = maxIndex - delta;

        return index;
    }

    private long calculateDate(int index) {
        switch (timeUnit) {
            case ONE_DAY:
                return ((lastDate / 1000) - ((maxIndex - index) * 86400)) * 1000;

            case ONE_HOUR:
                return ((lastDate / 1000) - ((maxIndex - index) * 3600)) * 1000;

            case ONE_MINUTE:
                return ((lastDate / 1000) - ((maxIndex - index) * 60)) * 1000;
        }

        return 0;
    }


    private String getYear(long dateTimestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dateTimestamp);
        int year = cal.get(Calendar.YEAR);

        return String.valueOf(year);
    }

    private String getTimelineText(long dateTimestamp) {
        Date date = new Date(dateTimestamp);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dateTimestamp);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);

        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatMonth = new SimpleDateFormat("dd/MM");

        String textTimeline;
        if (day == 1 && month == 0) {
            textTimeline = formatYear.format(date);
        }
        else {
            textTimeline = formatMonth.format(date);
        }

        return textTimeline;
    }

    private void drawTimeline(Canvas canvas) {
        float columnSpace = getWidth() / gridColumns;
        int dist = getWidth();

        Paint.FontMetrics fm = paintTextAxis.getFontMetrics();
        float textHeight = fm.descent - fm.ascent;

        long timestampMid = 0;
        for (int i = 0; i <= gridColumns; i++) {
            int indexFromX = getIndexFromX((int) (viewportChartView.getViewWidth() - translateX - dist));
            if (indexFromX < 0) indexFromX = 0;

            long dateTimestamp = calculateDate(indexFromX);
            String timelineText = getTimelineText(dateTimestamp);

            float textWidth = paintTextAxis.measureText(timelineText);

            if (i == 0) {
                canvas.drawText(timelineText, columnSpace * i, timelineArea.top + textHeight, paintTextAxis);
            }
            else if (i == gridColumns) {
                canvas.drawText(timelineText, columnSpace * i - textWidth, timelineArea.top + textHeight, paintTextAxis);
            }
            else {
                canvas.drawText(timelineText, columnSpace * i - textWidth / 2, timelineArea.top + textHeight, paintTextAxis);
            }

            if (i == gridColumns / 2) {
                timestampMid = dateTimestamp;
            }

            dist -= columnSpace;
        }

        String year = getYear(timestampMid);
        float textWidth = paintTextYear.measureText(year);
        canvas.drawText(year, (viewportChartView.getViewWidth() / 2f) - (textWidth / 2), timelineArea.bottom - ((timelineArea.bottom - timelineArea.top) / 3), paintTextYear);

    }

    private void drawTitle(Canvas canvas) {
        float textWidth = paintTextTitle.measureText("BTC / USD");
        canvas.drawText("BTC / USD", (viewportChartView.getViewWidth() / 2f) - (textWidth / 2), (titleArea.bottom - titleArea.top) / 2, paintTextAxis);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.chart_background));

        drawGrid(canvas);

        for (int i = 0; i < drawingElementList.size(); i++) {
            drawingElementList.get(i).draw(canvas, null, translateX);
        }

        drawTitle(canvas);
        drawTimeline(canvas);
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

        for (DrawingElement drawingElement: drawingElementList) {
            if (drawingElement.isAutoScale()) {
                int lastValueIndex = getIndexFromX((int) (viewportChartView.getViewWidth() - translateX));
                int firstValueIndex = (int) (lastValueIndex - getScreenDataCount());
                drawingElement.updateAxisRangeFromIndex(firstValueIndex, lastValueIndex);
                drawingElement.getyAxis().extendRange(20);
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
        return 0;
    }

    private int getScrollLeftLimit() {
        int maxIndex = Integer.MIN_VALUE;
        for (DrawingElement drawingElement: drawingElementList) {
            if (drawingElement.getMaxIndex() > maxIndex) {
                maxIndex = (int) (drawingElement.getChartData().getDataSize() * spaceBetweenValue - viewportChartView.getViewWidth());
            }
        }

        return maxIndex;
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

    public Paint getPaintGridLine() {
        return paintGridLine;
    }

    public Paint getPaintTextAxis() {
        return paintTextAxis;
    }

    public Paint getPaintViewSeparator() {
        return paintViewSeparator;
    }

    public void setTextYearColor(int color) {
        paintTextYear.setColor(color);
    }

    public void setTextYearSize(float size) {
        paintTextYear.setTextSize(size);
    }

    public void setTextTitleColor(int color) {
        paintTextTitle.setColor(color);
    }

    public void setTextTitleSize(float size) {
        paintTextTitle.setTextSize(size);
    }

    public void setScreenDataCount(int count) {
        screenDataCount = count;
    }

    public void setSpaceBetweenValue(float spaceBetweenValue) {
        this.spaceBetweenValue = spaceBetweenValue;
    }

    public void setMaxIndex(int maxIndex) {
        this.maxIndex = maxIndex;
    }

    public void setLastDate(long lastDate) {
        this.lastDate = lastDate;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public void setGridLineColor(int color) {
        paintGridLine.setColor(color);
    }

    public void setViewSeparatorColor(int color) {
        paintViewSeparator.setColor(color);
    }

    public void setTextAxisColor(int color) {
        paintTextAxis.setColor(color);
    }

    public void setTextAxisSize(float size) {
        paintTextAxis.setTextSize(size);
    }

    public float getScreenDataCount() {
        return screenDataCount;
    }


}
