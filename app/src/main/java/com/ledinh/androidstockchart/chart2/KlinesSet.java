package com.ledinh.androidstockchart.chart2;

import com.ledinh.androidstockchart.chart.Kline;
import com.ledinh.androidstockchart.chart2.TimeUnit;

import java.util.ArrayList;
import java.util.List;

public class KlinesSet extends ChartSet<Kline> {

    public KlinesSet(ArrayList<Kline> values, TimeUnit oneDay) {
        this.values = new ArrayList<>();
    }

    public KlinesSet(List<Kline> values, TimeUnit timeUnit) {
        super(values, timeUnit);
    }

}
