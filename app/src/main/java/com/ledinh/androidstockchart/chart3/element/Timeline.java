package com.ledinh.androidstockchart.chart3.element;

import com.ledinh.androidstockchart.chart.util.TimeUnit;
import com.ledinh.androidstockchart.chart2.view2.Grid;
import com.ledinh.androidstockchart.chart3.event.OnRangeChangeListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Timeline implements OnRangeChangeListener {
    private TimeUnit timeUnit;
    private long initialDate;
    private int firstIndex = 0;
    private int lastIndex = 0;

    public Timeline() {

    }

    public Timeline(TimeUnit timeUnit, long initialDate) {
        this.timeUnit = timeUnit;
        this.initialDate = initialDate;
    }

    @Override
    public void onRangeChange(int firstValueIndex, int lastValueIndex) {
        firstIndex = firstValueIndex;
        lastIndex = lastValueIndex;
    }

    public long calculateDate(int index) {
        switch (timeUnit) {
            case ONE_DAY:
                return ((initialDate / 1000) + (index * 86400)) * 1000;

            case ONE_HOUR:
                return ((initialDate / 1000) + (index * 3600)) * 1000;

            case ONE_MINUTE:
                return ((initialDate / 1000) + (index * 60)) * 1000;
        }

        return 0;
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

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public long getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(long initialDate) {
        this.initialDate = initialDate;
    }

}
