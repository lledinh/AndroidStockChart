package com.ledinh.androidstockchart.chart.set;

import android.util.Pair;

import com.ledinh.androidstockchart.chart.model.Kline;
import com.ledinh.androidstockchart.chart.util.TimeUnit;
import com.ledinh.androidstockchart.chart.model.RSI;

import java.util.ArrayList;
import java.util.List;

public class RSISet extends ChartSet<RSI> {
    private int rsiValue;

    public RSISet(List<RSI> values, TimeUnit timeUnit, int rsiValue) {
        this.values = values;
        this.timeUnit = timeUnit;
        this.rsiValue = rsiValue;
    }

    @Override
    public Pair<Integer, Integer> getRange(int firstValueIndex, int lastValueIndex) {
        double min = Float.MAX_VALUE;
        double max = Float.MIN_VALUE;

        for (int i = firstValueIndex; i <= lastValueIndex; i++) {
            RSI rsi = values.get(i);

            if (rsi.rsi < min) {
                min = rsi.rsi;
            }

            if (rsi.rsi > max) {
                max = rsi.rsi;
            }
        }

        return new Pair<>((int) min, (int) max);
    }

    public int getRsiValue() {
        return rsiValue;
    }

    public void setRsiValue(int rsiValue) {
        this.rsiValue = rsiValue;
    }
}
