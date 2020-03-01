package com.ledinh.androidstockchart.chart3.drawing.base;

import android.graphics.Paint;
import android.graphics.Rect;

import com.ledinh.androidstockchart.chart3.drawing.StockChartElement;
import com.ledinh.androidstockchart.chart3.event.TimelineFormatter;

public abstract class BaseTimelineDrawer extends StockChartElement {
    protected Paint paintTextTimeline;
    protected Paint paintTextYear;
    protected TimelineFormatter timelineFormatter;

    public BaseTimelineDrawer() {
        paintTextTimeline = new Paint();
        paintTextTimeline.setAntiAlias(true);
        paintTextYear = new Paint();
        paintTextYear.setAntiAlias(true);
    }

    public BaseTimelineDrawer(Rect position) {
        super(position);
    }

    public void setTextSize(int size) {
        paintTextTimeline.setTextSize(size);
    }

    public void setTextColor(int color) {
        paintTextTimeline.setColor(color);
    }

    public void setTextYearSize(int size) {
        paintTextYear.setTextSize(size);
    }

    public void setTextYearColor(int color) {
        paintTextYear.setColor(color);
    }
}
