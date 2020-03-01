package com.ledinh.androidstockchart.chart3.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.ledinh.androidstockchart.chart.YAxis;
import com.ledinh.androidstockchart.chart3.element.Grid;
import com.ledinh.androidstockchart.chart3.element.Timeline;
import com.ledinh.androidstockchart.chart3.event.TimelineFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimelineDrawer extends StockChartElement {
    private Paint paintTextTimeline;
    private Paint paintTextYear;
    private TimelineFormatter timelineFormatter;

    public TimelineDrawer() {
        paintTextTimeline = new Paint();
        paintTextTimeline.setAntiAlias(true);
        paintTextYear = new Paint();
        paintTextYear.setAntiAlias(true);

        timelineFormatter = new TimelineFormatter() {
            @Override
            public String getTimelineText(long dateTimestamp) {
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
        };

        timelineFormatter = new TimelineFormatter() {

            @Override
            public String getTimelineText(long dateTimestamp) {
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
                return null;
            }
        };

    }

    public TimelineDrawer(Rect position) {
        super(position);
    }

    public void draw(Canvas canvas, int gridColumns, Timeline timeline) {
        drawTimeline(canvas, gridColumns, timeline);
    }


    private void drawTimeline(Canvas canvas, int gridColumns, Timeline timeline) {
        float columnSpace = getWidth() / gridColumns;

        Paint.FontMetrics fm = paintTextTimeline.getFontMetrics();
        float textHeight = fm.descent - fm.ascent;

        long timestampMid = 0;

        int indexIterationStep = (timeline.getLastIndex() - timeline.getFirstIndex()) / gridColumns;
        int currentIndex = timeline.getFirstIndex();

        for (int i = 0; i <= gridColumns; i++) {
            long dateTimestamp = timeline.calculateDate(currentIndex);
            String timelineText = timelineFormatter.getTimelineText(dateTimestamp);

            float textWidth = paintTextTimeline.measureText(timelineText);

            if (i == 0) {
                canvas.drawText(timelineText, columnSpace * i, position.top + textHeight, paintTextTimeline);
            }
            else if (i == gridColumns) {
                canvas.drawText(timelineText, columnSpace * i - textWidth, position.top + textHeight, paintTextTimeline);
            }
            else {
                canvas.drawText(timelineText, columnSpace * i - textWidth / 2, position.top + textHeight, paintTextTimeline);
            }

            if (i == gridColumns / 2) {
                timestampMid = dateTimestamp;
            }

            currentIndex += indexIterationStep;
        }

        String year = getYear(timestampMid);
        float textWidth = paintTextTimeline.measureText(year);
        canvas.drawText(year, ((position.right - position.left) / 2f) - (textWidth / 2), position.bottom - ((position.bottom - position.top) / 3), paintTextYear);
    }

    public String getDay(long dateTimestamp) {

    }

    public String getYear(long dateTimestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dateTimestamp);
        int year = cal.get(Calendar.YEAR);

        return String.valueOf(year);
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
