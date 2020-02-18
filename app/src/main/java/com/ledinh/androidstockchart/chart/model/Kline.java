package com.ledinh.androidstockchart.chart.model;

public class Kline {
    public long openTime;
    public long closeTime;
    public double open;
    public double high;
    public double low;
    public double close;
    public double volume;

    public Kline(long openTime, double open, double high, double low, double close, double volume, long closeTime) {
        this.openTime = openTime;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.closeTime = closeTime;
    }

    @Override
    public String toString() {
        return "Kline{" +
                "openTime=" + openTime +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                '}';
    }
}

