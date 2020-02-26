package com.ledinh.androidstockchart.chart2.view2;

import com.ledinh.androidstockchart.chart.util.TimeUnit;

public class Timeline {
    private Grid grid;
    private TimeUnit timeUnit;

    public void update() {

    }

//
//    @Override
//    public void draw(Canvas canvas, float translateX) {
//
//        float columnSpace = getWidth() / gridColumns;
//        int dist = getWidth();
//
//        Paint.FontMetrics fm = paintTextAxis.getFontMetrics();
//        float textHeight = fm.descent - fm.ascent;
//
//        long timestampMid = 0;
//        for (int i = 0; i <= gridColumns; i++) {
//            int indexFromX = getIndexFromX((int) ( - translateX - dist));
//            if (indexFromX < 0) indexFromX = 0;
//
//            long dateTimestamp = calculateDate(indexFromX);
//            String timelineText = getTimelineText(dateTimestamp);
//
//            float textWidth = paintTextAxis.measureText(timelineText);
//
//            if (i == 0) {
//                canvas.drawText(timelineText, columnSpace * i, timelineArea.top + textHeight, paintTextAxis);
//            }
//            else if (i == gridColumns) {
//                canvas.drawText(timelineText, columnSpace * i - textWidth, timelineArea.top + textHeight, paintTextAxis);
//            }
//            else {
//                canvas.drawText(timelineText, columnSpace * i - textWidth / 2, timelineArea.top + textHeight, paintTextAxis);
//            }
//
//            if (i == gridColumns / 2) {
//                timestampMid = dateTimestamp;
//            }
//
//            dist -= columnSpace;
//        }
//
//        String year = getYear(timestampMid);
//        float textWidth = paintTextYear.measureText(year);
//        canvas.drawText(year, (viewportChartView.getViewWidth() / 2f) - (textWidth / 2), timelineArea.bottom - ((timelineArea.bottom - timelineArea.top) / 3), paintTextYear);
//
//    }
//
//    public int getIndexFromX(int x) {
//        int delta = (int) (x / spaceBetweenValue);
//        int index = delta;
////        int delta = (int) ((getWidth() - x) / spaceBetweenValue);
////        int index = maxIndex - delta;
//
//        return index;
//    }
//
//    private long calculateDate(int index) {
//        switch (timeUnit) {
//            case ONE_DAY:
//                return ((lastDate / 1000) - ((maxIndex - index) * 86400)) * 1000;
//
//            case ONE_HOUR:
//                return ((lastDate / 1000) - ((maxIndex - index) * 3600)) * 1000;
//
//            case ONE_MINUTE:
//                return ((lastDate / 1000) - ((maxIndex - index) * 60)) * 1000;
//        }
//
//        return 0;
//    }
//
//
//    private String getTimelineText(long dateTimestamp) {
//        Date date = new Date(dateTimestamp);
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(dateTimestamp);
//        int day = cal.get(Calendar.DAY_OF_MONTH);
//        int month = cal.get(Calendar.MONTH);
//
//        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
//        SimpleDateFormat formatMonth = new SimpleDateFormat("dd/MM");
//
//        String textTimeline;
//        if (day == 1 && month == 0) {
//            textTimeline = formatYear.format(date);
//        }
//        else {
//            textTimeline = formatMonth.format(date);
//        }
//
//        return textTimeline;
//    }
//
//    private void drawTimeline(Canvas canvas) {
//    }

}
