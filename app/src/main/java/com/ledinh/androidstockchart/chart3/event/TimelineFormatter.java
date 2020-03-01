package com.ledinh.androidstockchart.chart3.event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public interface TimelineFormatter {
    String getTimelineText(long dateTimestamp);
}
