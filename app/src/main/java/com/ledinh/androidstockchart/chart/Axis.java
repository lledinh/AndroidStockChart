package com.ledinh.androidstockchart.chart;

public class Axis {
    private double axisMin = Integer.MAX_VALUE;
    private double axisMax = Integer.MIN_VALUE;

    public Axis() {

    }

    public Axis(double axisMin, double axisMax) {
        this.axisMin = axisMin;
        this.axisMax = axisMax;
    }

    public void extendRange(float ratio) {
        double range = axisMax - axisMin;
        axisMax += (range / ratio);
        axisMin -= (range / ratio);
    }

    /**
     * Return a value between 0 and 1
     * 0.5 means that the position is located at half the axis
     * @param value
     * @return
     */
    public double getAxisPos(double value) {
        double range = axisMax - axisMin;
        value -= axisMin;

        return (value / range);
    }

    /**
     * Return a value between 0 and 1
     * 0.5 means that the position is located at half the axis
     * @param value
     * @return
     */
    public double getInvertedAxisPos(double value) {
        double range = axisMax - axisMin;
        value -= axisMin;

        return 1 - (value / range);
    }

    public double getAxisMin() {
        return axisMin;
    }

    public void setAxisMin(double axisMin) {
        this.axisMin = axisMin;
    }

    public double getAxisMax() {
        return axisMax;
    }

    public void setAxisMax(double axisMax) {
        this.axisMax = axisMax;
    }
}
