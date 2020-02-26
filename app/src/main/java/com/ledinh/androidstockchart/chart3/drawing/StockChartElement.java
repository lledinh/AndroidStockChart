package com.ledinh.androidstockchart.chart3.drawing;

import android.graphics.Rect;

public class StockChartElement {
    protected Rect position;

    public StockChartElement() {
        position = new Rect();
    }

    public StockChartElement(Rect position) {
        this.position = position;
    }

    public int getWidth() {
        return position.right - position.left;
    }

    public int getHeight() {
        return position.bottom - position.top;
    }

    public int getLeft() {
        return position.left;
    }

    public int getTop() {
        return position.top;
    }

    public int getRight() {
        return position.right;
    }

    public int getBottom() {
        return position.bottom;
    }

    public void setLeft(int left) {
        position.left = left;
    }

    public void setTop(int top) {
        position.top = top;
    }

    public void setRight(int right) {
        position.right = right;
    }

    public void setBottom(int bottom) {
        position.bottom = bottom;
    }

    public Rect getPosition() {
        return position;
    }

    public void setPosition(Rect position) {
        this.position = position;
    }
}
