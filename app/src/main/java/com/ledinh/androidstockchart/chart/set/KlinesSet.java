package com.ledinh.androidstockchart.chart.set;

import android.util.Pair;

import com.ledinh.androidstockchart.chart.util.TimeUnit;
import com.ledinh.androidstockchart.chart.model.Kline;

import java.util.ArrayList;
import java.util.List;

public class KlinesSet extends ChartSet<Kline> {

    public KlinesSet(List<Kline> values, TimeUnit timeUnit) {
        super(values, timeUnit);
    }

    @Override
    public Pair<Integer, Integer> getRange(int firstValueIndex, int lastValueIndex) {
        double min = Float.MAX_VALUE;
        double max = Float.MIN_VALUE;

        for (int i = firstValueIndex; i <= lastValueIndex; i++) {
            Kline kLine = values.get(i);

            if (kLine.close < min) {
                min = kLine.close;
            }
            if (kLine.open < min) {
                min = kLine.open;
            }
            if (kLine.high < min) {
                min = kLine.high;
            }
            if (kLine.low < min) {
                min = kLine.low;
            }

            if (kLine.close > max) {
                max = kLine.close;
            }
            if (kLine.open > max) {
                max = kLine.open;
            }
            if (kLine.high > max) {
                max = kLine.high;
            }
            if (kLine.low > max) {
                max = kLine.low;
            }
        }

        return new Pair<>((int) min, (int) max);
    }

}
