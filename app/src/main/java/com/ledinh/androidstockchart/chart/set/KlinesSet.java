package com.ledinh.androidstockchart.chart.set;

import com.ledinh.androidstockchart.chart.util.TimeUnit;
import com.ledinh.androidstockchart.chart.model.Kline;

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
