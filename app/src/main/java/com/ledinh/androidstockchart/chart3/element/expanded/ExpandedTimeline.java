package com.ledinh.androidstockchart.chart3.element.expanded;

import com.ledinh.androidstockchart.chart.util.TimeUnit;

public class ExpandedTimeline {
    private TimeUnit timeUnit;
    private long initialDate;

    public ExpandedTimeline() {

    }

    public ExpandedTimeline(TimeUnit timeUnit, long initialDate) {
        this.timeUnit = timeUnit;
        this.initialDate = initialDate;
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
