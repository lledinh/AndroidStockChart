package com.ledinh.androidstockchart.chart2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
import com.ledinh.androidstockchart.chart.Kline;
import com.ledinh.androidstockchart.chart.RSISet;
import com.ledinh.androidstockchart.chart.Viewport;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Chart extends View implements
        GestureDetector.OnGestureListener {

    private GestureDetectorCompat mDetector;
    private OverScroller mScroller;

    private Viewport viewport;

    private int scrollX = 0;
    private boolean touch = false;
    private boolean longPress = false;

    private float translateX = 0;
    private int gridColumns = 4;

    private Paint paintGridLine;
    private Paint paintTextAxis;

    private TimeUnit timeUnit;
    private long lastDate;
    private int maxIndex;

    private float spaceBetweenValue;
    private float screenDataCount;

    private List<DrawingElement> drawingElementList;

    public Chart(Context context) {
        super(context);
        init();
    }

    public Chart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttrs(attrs);
    }

    public Chart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttrs(attrs);
    }

    public Chart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {

    }

    private void init() {
        viewport = new Viewport();
        drawingElementList = new ArrayList<>();

        paintGridLine = new Paint();
        paintTextAxis = new Paint();
        paintTextAxis.setAntiAlias(true);

        mDetector = new GestureDetectorCompat(getContext(), this);
        mScroller = new OverScroller(getContext());
    }

    public void addDrawingElement(DrawingElement drawingElement) {
        drawingElement.viewport = viewport;
        drawingElement.setSpaceBetweenValue(spaceBetweenValue);
        drawingElementList.add(drawingElement);
    }

    public void setGridLineColor(int color) {
        paintGridLine.setColor(color);
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

    private void drawGrid(Canvas canvas) {
        float columnSpace = getWidth() / gridColumns;

        for (int i = 0; i <= gridColumns; i++) {
            canvas.drawLine(columnSpace * i, 0, columnSpace * i, viewport.getViewHeight(), paintGridLine);
        }
    }

    private void setViewport(int w,int h)
    {
        viewport.setViewWidth(w);
        viewport.setViewHeight(h);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setViewport(w,h);
        spaceBetweenValue = (float) viewport.getViewWidth() / screenDataCount;

        for (DrawingElement drawingElement : drawingElementList) {
            drawingElement.viewport = viewport;
            drawingElement.setSpaceBetweenValue(spaceBetweenValue);
        }
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

    public int getIndexFromX(int x) {
        int delta = (int) ((getWidth() - x) / spaceBetweenValue);
        int index = maxIndex - delta;

        return index;
    }

    private long calculateDate(int index) {
        switch (timeUnit) {
            case ONE_DAY:
                Log.d("Chart", "calculateDate index = " + index);
                Log.d("Chart", "calculateDate maxIndex = " + maxIndex);
                Log.d("Chart", "calculateDate lastDate = " + lastDate);
                return ((lastDate / 1000) - ((maxIndex - index) * 86400)) * 1000;

            case ONE_HOUR:
                return ((lastDate / 1000) - ((maxIndex - index) * 3600)) * 1000;

            case ONE_MINUTE:
                return ((lastDate / 1000) - ((maxIndex - index) * 60)) * 1000;
        }

        return 0;
    }

    private void drawTimeline(Canvas canvas) {
        float columnSpace = getWidth() / gridColumns;
        int dist = getWidth();

        for (int i = 0; i <= gridColumns; i++) {
            int indexFromX = getIndexFromX((int) (viewport.getViewWidth() - translateX - dist));
            if (indexFromX < 0) indexFromX = 0;

            long dateTimestamp = calculateDate(indexFromX);
            Date date = new Date(dateTimestamp);
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

    @Override
    public void invalidate() {
        super.invalidate();

        for (DrawingElement drawingElement: drawingElementList) {

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.chart_background));

        drawGrid(canvas);

//        canvas.translate(translateX - (1 * spaceBetweenValue), 0);
        for (int i = 0; i < drawingElementList.size(); i++) {
            drawingElementList.get(i).draw(canvas, translateX);
        }

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
                maxIndex = (int) (drawingElement.getChartData().getDataSize() * spaceBetweenValue - viewport.getViewWidth());
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
}
