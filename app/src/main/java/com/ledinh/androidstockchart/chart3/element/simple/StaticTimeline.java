package com.ledinh.androidstockchart.chart3.element.simple;

import com.ledinh.androidstockchart.chart.util.TimeUnit;
import com.ledinh.androidstockchart.chart2.view2.Grid;
import com.ledinh.androidstockchart.chart3.element.base.BaseTimeline;
import com.ledinh.androidstockchart.chart3.event.OnRangeChangeListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StaticTimeline extends BaseTimeline implements OnRangeChangeListener {
    private int firstIndex = 0;
    private int lastIndex = 0;
    private int columns = 5;

    public StaticTimeline() {
        super();
    }

    public StaticTimeline(TimeUnit timeUnit, long initialDate) {
        super(timeUnit, initialDate);
    }

    @Override
    public void onRangeChange(int firstValueIndex, int lastValueIndex) {
        firstIndex = firstValueIndex;
        lastIndex = lastValueIndex;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public void setFirstIndex(int firstIndex) {
        this.firstIndex = firstIndex;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }
}
