package com.ledinh.androidstockchart.chart;

import com.ledinh.androidstockchart.chart2.ChartData;

import java.util.ArrayList;
import java.util.List;

public class KlinesSet implements ChartData {
    private List<Kline> values;
    private TimeUnit unit;

    public KlinesSet(TimeUnit unit) {
        this.values = new ArrayList<>();
        this.unit = unit;
    }

    public KlinesSet(List<Kline> values, TimeUnit unit) {
        this.values = values;
        this.unit = unit;
    }

    public List<Kline> getValues() {
        return values;
    }

    public void setValues(List<Kline> values) {
        this.values = values;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    @Override
    public int getDataSize() {
        if (values != null) {
            return values.size();
        }
        else {
            return 0;
        }
    }
}
