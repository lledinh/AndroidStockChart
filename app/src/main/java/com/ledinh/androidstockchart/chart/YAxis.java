package com.ledinh.androidstockchart.chart;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.ledinh.androidstockchart.chart.util.Viewport;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class YAxis {
    public enum Position {
        LEFT, RIGHT
    }

    private double axisMin = Integer.MAX_VALUE;
    private double axisMax = Integer.MIN_VALUE;

    private String unit;

    private Position position;

    public YAxis() {
        this.unit = "";
    }

    public YAxis(double axisMin, double axisMax) {
        this.axisMin = axisMin;
        this.axisMax = axisMax;
    }

    public void extendRange(float ratio) {
        double range = axisMax - axisMin;
        axisMax += (range / ratio);
        axisMin -= (range / ratio);
    }

    public void setRange(double min, double max) {
        axisMin = min;
        axisMax = max;
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


    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}