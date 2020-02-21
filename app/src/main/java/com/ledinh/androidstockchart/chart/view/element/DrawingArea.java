package com.ledinh.androidstockchart.chart.view.element;

import com.ledinh.androidstockchart.chart.util.Viewport;
import com.ledinh.androidstockchart.chart.YAxis;

public class DrawingArea {
    private Viewport viewport;
    private YAxis leftAxis;
    private YAxis rightAxis;

    private DrawingElement drawingElement;

    public DrawingArea() {

    }

    public DrawingArea(YAxis leftAxis, YAxis rightAxis) {
        this.leftAxis = leftAxis;
        this.rightAxis = rightAxis;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public YAxis getLeftAxis() {
        return leftAxis;
    }

    public void setLeftAxis(YAxis leftAxis) {
        this.leftAxis = leftAxis;
    }

    public YAxis getRightAxis() {
        return rightAxis;
    }

    public void setRightAxis(YAxis rightAxis) {
        this.rightAxis = rightAxis;
    }
}
